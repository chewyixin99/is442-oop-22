<template>
  <div class="container-fluid">
    <div class="flex-column d-flex align-items-center justify-content-center">
      <h1 class="pt-4 mb-4">Booking</h1>
      <div class="tableBox position-relative">
        <hr />
        <div class="container">
          <div class="row">
            <div class="col-10 text-start">
              <h4 class="pt-4">Current Bookings</h4>
              <p class="text-muted fs-7">You have 2 loans left this month</p>
            </div>
            <div
              class="col d-flex align-items-end justify-content-end"
              data-bs-toggle="modal"
              data-bs-target="#createModal"
            >
              <i class="bi bi-calendar-plus fs-2 btnHover" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Add New Booking"></i>
            </div>
          </div>
        </div>
        <!-- current bookings table -->
        <div id="table1"></div>
        <hr />
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
      @cancelSubmitted="cancelSubmitted"
      :rowData="currentCancelData"
    ></CancelBookingModal>

    <div
      data-bs-toggle="modal"
      data-bs-target="#cancelModal"
      id="openCancelModal"
    ></div>
  </div>
</template>
<script>
import { Grid, h } from "gridjs";
import CreateBookingModal from "@/components/employee/CreateBookingModal.vue";
import { Toast } from "bootstrap";
import TheToastr from "@/components/TheToastr.vue";
import CancelBookingModal from "@/components/employee/CancelBookingModal.vue";

export default {
  name: "ViewBooking",
  components: {
    CreateBookingModal,
    CancelBookingModal,
    TheToastr,
  },
  data() {
    return {
      toastrResponse: "",
      formInput: { startStr: "null" },
      type: "employee",
      currentCancelData: {},
      calendarInput: {
        startStr: "null",
        endStr: "null",
      },
      pass: ["", ""],
      numPass: 2,
      componentKey: 0,
      successFlag: false,
      retrievedData: [],
      bookingDetails: {},
      currentBookingsGrid: new Grid({
        resizable: true,
        columns: [
          {
            id: "id",
            name: "Booking ID",
            width: "10%",
          },
          {
            id: "passTitle",
            name: "Pass Title",
            width: "20%",
          },
          {
            id: "startDate",
            name: "Start Date",
            width: "10%",
          },
          {
            id: "endDate",
            name: "End Date",
            width: "10%",
          },
          {
            id: "previous",
            name: "Previous Booker",
            width: "15%",
          },
          {
            id: "following",
            name: "Following Booker",
            width: "15%",
          },
          {
            id: "action",
            sort: false,
            formatter: (cell, row) => {
              return h(
                "button",
                {
                  style: {
                    border: "1px solid #0d6efd",
                    color: "#0d6efd",
                    backgroundColor: "#fff",
                    padding: "5px",
                    "border-radius": "5px",
                    "text-align": "center",
                  },
                  onClick: () => {
                    this.retrieveCancelData({
                      id: row.cells[0].data,
                      passTitle: row.cells[1].data,
                      startDate: row.cells[2].data,
                      endDate: row.cells[3].data,
                    });
                    document.getElementById("openCancelModal").click();
                  },
                },
                "Cancel"
              );
            },
            width: "10%",
          },
        ],
        data: [
          [
            1,
            "Zoo 1",
            "26/09/22",
            "30/09/22",
            "Charlie - 86541253",
            "Daisy - 85454126",
          ],
          [2, "Bird Park", "02/11/22", "04/11/22", "N.A.", "N.A."],
        ],
        search: true,
        sort: true,
        pagination: {
          limit: 10,
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
          table: {},
          th: {
            "background-color": "rgba(0, 0, 0, 0.1)",
            "text-align": "center",
          },
          td: {
            "text-align": "center",
          },
        },
      }),
      employeeBookingsGrid: new Grid({
        columns: [
          {
            id: "id",
            name: "Booking ID",
            width: "10%",
          },
          {
            id: "passTitle",
            name: "Pass Title",
            width: "20%",
          },
          {
            id: "startDate",
            name: "Start Date",
            width: "10%",
          },
          {
            id: "endDate",
            name: "End Date",
            width: "10%",
          },
          {
            id: "previous",
            name: "Previous Booker",
            width: "15%",
          },
          {
            id: "following",
            name: "Following Booker",
            width: "15%",
          },
        ],
        data: [
          [1, "John Doe", "30/07/22", "02/08/22", "Bob - 86556232", "N.A."],
          [2, "Joe Tan", "29/08/22", "01/09/22", "N.A.", "N.A."],
          [3, "Zoo 2", "05/09/22", "07/09/22", "Alan - 98756232", "N.A."],
          [4, "Gardens By The Bay", "15/09/22", "16/09/22", "N.A.", "N.A."],
        ],
        search: true,
        sort: true,
        pagination: {
          limit: 10,
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
    this.currentBookingsGrid.render(document.getElementById("table1"));
    this.employeeBookingsGrid.render(document.getElementById("table2"));
  },
  methods: {
    retrieveCancelData(data) {
      this.currentCancelData = data;
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
    updateToastrMsg(res) {
      this.toastrResponse = res;
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
    bookingSubmitted(data) {
      alert(JSON.stringify(data))
      this.forceRerender();
      var bsAlert = new Toast(document.getElementById("theToastr"));
      bsAlert.show();
    },
    cancelSubmitted() {
      this.forceRerender();
      var bsAlert = new Toast(document.getElementById("theToastr"));
      bsAlert.show();
    },
    forceRerender() {
      this.componentKey += 1;
    },
  },
};
</script>

<style>
.tableBox {
  width: 70vw;
}

.btnHover:hover {
  color: #0d6efd;
}

.btnHover {
  cursor: pointer;
}
</style>
