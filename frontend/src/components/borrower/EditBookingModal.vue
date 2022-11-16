<template>
  <div
    class="modal fade"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Edit Booking</h5>
          <i
            class="bi bi-x fs-1"
            id="edit-close-btn"
            style="cursor: pointer"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></i>
        </div>
        <div class="modal-body text-start" style="padding: 30px">
          <form>
            <div class="col-md">
              <div class="row gap-5">
                <div class="col text-end">
                  <span>Booking ID:</span>
                </div>
                <div class="col text-start">
                  <span>{{ rowData.id }}</span>
                </div>
              </div>
            </div>
            <div class="p-4">
              <div class="" v-if="selectedPass">
                <BookingCalendar
                  :key="componentKey"
                  @selectedData="selectedData"
                  :selectedPass="selectedPass"
                  :selectedLoan="rowData"
                  :isEdit="true"
                  class="mt-4"
                />
              </div>
              <div
                class="
                  d-flex
                  text-center
                  justify-content-center
                  align-center
                  mt-4
                "
                v-else
              >
                <div class="spinner-border text-primary" role="status">
                  <span class="visually-hidden">Loading...</span>
                </div>
              </div>
            </div>

            <div class="mt-4" v-if="selectedPass">
              <div class="form-group">
                <h4>Booking Details</h4>
                <div class="row my-4">
                  <div class="col">
                    <label for="exampleFormControlInput1">Email address</label>
                    <input
                      type="email"
                      class="form-control"
                      id="exampleFormControlInput1"
                      :value="user.email"
                      disabled
                    />
                  </div>
                  <div class="col">
                    <label for="exampleFormControlInput1">Staff ID</label>
                    <input
                      type="text"
                      class="form-control"
                      id="exampleFormControlInput1"
                      :value="user.userId"
                      disabled
                    />
                  </div>
                  <div class="col">
                    <label for="exampleFormControlInput1">Contact Number</label>
                    <input
                      type="text"
                      class="form-control"
                      id="exampleFormControlInput1"
                      :value="user.contactNumber"
                      disabled
                    />
                  </div>
                </div>
              </div>

              <div class="form-group">
                <div class="row">
                  <div class="col">
                    <label for="exampleFormControlInput1">Pass Title</label>
                    <input
                      type="email"
                      class="form-control"
                      id="exampleFormControlInput1"
                      :value="rowData.poi"
                      disabled
                    />
                  </div>
                </div>
                <div class="row my-4">
                  <div class="col">
                    <label for="exampleFormControlInput1">Start Date</label>
                    <input
                      type="date"
                      class="form-control"
                      id="exampleFormControlInput1"
                      :value="retrievedData.startDate"
                      disabled
                    />
                  </div>
                  <div class="col">
                    <label for="exampleFormControlInput1">End Date</label>
                    <input
                      type="date"
                      class="form-control"
                      id="exampleFormControlInput1"
                      :value="retrievedData.endDate"
                      disabled
                    />
                  </div>
                </div>
                <label for="exampleFormControlTextarea1">Remarks</label>
                <textarea
                  class="form-control"
                  id="exampleFormControlTextarea1"
                  rows="3"
                  v-model="bookingRemarks"
                ></textarea>
              </div>
            </div>
            <div class="modal-footer" v-if="selectedPass">
              <div class="my-2 form-check">
                <input
                  type="checkbox"
                  class="form-check-input"
                  id="exampleCheck1"
                  v-model="isChecked"
                />
                <span>I accept the <a href="#">terms and conditions</a> </span>
              </div>
              <button
                type="button"
                class="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                Close
              </button>
              <button
                type="button"
                class="btn btn-primary"
                :disabled="!isChecked || retrievedData.start == ''"
                @click.stop="editBooking"
                style="min-width: 100px"
              >
                <div class="" v-show="!isLoading">Submit</div>
                <div
                  class="spinner-border spinner-border-sm text-light"
                  v-show="isLoading"
                ></div>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BookingCalendar from "@/components/common/BookingCalendar.vue";
import LoanService from "@/api/services/LoanService";
import PassService from "@/api/services/PassService";

export default {
  name: "EditBookingModal",
  props: {
    modalType: String,
    rowData: Object,
  },
  components: {
    BookingCalendar,
  },
  data() {
    return {
      user: JSON.parse(localStorage.getItem("user")),
      bookingGuestDetails: [
        {
          name: "",
          email: "",
          contact: "",
        },
      ],
      bookingRemarks: "",
      bookingDetails: {},
      isChecked: false,
      selectedPassId: null,
      selectedPass: null,
      isLoading: false,
      numPass: 2,
      componentKey: 0,
      availablePasses: [],
      retrievedData: {},
      retrievedPassData: {},
    };
  },
  methods: {
    removeGuest(e, index) {
      console.log(index);
      this.bookingGuestDetails.splice(index, 1);
    },
    addNewGuest() {
      this.bookingGuestDetails.push({
        name: "",
        email: "",
        contact: "",
      });
    },
    selectedData($event) {
      this.retrievedData = {
        passId: $event.passID,
        userId: $event.userID,
        startDate: $event.startDate,
        endDate: $event.endDate,
      };
      console.log(this.retrievedData);
    },

    forceRerender() {
      this.componentKey += 1;
    },

    processDate2(date) {
      let split = date.split("-").reverse();
      for (let i = 0; i < split.length; i++) {
        if (split[i].length == 1) {
          split[i] = "0" + split[i];
        }
      }
      return split.join("/");
    },
    async editBooking() {
      this.isLoading = true;
      this.retrievedData.loanId = this.rowData.id;
      const response = await LoanService.editLoan(this.retrievedData);
      if (response.status == 200) {
        this.isLoading = false;
        document.getElementById("edit-close-btn").click();
        this.$emit("bookingSubmitted", this.retrievedData);
        this.$emit("toastrMsg", {
          status: "Success",
          msg: "Edit is successful!",
        });
      }
      console.log(response);
    },

    async getData() {
      console.log(this.rowData);
      const response1 = await LoanService.getLoanByLoanId(this.rowData.id);
      console.log(response1);
      const response2 = await PassService.getPassesById(
        response1.data.data.pass.passId
      );
      console.log(response2);


      if (response2.status == 200) {
        setTimeout(() => {
          this.retrievedPassData = response2.data.data;

          this.selectedPass = response2.data.data;
          console.log("specific pass data updated ------------------");
        }, 1000);
      }
    },
  },

  watch: {
    rowData: function (newVal, oldVal) {
      console.log(newVal);
      console.log(oldVal);
      console.log("rowData updated ------------------");
      this.getData();
    },
  },
};
</script>
<style>
.modal {
  --bs-modal-width: 50vw !important;
}
</style>