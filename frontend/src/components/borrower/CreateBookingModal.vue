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
          <h5 class="modal-title" id="exampleModalLabel">Create Booking</h5>
          <i
            class="bi bi-x fs-1"
            id="create-close-btn"
            style="cursor: pointer"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></i>
        </div>
        <div class="modal-body text-start" style="padding: 30px">
          <form>
            <div class="col-md">
              <div class="mb-3 has-validation">
                <label for="passType" class="col-form-label"
                  >Number of Passes</label
                >
                <select
                  class="form-select"
                  aria-label="Default select example"
                  @change="selectNumPasses($event)"
                  id="numPasses"
                >
                  <option value="1">1</option>
                  <option value="2">2</option>
                </select>
              </div>
            </div>
            <div class="mb-3 has-validation">
              <label for="passType" class="col-form-label">Pass Type</label>
              <select
                class="form-select"
                aria-label="Default select example"
                @change="selectPass($event)"
                id="passType"
              >
                <option>Choose Pass</option>
                <option
                  v-for="availablePass in availablePasses"
                  :key="availablePass"
                  :value="availablePass.passId"
                >
                  {{ availablePass.poi }} - ID#{{ availablePass.passId }}
                </option>
              </select>
            </div>
            <div class="p-4">
              <BookingCalendar
                :key="componentKey"
                @selectedData="selectedData"
                :selectedPass="selectedPass"
                :selectedLoan="selectedLoan"
                :selectNumPasses="selectNumPasses"
                class="mt-4"
              />
            </div>

            <div class="mt-4" v-if="selectedPass">
              <div class="" v-if="numPasses == 2 && isPassSelected">
                <h4>Conflict Check</h4>
                <div class="row my-4" v-if="isValidityLoading">
                  <div
                    class="spinner-border spinner-border ms-3 text-primary px-0"
                  ></div>
                </div>
                <div class="row my-4" v-else>
                  <div class="" v-if="isSecondaryPassConflict">
                    <span class="text-danger"
                      >There are conflicts. Please book another date!</span
                    >
                  </div>
                  <div class="" v-else>
                    <span class="text-success">There are no conflicts.</span>
                  </div>
                </div>
              </div>

              <div class="form-group">
                <h4>Booking Details</h4>
                <div class="row my-4">
                  <div class="col-6">
                    <label for="exampleFormControlInput1">Email address</label>
                    <input
                      type="email"
                      class="form-control"
                      id="exampleFormControlInput1"
                      :value="user.email"
                      disabled
                    />
                  </div>
                  <div class="col-2">
                    <label for="exampleFormControlInput1">Staff ID</label>
                    <input
                      type="text"
                      class="form-control"
                      id="exampleFormControlInput1"
                      :value="user.userId"
                      disabled
                    />
                  </div>
                  <div class="col-4">
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
                    <label for="exampleFormControlInput1">Pass Type</label>
                    <input
                      type="email"
                      class="form-control"
                      id="exampleFormControlInput1"
                      :value="selectedPass.poi"
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
                <span
                  >I accept the
                  <a
                    target="_blank"
                    href="https://www.youtube.com/watch?v=xvFZjo5PgG0"
                    >terms and conditions</a
                  >
                </span>
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
                @click.stop="submitBooking"
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
  name: "CreateBookingModal",
  props: {
    modalType: String,
  },
  components: {
    BookingCalendar,
  },
  data() {
    return {
      user: JSON.parse(localStorage.getItem("user")),
      selectedLoan: { id: null }, // this to prevent a type error as edit booking modal is using this
      bookingRemarks: "",
      bookingDetails: {},
      isChecked: false,
      selectedPassId: null,
      selectedPass: null,
      isLoading: false,
      isValidityLoading: true,
      isSecondaryPassConflict: true,
      isPassSelected: false,
      secondPassId: null,
      numPasses: 1,
      componentKey: 0,
      availablePasses: [],
      retrievedData: {
        passID: null,
        userID: this.user.userId,
        startDate: "",
        endDate: "",
      },
    };
  },
  methods: {
    // this method gets the selected event information from calendar
    async checkSecondaryPass() {
      this.isSecondaryPassConflict =
        await LoanService.checkValidityOfSecondaryPass(
          this.retrievedData.passID,
          this.selectedPass.poi,
          this.retrievedData.startDate
        );
      this.isValidityLoading = false;
    },

    async checkSecondPass(primaryPassId, primaryPassPOI) {
      return await LoanService.checkSecondPass(primaryPassId, primaryPassPOI);
    },
    selectedData($event) {
      console.log(this.secondPassId);
      this.retrievedData = {
        passID: $event.passId.toString(),
        userID: $event.userId.toString(),
        startDate: $event.startDate,
        endDate: $event.endDate,
        secondaryPassID: this.numPasses > 1 ? this.secondPassId : null,
      };

      console.log(this.retrievedData.secondaryPassID);
      console.log(this.retrievedData.passID);
      if (this.numPasses == 2) {
        this.checkSecondaryPass();
      }
      this.isPassSelected = true;
      console.log(this.retrievedData);
    },
    async selectPass(event) {
      this.selectedPassId = event.target.value;
      this.selectedPass = this.availablePasses.find(
        (pass) => pass.passId == this.selectedPassId
      );

      let secondPass = await this.checkSecondPass(
        this.selectedPass.passId,
        this.selectedPass.poi
      );

      this.secondPassId = secondPass[0].passId;
    },
    selectNumPasses(event) {
      this.numPasses = event.target.value;
      this.getPassesData();
    },
    forceRerender() {
      this.componentKey += 1;
    },
    async submitBooking() {
      if (this.retrievedData.start == "") {
        alert("Please select a date");
        return;
      }
      if (this.isSecondaryPassConflict && this.numPasses == 2) {
        alert("Secondary pass is not valid for this date");
        return;
      }
      if (!this.isChecked) {
        alert("Please accept the terms and conditions");
        return;
      }
      this.isLoading = true;
      const response = await LoanService.createLoan(this.retrievedData);
      console.log(response);
      if (response.status == 201) {
        this.isLoading = false;
        document.getElementById("create-close-btn").click();
        this.$emit("bookingSubmitted", this.retrievedData);
        this.$emit("toastrMsg", {
          status: "Success",
          msg: "Booking is successful!",
        });
      } else {
        this.isLoading = false;
        document.getElementById("create-close-btn").click();
        this.$emit("toastrMsg", {
          status: "Failed",
          msg: "Booking is unsuccessful! Please try again.",
        });
      }
    },
    async getPassesData() {

        let response = await PassService.getAllPasses();
        this.availablePasses = response.data;

        if (this.numPasses == 2) {
        const response = await PassService.getAllPasses();
        let retrieveAllPassesPOI = response.data.map((data) => {
          return data.poi;
        });

        var dupl = retrieveAllPassesPOI.reduce(function (
          list,
          item,
          index,
          array
        ) {
          if (
            array.indexOf(item, index + 1) !== -1 &&
            list.indexOf(item) === -1
          ) {
            list.push(item);
          }
          return list;
        },
        []);
        console.log(retrieveAllPassesPOI);
        this.availablePasses = this.availablePasses.filter((data)=>{
          return dupl.includes(data.poi);
        })
      }
    },
  },
  mounted() {
    // fetch data from api
    this.getPassesData();
  },
  beforeCreate() {
    this.user = JSON.parse(localStorage.getItem("user"));
  },
};
</script>

<style>
.modal {
  --bs-modal-width: 50vw !important;
}
</style>