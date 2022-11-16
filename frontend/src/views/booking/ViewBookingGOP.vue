<template>
  <div class="container-fluid">
    <div class="flex-column d-flex align-items-center justify-content-center">
      <h1 class="pt-4 mb-4">My Bookings</h1>
      <div class="tableBox position-relative">
        <hr />
        <div class="container">
          <div class="row">
            <div class="col-10 text-start">
              <h4 class="pt-4">Current Bookings</h4>
              <p class="text-muted fs-7">
                You have {{ loansLeftPerMonth }} / {{ maxLoansPerMonth }} loans
                left this month
              </p>
            </div>
            <div class="col d-flex align-items-end justify-content-end">
              <button class="border-0 bg-transparent" @click="clickModal">
                <i
                  class="bi bi-calendar-plus fs-2 btnHover"
                  data-bs-toggle="tooltip"
                  data-bs-placement="bottom"
                  title="Add New Booking"
                ></i>
              </button>
            </div>
          </div>
        </div>

        <!-- current bookings table -->
        <div id="table1"></div>
        <hr />
        <div class="text-start">
          <h4 class="pt-4 ps-4">Completed/Past Bookings</h4>
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

    <EditBookingModal
      @toastrMsg="updateToastrMsg"
      :key="componentKey"
      id="editModal"
      @bookingSubmitted="bookingSubmitted"
      :rowData="currentEditData"
    ></EditBookingModal>

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

    <div
      data-bs-toggle="modal"
      data-bs-target="#createModal"
      id="openCreateModal"
    ></div>

    <div
      data-bs-toggle="modal"
      data-bs-target="#editModal"
      id="openEditModal"
    ></div>
  </div>
</template>
<script>
import { Grid, h } from "gridjs";
import { Toast } from "bootstrap";
import TheToastr from "@/components/TheToastr.vue";

// import components from borrowers because viewbooking is borrower access level
import CreateBookingModal from "@/components/borrower/CreateBookingModal.vue";
import CancelBookingModal from "@/components/borrower/CancelBookingModal.vue";
import EditBookingModal from "@/components/borrower/EditBookingModal.vue";

import ENDPOINT from "../../constants";

import LoanService from "@/api/services/LoanService";

// import axios from "axios";

export default {
  name: "ViewBookingGOP",
  components: {
    CreateBookingModal,
    CancelBookingModal,
    EditBookingModal,
    TheToastr,
  },
  data() {
    return {
      user: {},
      toastrResponse: "",
      formInput: { startStr: "null" },
      type: "employee",
      currentCancelData: {},
      currentEditData: {},
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
      maxLoansPerMonth: 2,
      loansLeftPerMonth: 0,
      currentBookingsGrid: new Grid({
        resizable: true,
        columns: [
          {
            id: "id",
            name: "ID",
            width: "5%",
          },
          {
            id: "passId",
            name: "PID",
            width: "7%",
          },
          {
            id: "passTitle",
            name: "Pass Title",
            width: "13%",
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
            width: "15%",
          },
          {
            id: "edit",
            sort: false,
            name: "Edit",
            formatter: (cell, row) => {
              return h(
                "i",
                {
                  class: "bi bi-pencil-fill fs-5 btnHover",
                  onClick: () => {
                    this.retrieveEditData({
                      id: row.cells[0].data,
                      passId: row.cells[1].data,
                      poi: row.cells[2].data,
                      startDate: row.cells[3].data,
                      endDate: row.cells[4].data,
                    });
                    document.getElementById("openEditModal").click();
                  },
                },
                ""
              );
            },
            width: "5%",
          },
          {
            id: "delete",
            sort: false,
            name: "Del",
            formatter: (cell, row) => {
              return h(
                "i",
                {
                  class: "bi bi-trash-fill fs-4 btnHover",
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
                ""
              );
            },
            width: "5%",
          },
        ],
        server: {
          url: `${ENDPOINT}/loan/byUserId/${this.user.userId}`,
          headers: { Authorization: this.token },
          then: (data) =>
            data.data
              .map((data) => [
                data.loanId,
                data.pass.passId,
                data.pass.poi,
                data.startDate,
                data.endDate,
                data.prevUser
                  ? data.prevUser?.email + ", " + data.prevUser?.contactNumber
                  : "N/A",
                data.user.userId,
                data.defunct,
                data.completed,
              ])
              .filter(
                (data) =>
                  data[3] >= new Date().toISOString().replace(/T.*$/, "") &&
                  data[7] == false &&
                  data[8] == false
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
          table: {},
          th: {
            'background-color': 'var(--sss_orange)',
            'color': '#273746',
            "text-align": "center",
          },
          td: {
            "text-align": "center",
          },
        },
      }),
      pastBookingsGrid: new Grid({
        columns: [
          {
            id: "id",
            name: "ID",
            width: "5%",
          },
          {
            id: "passId",
            name: "PID",
            width: "7%",
          },
          {
            id: "passTitle",
            name: "Pass Title",
            width: "13%",
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
            width: "15%",
          },
          {
            id: "status",
            sort: false,
            name: "Status",
            formatter: (cell, row) => {
              return h(
                "i",
                {
                  class: `bi ${row.cells[6] ? "bi-check-lg text-success" : "bi bi-x-lg text-danger"} fs-4 `,
                },
                ""
              );
            },
            width: "10%",
          },
        ],
        server: {
          url: `${ENDPOINT}/loan/byUserId/${this.user.userId}`,
          headers: { Authorization: this.token },
          then: (data) =>
            data.data
              .map((data) => [
                data.loanId,
                data.pass.passId,
                data.pass.poi,
                data.startDate,
                data.endDate,
                data.prevUser
                  ? data.prevUser?.email + ", " + data.prevUser?.contactNumber
                  : "N/A",
                data.completed,
                data.user.userId,
                data.defunct,
              ])
              .filter(
                (data) =>
                  (data[3] < new Date().toISOString().replace(/T.*$/, "") || data[6] == true) &&
                  data[8] == false
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
            'background-color': 'var(--sss_orange)',
            'color': '#273746',
            "text-align": "center",
          },
          td: {
            "text-align": "center",
          },
        },
      }),
    };
  },
  beforeCreate() {
    const getToken = localStorage.getItem("token");
    this.token = `Bearer ${getToken}`;
    this.user = JSON.parse(localStorage.getItem("user"));
  },
  mounted() {
    this.currentBookingsGrid.render(document.getElementById("table1"));
    this.pastBookingsGrid.render(document.getElementById("table2"));
    this.getLoansPerMonth();
  },
  methods: {
    clickModal() {
      if (this.loansLeftPerMonth != 0) {
        document.getElementById("openCreateModal").click();
      } else {
        alert(
          "You have reached your monthly limit of 2 bookings. Please cancel a booking to make a new one."
        );
      }
    },

    async getLoansPerMonth() {
      let loansPerMonth = await LoanService.getLoansPerMonth(
        JSON.parse(localStorage.getItem("user")).userId
      );

      this.loansLeftPerMonth =
        this.maxLoansPerMonth - loansPerMonth < 0
          ? 0
          : this.maxLoansPerMonth - loansPerMonth;
    },

    retrieveCancelData(data) {
      this.currentCancelData = data;
    },
    retrieveEditData(data) {
      this.currentEditData = data;
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

    bookingSubmitted(data) {
      console.log(data);
      this.getLoansPerMonth();
      this.currentBookingsGrid.forceRender();
      this.forceRerender();
      var bsAlert = new Toast(document.getElementById("theToastr"));
      bsAlert.show();
    },
    cancelSubmitted() {
      this.getLoansPerMonth();
      this.$emit("reloadComponent", true);
      this.currentBookingsGrid.forceRender();
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

@media only screen and (min-width: 992px){
  .gridjs-table {
    width: 100% !important;
  }
}

.btnHover:hover {
  color: #0d6efd;
}

.btnHover {
  cursor: pointer;
}
</style>
