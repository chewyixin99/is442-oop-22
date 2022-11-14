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
                    {{ availablePass.poi }}
                  </option>
                </select>
              </div>
            </div>
            <div class="p-4">
              <BookingCalendar
                :key="componentKey"
                @selectedData="selectedData"
                :selectedPass="selectedPass"
                :selectedLoan="selectedLoan"
                class="mt-4"
              />
            </div>

            <div class="mt-4" v-if="selectedPass">
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
              <!-- <hr /> -->

              <!-- Booking Details Form start --------------------------------------------------- -->

              <!-- DO NOT DELETE IN CASE IT IS A BUSINESS REQUIREMENT  -->
              <!-- <div class="form-group">
                <div class="d-flex justify-content-between align-items-top">
                  <h4>Guest Details</h4>
                  <i
                    class="bi bi-plus fs-1"
                    style="cursor: pointer"
                    @click="addNewGuest"
                  ></i>
                </div>

                <label for="exampleFormControlSelect1">Number of Guest</label>
                <select
                  class="form-control"
                  id="exampleFormControlSelect1"
                  v-model.number="numOfGuest"
                  @change="updateGuestNum"
                >
                  <option value="1">1</option>
                  <option value="2">2</option>
                  <option value="3">3</option>
                </select> 
              </div>
              <div class="form-group">
                <div
                  class="row mt-3"
                  v-for="(detail, index) in bookingGuestDetails"
                  :key="detail"
                >
                  <div class="col">
                    <label for="exampleFormControlInput1">Name</label>
                    <input
                      type="text"
                      class="form-control"
                      id="exampleFormControlInput1"
                      v-model="detail.name"
                    />
                  </div>
                  <div class="col">
                    <label for="exampleFormControlInput1">Email address</label>
                    <input
                      type="email"
                      class="form-control"
                      id="exampleFormControlInput1"
                      v-model="detail.email"
                    />
                  </div>
                  <div class="col">
                    <label for="exampleFormControlInput1">Contact Number</label>
                    <input
                      type="text"
                      class="form-control"
                      id="exampleFormControlInput1"
                      v-model="detail.contact"
                    />
                  </div>
                  <div class="col-1 d-flex align-items-end">
                    <i
                      class="bi bi-trash fs-5"
                      style="cursor: pointer"
                      @click.stop="removeGuest($event, index)"
                    ></i>
                  </div>
                </div>
                <div
                  class="d-flex justify-content-center text-center"
                  v-if="bookingGuestDetails.length < 1"
                >
                  No guest selected.
                </div>
                <br />
              </div>
              <hr /> -->

              <!-- Booking Details Form end --------------------------------------------------- -->

              <div class="form-group">
                <!-- <h4>Booking Details</h4> -->
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
                :disabled="!isChecked || retrievedData.start == ''"
                @click.stop="submitBooking"
                style="min-width: 100px"
              >
                <div class="" v-if="!isLoading">Submit</div>
                <div
                  class="spinner-border spinner-border-sm text-light"
                  v-else
                  role="status"
                >
                  <span class="visually-hidden">Loading...</span>
                </div>
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
import axios from "axios";
import ENDPOINT from '../../constants';
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
      retrievedData: {
        passID: null,
        userID: 0,
        start: "",
        end: "",
      },
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
        passID: $event.passID.toString(),
        userID: $event.userID.toString(),
        startDate: $event.startDate,
        endDate: $event.endDate,
      };
      console.log(this.retrievedData);
    },
    selectPass(event) {
      console.log(event.target.value);
      console.log(this.availablePasses);
      this.selectedPassId = event.target.value;
      this.selectedPass = this.availablePasses.find(
        (pass) => pass.passId == this.selectedPassId
      );

      console.log(this.selectedPass);
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
      console.log(date);
      return split.join("/");
    },
    async submitBooking() {
      this.isLoading = true;
      
      axios
        .post(`${ENDPOINT}/loan`, this.retrievedData)
        .then((response) => {
          console.log(response);
          setTimeout(() => {
            this.isLoading = false;
            document.getElementById("create-close-btn").click();
            this.$emit("bookingSubmitted", this.retrievedData);
            this.$emit("toastrMsg", {
              status: "Success",
              msg: "Booking is successful!",
            });
          }, 1000);
        })
        .catch((error) => {
          console.log(error);
        });
    },

    getData() {
      const bearer_token = `Bearer ${localStorage.getItem("token")}`;
      const config = {
        headers: {
          Authorization: bearer_token,
        },
      };
      axios
        .get(`${ENDPOINT}/passes`, config)
        .then((response) => {
          this.availablePasses = response.data.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  mounted() {
    // fetch data from api
    this.getData();
  },
};
</script>
<style>
.modal {
  --bs-modal-width: 50vw !important;
}
</style>