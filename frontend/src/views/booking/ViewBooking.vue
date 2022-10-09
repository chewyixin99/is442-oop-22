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
              data-bs-target="#employeeModal"
            >
              <i class="bi bi-calendar-plus fs-2 btnHover"></i>
            </div>
          </div>
        </div>

        <div id="table1"></div>
        <hr />
        <div class="text-start">
          <h4 class="pt-4 ps-4">Past Bookings</h4>
        </div>

        <div id="table2"></div>
      </div>
    </div>

    <!-- <div
      id="toast"
      class="toast fade position-fixed bottom-0 right-0"
      role="alert"
      aria-live="assertive"
      aria-atomic="true"
    >
      <div class="toast-header">
        <svg
          class="bd-placeholder-img rounded me-2"
          width="20"
          height="20"
          xmlns="http://www.w3.org/2000/svg"
          aria-hidden="true"
          preserveAspectRatio="xMidYMid slice"
          focusable="false"
        >
          <rect width="100%" height="100%" fill="#007aff"></rect>
        </svg>

        <strong class="me-auto">Loan Success!</strong>
        <small>Just now</small>
        <button
          type="button"
          class="btn-close"
          data-bs-dismiss="toast"
          aria-label="Close"
        ></button>
      </div>
      <div class="toast-body">This is supposed to be on the right</div>
    </div> -->
    <!--       @toastrMsg="updateToastrMsg" -->
    <TheToastr :toastrResponse="toastrResponse"></TheToastr>

    <EmployeeBookingModal
      @toastrMsg="updateToastrMsg"
      :key="componentKey"
      id="employeeModal"
      modalType="create"
      @bookingSubmitted="bookingSubmitted"
    ></EmployeeBookingModal>
  </div>
</template>
<script>
import { Grid } from "gridjs";

import EmployeeBookingModal from "@/components/EmployeeBookingModal.vue";
import * as bootstrap from "bootstrap";
import TheToastr from "@/components/TheToastr.vue";

export default {
  name: "ViewBooking",
  components: {
    EmployeeBookingModal,
    TheToastr
  },
  data() {
    return {
      toastrResponse: "",
      formInput: { startStr: "null" },
      type: "employee",
      calendarInput: {
        startStr: "null",
        endStr: "null",
      },
      pass: ["", ""],
      numPass: 2,
      componentKey: 0,
      successFlag: false,
      retrievedData: [],
      currentBookingsGrid: new Grid({
        resizable: true,
        columns: [
          {
            name: "ID",
            width: "10%",
          },
          {
            name: "Pass Title",
            width: "25%",
          },
          {
            name: "Start Date",
            width: "12%",
          },
          {
            name: "End Date",
            width: "12%",
          },
          {
            name: "Previous Booker",
            width: "20%",
          },
          {
            name: "Following Booker",
            width: "20%",
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
          [2, "Zoo 2", "30/09/22", "2/10/22", "Alan - 98756232", "N.A."],
          [
            3,
            "Imagine Dragon Deez Nuts",
            "eo3n@yahoo.com",
            "(05) 10 878 5554",
            "c",
            "d",
          ],
          [4, "Nisen", "nis900@gmail.com", "313 333 1923", "c", "d"],
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
            name: "ID",
            width: "10%",
          },
          {
            name: "Pass Title",
            width: "25%",
          },
          {
            name: "Start Date",
            width: "12%",
          },
          {
            name: "End Date",
            width: "12%",
          },
          {
            name: "Previous Booker",
            width: "20%",
          },
          {
            name: "Following Booker",
            width: "20%",
          },
        ],
        data: [
          [1, "John Cena", "john@example.com", "(353) 01 222 3333", "c", "d"],
          [2, "Joe Mama", "mark@gmail.com", "(01) 22 888 4444", "c", "d"],
          [
            3,
            "Imagine Dragon Deez Nuts",
            "eo3n@yahoo.com",
            "(05) 10 878 5554",
            "c",
            "d",
          ],
          [4, "Nisen", "nis900@gmail.com", "313 333 1923", "c", "d"],
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
    this.currentBookingsGrid.render(document.getElementById("table1"));
    this.employeeBookingsGrid.render(document.getElementById("table2"));
  },
  methods: {
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
      console.log('dkjf');
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
    bookingSubmitted() {
      this.forceRerender();
      var bsAlert = new bootstrap.Toast(document.getElementById("theToastr")); //inizialize it
      this.$emit("toastrMsg", {
        status: "Success",
        msg: "New employee has been created!",
      });
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
