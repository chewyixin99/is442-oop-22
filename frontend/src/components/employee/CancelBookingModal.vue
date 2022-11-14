<template>
  <div
    class="modal fade"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div
      class="modal-dialog modal-dialog-centered"
      style="justify-content: center"
    >
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">
            <p class="m-0">Cancel Booking</p>
            <p class="text-muted m-0 fs-6 text-start">
              Booking ID: {{ rowData.id }}
            </p>
          </h5>
          <i
            class="bi bi-x fs-1"
            id="cancel-close-btn"
            style="cursor: pointer"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></i>
        </div>
        <div class="modal-body">
          <h4>Booking Details</h4>
          <div class="row gap-5">
            <div class="col text-end">
              <span>Booking ID:</span>
            </div>
            <div class="col text-start">
              <span>{{ rowData.id }}</span>
            </div>
          </div>
          <div class="row gap-5">
            <div class="col text-end">
              <span>Pass Name:</span>
            </div>
            <div class="col text-start">
              <span>{{ rowData.passTitle }}</span>
            </div>
          </div>
          <div class="row gap-5">
            <div class="col text-end">
              <span>Start Date:</span>
            </div>
            <div class="col text-start">
              <span>{{ rowData.startDate }}</span>
            </div>
          </div>
          <div class="row gap-5">
            <div class="col text-end">
              <span>End Date:</span>
            </div>
            <div class="col text-start">
              <span>{{ rowData.endDate }}</span>
            </div>
          </div>
          <hr />
          <p>Confirm cancel?</p>
          <div class="row">
            <div class="col text-end">
              <button
                class="btn btn-danger"
                style="min-width: 100px"
                @click.prevent="cancelBooking"
              >
                <div class="" v-show="!isLoading">Yes</div>
                <div
                  class="spinner-border spinner-border-sm text-light"
                  v-show="isLoading"
                ></div>
              </button>
            </div>
            <div class="col text-start">
              <button
                class="btn btn-secondary"
                style="min-width: 100px"
                @click.stop="clickedCancel"
              >
                No
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "CancelBookingModal",
  props: {
    modalType: String,
    bookingDetails: Object,
    rowData: Object,
  },
  components: {
    // BookingCalendar,
  },
  data() {
    return {
      user: JSON.parse(localStorage.getItem("user")),
      isChecked: false,
      selectedPassId: null,
      selectedPass: null,
      isLoading: false,
      numOfGuest: 1,
      numPass: 2,
      componentKey: 0,
      availablePasses: [
        {
          id: "1",
          title: "Zoo 1",
          selected: false,
        },
        {
          id: "2",
          title: "Zoo 2",
          selected: false,
        },
        {
          id: "3",
          title: "Zoo 3",
          selected: false,
        },
        {
          id: "4",
          title: "Safari 1",
          selected: false,
        },
        {
          id: "5",
          title: "Safari 2",
          selected: false,
        },
        {
          id: "6",
          title: "Gardens By The Bay",
          selected: false,
        },
      ],
      retrievedData: {
        passId: null,
        passTitle: "",
        start: "",
        end: "",
      },
    };
  },
  methods: {
    clickedCancel() {
      document.getElementById("cancel-close-btn").click();
    },
    selectedDates($event) {
      this.retrievedData = {
        passId: $event.passData.passId,
        passTitle: $event.passData.passTitle,
        start: $event.start,
        end: $event.end,
      };
      console.log(this.retrievedData);
    },
    selectPass(event) {
      this.selectedPassId = event.target.value;
      this.selectedPass = this.availablePasses.find(
        (pass) => pass.id === this.selectedPassId
      );

      console.log(this.selectedPass);
    },
    forceRerender() {
      this.componentKey += 1;
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
    async cancelBooking() {
      this.isLoading = true;
      const bearer_token = `Bearer ${localStorage.getItem("token")}`;
      const config = {
        headers: {
          Authorization: bearer_token,
        },
      };
      axios
        .delete("http://localhost:8081/loan/" + this.rowData.id,config)
        .then((response) => {
          console.log(response);
          if (response.status != 500) {
            this.isLoading = false;
            document.getElementById("cancel-close-btn").click();
            this.$emit("cancelSubmitted", true);
            this.$emit("toastrMsg", {
              status: "Success",
              msg: "Cancellation is successful!",
            });
          }
        })
        .catch((error) => {
          console.log(error);
        });

      // setTimeout(() => {
      //   document.getElementById("cancel-close-btn").click();
      //   this.$emit("cancelSubmitted", true);
      //   this.$emit("toastrMsg", {
      //     status: "Success",
      //     msg: "Cancellation is successful!",
      //   });
      // }, 1000);
      // var bsAlert = new Toast(document.getElementById("theToastr")); //inizialize it
      // this.$emit("toastrMsg", "New employee has been created!");
      // bsAlert.show();
    },
  },
};
</script>
<style>
.modal-content {
  /* width: 60% !important; */
}
</style>