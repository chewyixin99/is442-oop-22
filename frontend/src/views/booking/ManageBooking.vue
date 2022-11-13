<template>
  <div class="container-fluid">
    <div class="flex-column d-flex align-items-center justify-content-center">
      <h1 class="pt-4 mb-4">All Bookings</h1>
      <div class="tableBox position-relative">
        <hr />
        <div class="container">
          <div class="row">
            <div class="col-10 text-start">
              <h4 class="pt-4">Current Bookings</h4>
            </div>
            <div class="col-2 gap-4 d-flex justify-content-end align-items-end">
              <div
                data-bs-toggle="modal"
                data-bs-target="#cancelModal"
                v-if="recordsToDelete.length > 0"
              >
                <i
                  class="bi bi-calendar-x fs-2 btnHover"
                  data-bs-toggle="tooltip"
                  data-bs-placement="bottom"
                  title="Delete Booking"
                ></i>
              </div>
              <div
                data-bs-toggle="modal"
                data-bs-target="#createModal"
                data-bs-placement="bottom"
                title="Tooltip on bottom"
              >
                <i
                  class="bi bi-calendar-plus fs-2 btnHover"
                  data-bs-toggle="tooltip"
                  data-bs-placement="bottom"
                  title="Add New Booking"
                ></i>
              </div>
            </div>
          </div>
        </div>

        <!-- current bookings table -->
        <div id="table1"></div>
        <div class="text-start">
          <h4 class="pt-4 ps-4">Past Bookings</h4>
        </div>
        <!-- past bookings table -->
        <div id="table2"></div>
      </div>
    </div>

    <TheToastr :toastrResponse="toastrResponse"></TheToastr>

    <CreateBookingModal
      @toastrMsg="updateToastrMsg"
      :key="componentKey"
      id="createModal"
      @bookingSubmitted="bookingSubmitted"
    ></CreateBookingModal>

    <CancelBookingModal
      @toastrMsg="updateToastrMsg"
      :key="componentKey"
      id="cancelModal"
      :dataOfSelectedRow="dataOfSelectedRow"
      @cancelSubmitted="cancelSubmitted"
    ></CancelBookingModal>
  </div>
</template>
<script>
import { Grid, h } from "gridjs";
import { RowSelection } from "gridjs/plugins/selection";
import CreateBookingModal from "@/components/admin/CreateBookingModal.vue";
import { Toast } from "bootstrap";
import TheToastr from "@/components/TheToastr.vue";
import CancelBookingModal from "@/components/admin/CancelBookingModal.vue";
import axios from "axios";

export default {
  name: "ManageBooking",
  components: {
    CreateBookingModal,
    TheToastr,
    CancelBookingModal,
  },
  data() {
    return {
      user: {},
      formInput: { startStr: "null" },
      type: "admin",
      calendarInput: {
        startStr: "null",
        endStr: "null",
      },
      bookingData: [],
      currentCompletion: false,
      recordsToDelete: [],
      dataOfSelectedRow: [{}],
      toastrResponse: "",
      pass: ["", ""],
      numPass: 2,
      componentKey: 0,
      successFlag: false,
      retrievedData: [],
      currentGrid: new Grid({
        columns: [
          {
            id: "employeeCheckBox",
            plugin: {
              component: RowSelection,
              props: {
                // use the "email" column as the row identifier
                id: (row) => row.cell(1).data,
              },
            },
            width: "5%",
          },
          {
            id: "id",
            name: "ID",
            width: "10%",
          },
          {
            id: "email",
            name: "Borrower Email",
            width: "20%",
          },
          {
            id: "startDate",
            name: "Start",
            width: "10%",
          },
          {
            id: "endDate",
            name: "End",
            width: "10%",
          },
          {
            id: "previous",
            name: "Previous",
            width: "30%",
          },
          {
            id: "status",
            name: "Status",
            formatter: (cell, row) => {
              return h(
                "select",
                {
                  className: `form-select ${
                    row.cells[6].data ? "text-success" : "text-danger"
                  } `,
                  onChange: () => {
                    this.updatestatus(row.cells[1].data);
                  },
                },
                [
                  h(
                    "option",
                    {
                      value: true,
                      selected: row.cells[6].data,
                      disabled: row.cells[6].data,
                      className: `${row.cells[6].data ? "" : "text-dark"}`,
                    },
                    "Completed"
                  ),
                  h(
                    "option",
                    {
                      value: false,
                      selected: !row.cells[6].data,
                      disabled: row.cells[6].data,
                      className: `${row.cells[6].data ? "" : "text-dark"}`,
                    },
                    "Incomplete"
                  ),
                ]
              );
            },
            sort: false,
          },
        ],
        server: {
          url: "http://localhost:8081/loan",
          headers: { Authorization: this.token },
          then: (data) =>
            data.data
              .map((data) => [
                data.loanId,
                data.user.email,
                data.startDate,
                data.endDate,
                data.prevUser
                  ? data.prevUser?.email + ", " + data.prevUser?.contactNumber
                  : "N/A",
                data.completed,
                data.defunct,
              ])
              .filter(
                (data) =>
                  data[2] > new Date().toISOString().replace(/T.*$/, "") &&
                  data[6] == false
              )
              .reverse(),
          handle: (res) => {
            return res.json();
          },
        },
        search: true,
        sort: true,
        pagination: {
          limit: 5,
        },
        language: {
          search: {
            placeholder: "Search...",
          },
          pagination: {
            showing: "Displaying",
            results: () => "Records",
          },
        },
        loading: "Loading...",
        noRecordsFound: "No matching records found",
        error: "An error happened while fetching the data",
        style: {
          th: {
            "background-color": "var(--sss_orange)",
            color: "#273746",
            "text-align": "center",
          },
          td: {
            "text-align": "center",
            "font-size": "0.9rem",
          },
        },
      }),
      pastGrid: new Grid({
        columns: [
          {
            id: "employeeCheckBox",
            plugin: {
              component: RowSelection,
              props: {
                // use the "email" column as the row identifier
                id: (row) => row.cell(1).data,
              },
            },
            width: "5%",
          },
          {
            id: "id",
            name: "ID",
            width: "10%",
          },
          {
            id: "email",
            name: "Borrower Email",
            width: "20%",
          },
          {
            id: "startDate",
            name: "Start",
            width: "10%",
          },
          {
            id: "endDate",
            name: "End",
            width: "10%",
          },
          {
            id: "previous",
            name: "Previous",
            width: "30%",
          },
          {
            id: "status",
            name: "Status",
            formatter: (cell, row) => {
              return h(
                "select",
                {
                  className: `form-select ${
                    row.cells[6].data ? "text-success" : "text-danger"
                  }`,
                  onChange: () => {
                    this.updatestatus(row.cells[1].data);
                  },
                },
                [
                  h(
                    "option",
                    {
                      value: true,
                      selected: row.cells[6].data,
                      disabled: row.cells[6].data,
                      className: `${row.cells[6].data ? "" : "text-dark"}`,
                    },
                    "Completed"
                  ),
                  h(
                    "option",
                    {
                      value: false,
                      selected: !row.cells[6].data,
                      disabled: row.cells[6].data,
                      className: `${row.cells[6].data ? "" : "text-dark"}`,
                    },
                    "Incomplete"
                  ),
                ]
              );
            },
            sort: false,
          },
        ],
        server: {
          url: "http://localhost:8081/loan",
          headers: { Authorization: this.token },
          then: (data) =>
            data.data
              .map((data) => [
                data.loanId,
                data.user.email,
                data.startDate,
                data.endDate,
                data.prevUser
                  ? data.prevUser?.email + ", " + data.prevUser?.contactNumber
                  : "N/A",
                data.completed,
                data.defunct,
              ])
              .filter(
                (data) =>
                  data[2] < new Date().toISOString().replace(/T.*$/, "") &&
                  data[6] == false
              )
              .reverse(),
          handle: (res) => {
            return res.json();
          },
        },
        search: true,
        sort: true,
        pagination: {
          limit: 5,
        },
        language: {
          search: {
            placeholder: "Search...",
          },
          pagination: {
            showing: "Displaying",
            results: () => "Records",
          },
        },
        loading: "Loading...",
        noRecordsFound: "No matching records found",
        error: "An error happened while fetching the data",
        style: {
          th: {
            "background-color": "var(--sss_orange)",
            color: "#273746",
            "text-align": "center",
          },
          td: {
            "text-align": "center",
            "font-size": "0.9rem",
          },
        },
      }),
    };
  },
  beforeCreate() {
    const getToken = localStorage.getItem("token");
    this.user = JSON.parse(localStorage.getItem("user"));
    this.token = `Bearer ${getToken}`;
  },

  mounted() {
    this.currentGrid.render(document.getElementById("table1"));
    this.pastGrid.render(document.getElementById("table2"));

    this.currentGrid.on("ready", () => {
      // find the plugin with the give plugin ID
      const checkboxPlugin =
        this.currentGrid.config.plugin.get("employeeCheckBox");
      // subscribe to the store events
      checkboxPlugin.props.store?.on("updated", (state, prevState) => {
        console.log("checkbox updated", state, prevState);
        this.recordsToDelete = state["rowIds"];
        this.filterSelected();
      });
    });

    this.pastGrid.on("ready", () => {
      // find the plugin with the give plugin ID
      const checkboxPlugin =
        this.pastGrid.config.plugin.get("employeeCheckBox");

      // subscribe to the store events
      checkboxPlugin.props.store?.on("updated", (state, prevState) => {
        console.log("checkbox updated", state, prevState);
        this.recordsToDelete = state["rowIds"];
        this.filterSelected();
      });
    });

    const bearer_token = `Bearer ${localStorage.getItem("token")}`;
    const config = {
      headers: {
        Authorization: bearer_token,
      },
    };

    axios
      .get("http://localhost:8081/loan", config)
      .then((response) => {
        console.log(response);
        this.bookingData = response.data.data;
      })
      .catch((error) => {
        console.log(error);
      });
  },
  methods: {
    updatestatus(rowData) {
      console.log(rowData);

      const bearer_token = `Bearer ${localStorage.getItem("token")}`;
      const config = {
        headers: {
          Authorization: bearer_token,
        },
      };

      axios
        .put(
          "http://localhost:8081/loan/updateCompleted",
          {
            loanId: rowData,
            gopId: 1,
          },
          config
        )
        .then((response) => {
          console.log(response);
          if (response.status == 200) {
            this.currentGrid.forceRender();
            this.updateToastrMsg({ status: "Success", msg: "Status Updated" });
            var bsAlert = new Toast(document.getElementById("theToastr"));
            bsAlert.show();
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    processDate(date) {
      let split = date.split("/");
      return new Date(
        parseInt(split[2]),
        parseInt(split[1]) - 1,
        parseInt(split[0])
      )
        .toISOString()
        .replace(/T.*$/, "");
    },
    filterSelected() {
      this.dataOfSelectedRow = this.bookingData.filter((row) => {
        return this.recordsToDelete.includes(row.loanId);
      });
      console.log(this.dataOfSelectedRow);
    },
    cancelSubmitted() {
      this.dataOfSelectedRow = [{}];
      this.recordsToDelete = [];
      this.currentGrid.updateConfig().forceRender();
      this.forceRerender();

      var bsAlert = new Toast(document.getElementById("theToastr"));
      bsAlert.show();
    },
    updateToastrMsg(res) {
      this.toastrResponse = res;
    },
    bookingSubmitted() {
      this.forceRerender();
      this.currentGrid.forceRender();
      this.pastGrid.forceRender();
      var bsAlert = new Toast(document.getElementById("theToastr"));
      bsAlert.show();
    },
    forceRerender() {
      this.componentKey += 1;
    },
    selectedDates($event) {
      console.log($event);
      this.retrievedData.push({
        passId: $event.passData.passId,
        passTitle: $event.passData.passTitle,
        start: $event.start,
        end: $event.end,
      });
    },
    selectedPass(event) {
      console.log(event.target.value);
    },
  },
};
</script>

<style>
.tableBox {
  width: 70vw;
}
.btnHover:hover {
  color: var(--sss_orange) !important;
}


.btnHover {
  cursor: pointer;
}
</style>
