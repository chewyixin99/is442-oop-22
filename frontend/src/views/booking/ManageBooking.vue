<template>
  <div class="container-fluid">
    <div class="flex-column d-flex align-items-center justify-content-center">
      <h1 class="pt-4 mb-4">Booking</h1>
      <h4 class="pt-4">Employee Bookings</h4>
      <div class="tableBox position-relative">
        <button
          type="button"
          class="newButton btn btn-info"
          data-bs-toggle="modal"
          data-bs-target="#adminModal"
        >
          New
        </button>
        <button
          type="button"
          class="delButton btn btn-danger"
          @click="deleteRecords()"
        >
          Delete
        </button>
        <div id="table"></div>
      </div>
    </div>
    <AdminBookingModal
      @toastrMsg="updateToastrMsg"
      id="adminModal"
      modalType="create"
    ></AdminBookingModal>

  </div>
</template>
<script>
import { Grid, html } from "gridjs";
import { RowSelection } from "gridjs/plugins/selection";
import * as bootstrap from "bootstrap";
import AdminBookingModal from "@/components/AdminBookingModal.vue";

export default {
  name: "ManageBooking",
  components: {
    AdminBookingModal
  },
  data() {
    return {
      formInput: { startStr: "null" },
      type: "admin",
      calendarInput: {
        startStr: "null",
        endStr: "null",
      },
      pass: ["", ""],
      numPass: 2,
      componentKey: 0,
      successFlag: false,
      retrievedData: [],
      grid: new Grid({
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
            name: "Pass ID",
            width: "10%",
          },
          {
            name: "Borrower Email",
            width: "20%",
          },
          {
            name: "Start Date",
            width: "10%",
          },
          {
            name: "End Date",
            width: "10%",
          },
          {
            name: "Previous Booker",
            width: "15%",
          },
          {
            name: "Following Booker",
            width: "15%",
          },
          {
            name: "Status",
            // formatter: (cell, row) => {
            //     return h('button', {
            //         className: 'py-2 px-3 border rounded text-white bg-primary',
            //         onClick: () => alert(`Editing "${row.cells[0].data}" "${row.cells[1].data}"`)
            //     }, 'Edit');
            // }

            formatter: () =>
              html(
                `<select class="form-select" aria-label="Default select example">
                  <option selected value="Approved">Approved</option>
                  <option value="Rejected">Rejected</option>
                  <option value="Cancelled">Cancelled</option>
                `
              ),
            sort: false
          },
        ],
        data: [
          [
            1,
            "mark@gmail.com",
            "26/09/22",
            "30/09/22",
            "Charlie - 86541253",
            "Daisy - 85454126",
          ],
          [
            2,
            "bob@gmail.com",
            "30/09/22",
            "1/10/22",
            "Mark - 86541253",
            "Aloysius - 85454126",
          ],
        ],
        search: true,
        sort: true,
        pagination: {
          limit: 10,
        },
        language: {
          search: {
            placeholder: "🔍 Search...",
          },
          pagination: {
            showing: "😃 Displaying",
            results: () => "Records",
          },
        },
        loading: "Loading...",
        noRecordsFound: "No matching records found",
        error: "An error happened while fetching the data",
        style: {
          th: {
            "background-color": "rgba(0, 0, 0, 0.1)",
            "text-align": "center",
          },
          td: {
            "text-align": "center",
          },
        },
      }),
    };
  },
  mounted() {
    this.grid.render(document.getElementById("table"));
    this.grid.on("ready", () => {
      // find the plugin with the give plugin ID
      const checkboxPlugin = this.grid.config.plugin.get("employeeCheckBox");

      // subscribe to the store events
      checkboxPlugin.props.store.on("updated", (state, prevState) => {
        console.log("checkbox updated", state, prevState);
        this.recordsToDelete = state["rowIds"];
      });
    });
  },
  methods: {
    async createEmployee() {
      // const employee = await EmployeeService.createEmployee();
      // console.log(employee);
      var bsAlert = new bootstrap.Toast(document.getElementById("theToastr")); //inizialize it
      this.$emit("toastrMsg", "New employee has been created!");
      bsAlert.show();
    },
    async deleteRecords() {
      this.recordsToDelete.forEach((value, index) => {
        console.log("Index to delete:", index, "Id value:", value);
      });
      // const employees = await EmployeeService.removeEmployees();
      // console.log(employees);
    },
    async editEmployeeDetails() {
      // const employees = await EmployeeService.editEmployeeDetails("1");
      // console.log(employees);
      var bsAlert = new bootstrap.Toast(document.getElementById("theToastr")); //inizialize it
      this.$emit("toastrMsg", "Your changes have been saved!");
      bsAlert.show();
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
    // fixed events
    passFn(id) {
      if (id == "1") {
        return {
          title: "Zoo 1",
          id: "1",
          events: [
            {
              id: 1,
              title: "Team 1",
              start: new Date(2022, 8, 29).toISOString().replace(/T.*$/, ""),
            },
            {
              id: 2,
              title: "Team 3",
              start: new Date(2022, 8, 30).toISOString().replace(/T.*$/, ""),
            },
          ],
        };
      } else if (id == "2") {
        return {
          title: "Zoo 2",
          id: "2",
          events: [
            {
              id: 1,
              title: "Finance Dept",
              start: new Date(2022, 8, 24).toISOString().replace(/T.*$/, ""),
            },
            {
              id: 2,
              title: "Teaching Dept",
              start: new Date(2022, 8, 26).toISOString().replace(/T.*$/, ""),
            },
          ],
        };
      } else if (id == "5") {
        return {
          title: "Safari 2",
          id: "5",
        };
      } else if (id == "6") {
        return {
          title: "Gardens By The Bay",
          id: "6",
        };
      }
    },
    forceRerender() {
      this.pass = "";
      this.componentKey += 1;
      this.successFlag = true;
      alert(JSON.stringify(this.retrievedData));
      document.getElementById("toast").classList.add("show");
    },
  },
};
</script>

<style>
.tableBox {
  width: 70vw;
}
</style>