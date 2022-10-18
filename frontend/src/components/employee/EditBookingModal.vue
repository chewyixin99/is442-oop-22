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
            id="create-close-btn"
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
              <div class="row gap-5">
                <div class="col text-end">
                  <span>Pass Name:</span>
                </div>
                <div class="col text-start">
                  <span>{{ rowData.passTitle }}</span>
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
                class="mt-4"
              />
              </div>
              <div class="d-flex text-center justify-content-center align-center mt-4" v-else>
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
             
              <div class="form-group">
                <div class="row">
                  <div class="col">
                    <label for="exampleFormControlInput1">Pass Type</label>
                    <input
                      type="email"
                      class="form-control"
                      id="exampleFormControlInput1"
                      :value="retrievedData.passTitle"
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
import axios from "axios";
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
      retrievedPassData: {}
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
        passID: $event.passID,
        userID: $event.userID,
        startDate: $event.startDate,
        endDate: $event.endDate,
      };
      console.log(this.retrievedData);
    },

    forceRerender() {
      this.componentKey += 1;
    },
    
    processDate2(date){
      
      let split = date.split("-").reverse()
      for (let i = 0; i < split.length; i++) {
        if (split[i].length == 1) {
          split[i] = "0" + split[i];
        }
      }
      return split.join("/");
    },
    async submitBooking() {

      this.retrievedData.startDate = this.processDate2(this.retrievedData.startDate)
      this.retrievedData.endDate = this.processDate2(this.retrievedData.endDate)


      this.isLoading = true;
      console.log(this.retrievedData)
      // axios
      //   .post("http://localhost:8081/loan", this.retrievedData)
      //   .then((response) => {
      //     if (response.status != 500) {
      //       this.isLoading = false;
      //       document.getElementById("create-close-btn").click();
      //       this.$emit("bookingSubmitted", this.retrievedData);
      //       this.$emit("toastrMsg", {
      //         status: "Success",
      //         msg: "Booking is successful!",
      //       });
      //     }
      //   })
      //   .catch((error) => {
      //     console.log(error);
      //   });
    },

    getData() {
      axios
        .get("http://localhost:8081/loan/" + this.rowData.id)
        .then((response1) => {
          this.retrievedLoanData = response1.data
          
          axios
            .get("http://localhost:8081/passes/" + response1.data.passId)
            .then((response2) => {
              console.log('response2', response2.data);
              setTimeout(() => {
              this.retrievedPassData = response2.data
              this.selectedPass = response2.data
              console.log("specific pass data updated ------------------")
            }, 1000);
            })
            .catch((error) => {
              console.log(error);
            });
          

        })
        .catch((error) => {
          console.log(error);
        });
    },
  },

  watch: {
    rowData: function (newVal, oldVal) {
      console.log(newVal);
      console.log(oldVal)
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