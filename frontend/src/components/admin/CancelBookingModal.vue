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
      <div class="modal-content cancel-modal">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">
            <p class="m-0">Cancel Booking</p>
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
          <!-- <h4>Confirm Cancel?</h4> -->
          <!-- <div class="" v-for="data in dataOfSelectedRow" :key="data">
            <div class="row gap-5">
              <div class="col text-end">
                <span>Loan ID:</span>
              </div>
              <div class="col text-start">
                <span>{{ data.loanId }}</span>
              </div>
            </div>
             <div class="row gap-5">
              <div class="col text-end">
                <span>Pass Title:</span>
              </div>
              <div class="col text-start">
                <span>{{ data.pass?.poi }}</span>
              </div>
            </div>
            <div class="row gap-5">
              <div class="col text-end">
                <span>Staff Email:</span>
              </div>
              <div class="col text-start">
                <span>{{ user.email }}</span>
              </div>
            </div>
            <div class="row gap-5">
              <div class="col text-end">
                <span>Start Date:</span>
              </div>
              <div class="col text-start">
                {{ data.startDate }}
              </div>
            </div>
            <div class="row gap-5">
              <div class="col text-end">
                <span>End Date:</span>
              </div>
              <div class="col text-start">
                {{ data.endDate }}
              </div>
            </div>
            <hr />
          </div> -->
          <p>Confirm cancel?</p>
          <div class="row">
            <div class="col text-end">
              <button
                class="btn btn-danger"
                style="min-width: 100px"
                @click.stop="cancelSubmitted"
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
    dataOfSelectedRow: {
      type: Array,
    },
  },
  components: {},
  data() {
    return {
      user: JSON.parse(localStorage.getItem("user")),
      isLoading: false,
    };
  },
  methods: {
    cancelSubmitted() {
      const bearer_token = `Bearer ${localStorage.getItem("token")}`;
      const config = {
        headers: {
          Authorization: bearer_token,
        },
      };
      let isAllDeleted = true;
      this.isLoading = true;
      this.dataOfSelectedRow.map((data) => {
        axios
          .delete(`http://localhost:8081/loan/${data.loanId}`,config)
          .then((res) => {
            console.log(res);
          })
          .catch((err) => {
            isAllDeleted = false;
            console.log(err);
          });
      });

      if (isAllDeleted) {
        document.getElementById("cancel-close-btn").click();
        this.$emit("cancelSubmitted", true);

        this.$emit("toastrMsg", {
          status: "Success",
          msg: "Cancellation is successful!",
        });
      } else {
        this.$emit("toastrMsg", {
          status: "Error",
          msg: "Cancellation is unsuccessful!",
        });
      }
    },
    clickedCancel() {
      document.getElementById("cancel-close-btn").click();
    },
  },
  mounted() {
    console.log(this.dataOfSelectedRow);
  
  },
};
</script>

<style>
.cancel-modal{
  max-width: 50%;
}
</style>