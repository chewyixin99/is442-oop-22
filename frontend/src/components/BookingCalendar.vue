<template>
  <div class="container-fluid">
    <div class="" v-if="selectedPass != null">
      <h1>{{ selectedPass.poi }}</h1>
      <FullCalendar id="calendar" :options="calendarOptions" />
      <br>
      <div class="row">
        <div class="col d-flex">
          <div class="circle-legend-other" />
          <span class="align-items-center d-flex ms-2">Existing Bookings</span>
        </div>
        <div class="col d-flex">
          <div class="circle-legend-past" />
          <span class="align-items-center d-flex ms-2">Your Existing Bookings</span>
        </div>
        <div class="col d-flex">
          <div class="circle-legend-new" />
          <span class="align-items-center d-flex ms-2">Your Selected Booking</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
// import { INITIAL_EVENTS } from './event-utils'
import axios from "axios";

export default {
  props: ["selectedPass", "selectedLoan", "isEdit"],
  components: {
    FullCalendar,
  },
  data() {
    return {
      user: JSON.parse(localStorage.getItem("user")),
      componentKey: 0,
      isEditing: false,
      calendarOptions: {
        plugins: [dayGridPlugin, interactionPlugin, timeGridPlugin],
        initialView: "dayGridMonth",
        // initialEvents: this.passId.events,
        editable: true,
        selectable: true,
        selectMirror: true,
        dayMaxEvents: true,
        weekends: true,
        select: this.handleDateSelect,
        eventClick: this.handleEventClick,
        eventsSet: this.handleEvents,
        dateClick: this.handleDateClick,
        changeInfo: this.handleChange,
        selectOverlap: false,
        eventOverlap: false,
        events: [],
        droppable: true,
        eventDrop: this.handleDropChange,
      },
      userId: this.user.userId,
      selectedData: {
        userID: this.userId,
        passID: 0,
        startDate: "",
        endDate: "",
      },
      clickedEventId: "",
      selectedPassLoans: [],
    };
  },
  methods: {
    forceRerender() {
      this.componentKey += 1;
    },
    handleDrop(info) {
      console.log(info);
    },
    handleDateClick: function (arg) {
      if (this.isEdit) {
        console.log("edit mode");
        return;
      }
      if (arg.dateStr < new Date().toISOString().replace(/T.*$/, "")) {
        alert("Please select a date in the future!");
      } else if (this.clickedEventId == "") {
        console.log(arg.view.calendar.events);

        this.selectedData.startDate = arg.dateStr;
        this.selectedData.endDate = arg.dateStr;
        let calendarApi = arg.view.calendar;
        let existingEvents = this.calendarOptions.events;
        if (
          existingEvents.filter((event) => event.date === arg.dateStr).length >
          0
        ) {
          alert("There is already a loan on this date!");
        } else {
          calendarApi.addEvent({
            id: "3",
            title: this.user.username,
            date: arg.dateStr,
            allDay: true,
            color: "#18c200",
          });
          this.clickedEventId = "3";
          console.log(this.selectedData);
          this.$emit("selectedData", this.selectedData);
        }
      }
    },
    handleDropChange(info) {
      console.log(info.event.startStr);
      // function to stop event from dropping at a past date
      if (info.event.startStr < new Date().toISOString().replace(/T.*$/, "")) {
        alert("Please select a date in the future!");
        info.revert();
      }
      this.selectedData.startDate = info.event.startStr;
      this.selectedData.endDate = info.event.startStr;
      this.$emit("selectedData", this.selectedData);
    },
    handleDateSelect(selectInfo) {
      console.log(selectInfo);
    },
    handleEventClick(clickInfo) {
      let eventId = clickInfo.event.id;
      console.log(eventId);
    },

    // retrieve data, process it, and set it as the events source
    getData() {
      const bearer_token = `Bearer ${localStorage.getItem("token")}`;
      const config = {
        headers: {
          Authorization: bearer_token,
        },
      };
      axios
        .get("http://localhost:8081/loan", config)
        .then((response) => {
          console.log(response);
          this.selectedPassLoans = response.data.data.filter(
            (pass) =>
              pass.pass.passId == this.selectedPass.passId &&
              pass.defunct == false
          );

          console.log(this.selectedPassLoans);

          this.processData();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // for now just process the data into the format FullCalendar expects
    processDate1(date) {
      let split = date.split("/").reverse();
      for (let i = 0; i < split.length; i++) {
        if (split[i].length == 1) {
          split[i] = "0" + split[i];
        }
      }
      return split.join("-");
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

    processData() {
      console.log("in process data");
      let tempList = [];
      for (let i = 0; i < this.selectedPassLoans.length; i++) {
        let data = {
          id: this.selectedPassLoans[i].pass.passId,
          title: this.selectedPassLoans[i].user.username,
          date: this.selectedPassLoans[i].startDate,
          allDay: true,
        };

        // need to account for various accounts and users here

        if (
          this.selectedPassLoans[i].user.userId == this.user.userId &&
          this.selectedPassLoans[i].loanId != this.selectedLoan.id
        ) {
          data.color = "#96f2a6";
          data.editable = false;
        } else if (
          this.selectedPassLoans[i].user.userId == this.user.userId &&
          this.selectedPassLoans[i].loanId == this.selectedLoan.id
        ) {
          console.log(this.selectedPassLoans[i]);
          console.log(this.selectedLoan.id);
          data.color = "#18c200";
          data.editable = true;
        } else {
          data.color = "#92c5f7";
          data.editable = false;
        }
        tempList.push(data);
      }

      this.calendarOptions.events = tempList;
    },
  },

  // this is for creation of new booking as selectedPass is based on user input
  watch: {
    selectedPass: function (newVal, oldVal) {
      console.log(newVal);
      console.log(oldVal);
      console.log("selectedPass updated ------------------");
      console.log(this.selectedPass);
      this.selectedData.passID = this.selectedPass.passId;
      this.selectedData.userID = this.userId;
      this.getData();
    },
  },
  // this is for editing of exisiing booking as selectedPass is based on rowData
  mounted() {
    if (this.isEdit) {
      this.getData();
      this.selectedData.passID = this.selectedPass.passId;
      this.selectedData.userID = this.userId;
    }
  },
  beforeCreate() {
    this.user = JSON.parse(localStorage.getItem("user"));
  },
};
</script>

<style>
.circle-legend-other {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: #92c5f7;
}

.circle-legend-past {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: #96f2a6;
}

.circle-legend-new {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: #18c200;
}

.form {
  height: 50vh;
  margin-top: 20px;
  padding: 20px;
  background-color: rgba(131, 163, 163, 0.116);
}
</style>
