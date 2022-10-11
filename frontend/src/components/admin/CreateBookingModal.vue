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
                    :value="availablePass.id"
                  >
                    {{ availablePass.title }}
                  </option>
                </select>
              </div>
            </div>
            <div class="p-4">
              <BookingCalendar
                :key="componentKey"
                :passId="passFn('5')"
                @selectedDates="selectedDates"
                :selectedPass="selectedPass"
                class="mt-4"
              />
            </div>

            <div class="mt-4" v-if="selectedPass">
              <div class="form-group">
                <h4>My Details</h4>
                <div class="row my-4">
                  <div class="col">
                    <label for="exampleFormControlInput1">Email address</label>
                    <input
                      type="email"
                      class="form-control"
                      id="exampleFormControlInput1"
                      placeholder="aloysius@sss.edu.sg"
                      disabled
                    />
                  </div>
                  <div class="col">
                    <label for="exampleFormControlInput1">Staff ID</label>
                    <input
                      type="text"
                      class="form-control"
                      id="exampleFormControlInput1"
                      placeholder="1525"
                      disabled
                    />
                  </div>
                  <div class="col">
                    <label for="exampleFormControlInput1">Contact Number</label>
                    <input
                      type="text"
                      class="form-control"
                      id="exampleFormControlInput1"
                      placeholder="84623411"
                      disabled
                    />
                  </div>
                </div>
              </div>
              <hr />
              <div class="form-group">
                <div class="d-flex justify-content-between align-items-top">
                  <h4>Guest Details</h4>
                  <i
                    class="bi bi-plus fs-1"
                    style="cursor: pointer"
                    @click="addNewGuest"
                  ></i>
                </div>

                <!-- <label for="exampleFormControlSelect1">Number of Guest</label>
                <select
                  class="form-control"
                  id="exampleFormControlSelect1"
                  v-model.number="numOfGuest"
                  @change="updateGuestNum"
                >
                  <option value="1">1</option>
                  <option value="2">2</option>
                  <option value="3">3</option>
                </select> -->
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
              <hr />
              <div class="form-group">
                <h4>Booking Details</h4>
                <div class="row">
                  <div class="col">
                    <label for="exampleFormControlInput1">Pass Type</label>
                    <input
                      type="email"
                      class="form-control"
                      id="exampleFormControlInput1"
                      :value="selectedPass.title"
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
                      :value="retrievedData.start"
                      disabled
                    />
                  </div>
                  <div class="col">
                    <label for="exampleFormControlInput1">End Date</label>
                    <input
                      type="date"
                      class="form-control"
                      id="exampleFormControlInput1"
                      :value="retrievedData.end"
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
import BookingCalendar from "@/components/BookingCalendar.vue";
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
    async submitBooking() {
      console.log(this.bookingGuestDetails);

      this.isLoading = true;
      setTimeout(() => {
        document.getElementById("create-close-btn").click();
        this.bookingDetails = {
          passData: {
            passId: this.retrievedData.passId,
            passTitle: this.retrievedData.passTitle,
          },
          duration: {
            start: this.retrievedData.start,
            end: this.retrievedData.end,
          },
          remarks: this.bookingRemarks,
          guestData: this.bookingGuestDetails,
          timeStamp: new Date().toISOString(),
        };
        this.$emit("bookingSubmitted", this.bookingDetails);

        this.$emit("toastrMsg", {
          status: "Success",
          msg: "Booking is successful!",
        });
      }, 1000);
    },
  },
};
</script>
<style>
.modal {
  --bs-modal-width: 50vw !important;
}
</style>