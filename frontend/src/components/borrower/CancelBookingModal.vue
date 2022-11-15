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
import LoanService from "@/api/services/LoanService";

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
      selectedPassId: null,
      selectedPass: null,
      isLoading: false,
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

    },
    async cancelBooking() {
      this.isLoading = true;
      const response = await LoanService.cancelLoan(this.rowData.id)
       if (response.status != 500) {
            this.isLoading = false;
            document.getElementById("cancel-close-btn").click();
            this.$emit("cancelSubmitted", true);
            this.$emit("toastrMsg", {
              status: "Success",
              msg: "Cancellation is successful!",
            });
          }
    },
  },
};
</script>
<style>
</style>