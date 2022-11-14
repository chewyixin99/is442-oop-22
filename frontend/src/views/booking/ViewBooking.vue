<template>
  <div class="container-fluid">
    <div class="flex-column d-flex align-items-center justify-content-center">
      <h1 class="pt-4 mb-4">My Booking</h1>
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
              <i
                class="bi bi-calendar-plus fs-2 btnHover"
                data-bs-toggle="tooltip"
                data-bs-placement="bottom"
                title="Add New Booking"
              ></i>
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
import CreateBookingModal from "@/components/employee/CreateBookingModal.vue";
import { Toast } from "bootstrap";
import TheToastr from "@/components/TheToastr.vue";
import CancelBookingModal from "@/components/employee/CancelBookingModal.vue";
import EditBookingModal from "@/components/employee/EditBookingModal.vue";

// import axios from "axios";

export default {
  name: "ViewBooking",
  components: {
    CreateBookingModal,
    CancelBookingModal,
    EditBookingModal,
    TheToastr,
  },
  data() {
    return {
      user: JSON.parse(localStorage.getItem("user")),
      token: "",
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
      currentBookingsGrid: new Grid({
        resizable: true,
        columns: [
          {
            id: "id",
            name: "ID",
            width: "10%",
          },
          {
            id: "passTitle",
            name: "Pass Title",
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
                  // style: {
                  //   border: "1px solid #0d6efd",
                  //   color: "#0d6efd",
                  //   backgroundColor: "#fff",
                  //   padding: "5px",
                  //   "border-radius": "5px",
                  //   "text-align": "center",
                  // },
                  onClick: () => {
                    this.retrieveEditData({
                      id: row.cells[0].data,
                      poi: row.cells[1].data,
                      startDate: row.cells[2].data,
                      endDate: row.cells[3].data,
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
                  // style: {
                  //   border: "1px solid #0d6efd",
                  //   color: "#0d6efd",
                  //   backgroundColor: "#fff",
                  //   padding: "5px",
                  //   "border-radius": "5px",
                  //   "text-align": "center",
                  // },
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
          url: "http://localhost:8081/loan",
          headers: { "Authorization" : this.token},
          then: (data) =>
            data.data
              .map((data) => [
                data.loanId,
                data.pass.poi,
                data.startDate,
                data.endDate,
                data.prevUser
                  ? data.prevUser?.email + ", " + data.prevUser?.contactNumber
                  : "N/A",
                data.user.userId,
                data.defunct
              ])
              .filter(
                (data) => (data[2] >= new Date().toISOString().replace(/T.*$/, "")) && (data[6] == false) && (data[5] == this.user.userId)
              ),
          handle: (res) => {
            return res.json();
          },
        },
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
      pastBookingsGrid: new Grid({
        columns: [
          {
            id: "id",
            name: "ID",
            width: "10%",
          },
          {
            id: "passTitle",
            name: "Pass Title",
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
        ],
        server: {
          url: "http://localhost:8081/loan",
          headers: { "Authorization" : this.token},
          then: (data) =>
            data.data
              .map((data) => [
                data.loanId,
                data.pass.poi,
                data.startDate,
                data.endDate,
                data.prevUser
                  ? data.prevUser?.email + ", " + data.prevUser?.contactNumber
                  : "N/A",
                data.user.userId,
                data.defunct
              ])
              .filter(
                (data) => (data[2] < new Date().toISOString().replace(/T.*$/, "")) && (data[6] == false) && (data[5] == this.user.userId)
              ),
          handle: (res) => {
            return res.json();
          },
        },
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
  beforeCreate(){
    const getToken = localStorage.getItem("token");
    this.token = `Bearer ${getToken}`
  },
  mounted() {

    this.currentBookingsGrid.render(document.getElementById("table1"));
    this.pastBookingsGrid.render(document.getElementById("table2"));
    // this.getData();
  

  },
  methods: {
    processDate(date){
      let split = date.split("/")
      return new Date(parseInt(split[2]), parseInt(split[1])-1, parseInt(split[0])).toISOString().replace(/T.*$/, "")
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
    // getData() {

    //   const bearer_token = `Bearer ${localStorage.getItem('token')}`;
    //   const config = {
    //       headers: {
    //         Authorization: bearer_token
    //       }
    //   };
    //   axios
    //     .get("http://localhost:8081/loan", config)
    //     .then((response) => {
    //       let resList = [];
    //       console.log(response.data.data);
    //       console.log(this.selectedPass);
    //       resList = response.data.data.filter((pass) => pass.userId == this.userId);

    //       // loop through resList
    //       for (let i = 0; i < resList.length; i++) {
    //         let passTitle = resList[i].passId;
    //         let id = resList[i].loanId;
    //         let startDate = resList[i].startDate;
    //         let endDate = resList[i].endDate;
    //         let previous = "N.A.";
    //         let following = "N.A.";
    //         console.log(             id,
    //           passTitle,
    //           startDate,
    //           endDate,
    //           previous,
    //           following);
    //         this.currentBookingsGrid.data.push([
    //           id,
    //           passTitle,
    //           startDate,
    //           endDate,
    //           previous,
    //           following,
    //         ]);
    //       }
    //       console.log(this.data);
    //     })
    //     .catch((error) => {
    //       console.log(error);
    //     });
    // },
    bookingSubmitted(data) {
      console.log(data);      
      this.currentBookingsGrid.forceRender();
      this.forceRerender();
      var bsAlert = new Toast(document.getElementById("theToastr"));
      bsAlert.show();
    },
    cancelSubmitted() {
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

.gridjs-table{
  width: 100% !important;
}

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
