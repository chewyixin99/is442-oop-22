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
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body text-start">
          <form>
            <div class="col-md">
              <div class="mb-3 has-validation">
                <label for="employeeEmail" class="col-form-label"
                  >Employee Email</label
                >
                <input
                  type="text"
                  required
                  class="form-control"
                  id="employeeEmail"
                />
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
                    :value="availablePass.id"
                  >
                    {{ availablePass.title }}
                  </option>
                </select>
              </div>
            </div>

            <BookingCalendar
              :key="componentKey"
              :passId="passFn('5')"
              @selectedDates="selectedDates"
              :selectedPass="selectedPass"
              class="mt-4"
            />
          </form>
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
          >
            Close
          </button>
          <button
            v-if="modalType == 'create'"
            type="button"
            class="btn btn-primary"
            @click="createEmployee"
            data-bs-dismiss="modal"
          >
            Create
          </button>
          <button
            v-else
            type="button"
            class="btn btn-primary"
            @click="editEmployeeDetails"
            data-bs-dismiss="modal"
          >
            Save
          </button>
        </div>
      </div>
    </div>
</div>

</template>
<script>
import { Toast } from "bootstrap";
import BookingCalendar from "@/components/BookingCalendar.vue";
export default {
  name: "BookingModal",
  props: {
    modalType: String,
    bookingDetails: Object,
  },
  components: {
    BookingCalendar,
  },
  data() {
    return {
      selectedPassId: null,
      selectedPass: null,
      formInput: { startStr: "null" },
      calendarInput: {
        startStr: "null",
        endStr: "null",
      },
      pass: ["", ""],
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
      retrievedData: null,
    };
  },
  methods: {
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
    async createEmployee() {
      // const employee = await EmployeeService.createEmployee();
      // console.log(employee);
      var bsAlert = new Toast(document.getElementById("theToastr")); //inizialize it
      this.$emit("toastrMsg", "New employee has been created!");
      bsAlert.show();
    },
    async deleteRecords() {
      this.recordsToDelete.forEach((value, index) => {
        console.log("Index to delete:", index, "Id value:", value);
      });
      // const employees = await EmployeeService.removeEmployees();
      // console.log(employees);
    },
    async editEmployeeDetails() {
      var bsAlert = new Toast(document.getElementById("theToastr")); //inizialize it
      this.$emit("toastrMsg", "Your changes have been saved!");
      bsAlert.show();
    },
  },
};
</script>
<style>
.modal {
  --bs-modal-width: 50vw !important;
}
</style>