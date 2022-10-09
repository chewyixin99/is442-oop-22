<template>
  <div class="container-fluid">
    <div class="w-60 p-4 flex-column justify-content-center">
      <h1 class="pt-4 mb-4">Booking</h1>

      <h4 class="pt-4">Make New Booking</h4>
      <p class="text-muted fs-7">You have 2 loans left this month</p>
      <!-- <form>
        <label>Number of pass(es): </label>
       <select v-model.number="numPass">
          <option value="1">1</option>
          <option value="2">2</option>
        </select>
              </form> -->
      <!-- add button -->
      <button type="button" class="btn btn-info" @click="addPass">Add</button>


      <div class="" v-for="index in numPass" :key="index">
        <div class="formBox pt-4">
          <form class="pt-4">
            <label>Select Pass: </label>
            <select v-model="pass[index]" @change="selectedPass($event)">
              <option
                v-for="availablePass in availablePasses"
                :key="availablePass"
              >
                {{ availablePass.name }}
              </option>
            </select>
          </form>
        </div>
        <div class="d-flex justify-content-center gap-5">
          <BookingCalendar
            :key="componentKey"
            :passId="passFn('1')"
            @updateDates="selectedDates"
            class="mt-4"
            v-if="pass[index] == '1'"
          />
          <BookingCalendar
            :key="componentKey"
            :passId="passFn('2')"
            @updateDates="selectedDates"
            class="mt-4"
            v-if="pass[index] == '2'"
          />
          <BookingCalendar
            :key="componentKey"
            :passId="passFn('5')"
            @updateDates="selectedDates"
            class="mt-4"
            v-if="pass[index] == '5'"
          />
          <!-- <BookingCalendar :passId="passFn('6')" class="mt-4" v-if="pass == '6'"/> -->
          <h1 v-if="pass == '6'">Temporarily Unavailable</h1>
          <!-- <BookingForm :calendarInput="calendarInput" @formOutput="callbackForm($event)"/> -->
        </div>
      </div>

      <button type="submit" @click="forceRerender()">Submit</button>
    </div>

    <div
      id="toast"
      class="toast fade position-fixed bottom-0 right-0"
      role="alert"
      aria-live="assertive"
      aria-atomic="true"
    >
      <div class="toast-header">
        <svg
          class="bd-placeholder-img rounded me-2"
          width="20"
          height="20"
          xmlns="http://www.w3.org/2000/svg"
          aria-hidden="true"
          preserveAspectRatio="xMidYMid slice"
          focusable="false"
        >
          <rect width="100%" height="100%" fill="#007aff"></rect>
        </svg>

        <strong class="me-auto">Loan Success!</strong>
        <small>Just now</small>
        <button
          type="button"
          class="btn-close"
          data-bs-dismiss="toast"
          aria-label="Close"
        ></button>
      </div>
      <div class="toast-body">This is supposed to be on the right</div>
    </div>
  </div>
</template>
<script>
import BookingCalendar from "@/components/BookingCalendar.vue";

export default {
  name: "NewBooking",
  data() {
    return {
      formInput: { startStr: "null" },
      calendarInput: {
        startStr: "null",
        endStr: "null",
      },
      pass: ["", ""],
      numPass: 2,
      componentKey: 0,
      successFlag: false,
      retrievedData: [],
      availablePasses: [
        {
          id: "1",
          name: "Zoo 1",
          selected: false,
        },
        {
          id: "2",
          name: "Zoo 2",
          selected: false,
        },
        {
          id: "3",
          name: "Zoo 3",
          selected: false,
        },
        {
          id: "4",
          name: "Safari 1",
          selected: false,
        },
        {
          id: "5",
          name: "Safari 2",
          selected: false,
        },
        {
          id: "6",
          name: "Gardens By The Bay",
          selected: false,
        },
      ],
    };
  },
  components: {
    BookingCalendar,
    // BookingForm
  },
  methods: {
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
    forceRerender() {
      this.pass = "";
      this.componentKey += 1;
      this.successFlag = true;
      alert(JSON.stringify(this.retrievedData));
      document.getElementById("toast").classList.add("show");
    },
  },
};
</script>

<style>
.bookingList {
  padding: 7%;
}
</style>
