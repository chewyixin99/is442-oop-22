<template>
  <div class="container-fluid">
    <div class="" v-if="selectedPass != null">
      <h1>{{ selectedPass.poi }}</h1>
      <FullCalendar id="calendar" :options="calendarOptions" />
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
  props: ["selectedPass"],
  components: {
    FullCalendar,
  },
  data() {
    return {
      componentKey: 0,
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
        events: [
          
        ],
        droppable: true,
        eventDrop: this.handleDropChange,
      },
      userId: 1,
      selectedData: {
        userID: 0,
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
    handleDateClick: function (arg) {
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
            title: this.userId,
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
      axios
        .get("http://localhost:8081/loan")
        .then((response) => {
          console.log(response.data);
          console.log(this.selectedPass);
          this.selectedPassLoans = response.data.filter(
            (pass) => pass.passId == this.selectedPass.id
          );

          this.processData();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // for now just process the data into the format FullCalendar expects
    processDate1(date){
      let split = date.split("/").reverse()
      for (let i = 0; i < split.length; i++) {
        if (split[i].length == 1) {
          split[i] = "0" + split[i];
        }
      }
      return split.join("-");
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

    processData() {
      let tempList = []
      for (let i = 0; i < this.selectedPassLoans.length; i++) {
        console.log(this.selectedPassLoans[i]);
        let data = {
            id: this.selectedPassLoans[i].id,
            title: this.selectedPassLoans[i].userId,
            date: this.processDate1(this.selectedPassLoans[i].startDate),
            allDay: true,
            editable: false,
          };
        if (this.selectedPassLoans[i].userId == "1") {
          data.color = "#92f7a3"
        } 
        tempList.push(data);
      }

      this.calendarOptions.events = tempList;
          },

  },

  watch: {
    selectedPass: function (newVal, oldVal) {
      console.log(newVal);
      console.log(oldVal);
      this.selectedData.passID = this.selectedPass.id
      this.selectedData.userID = this.userId
      this.getData();
    },
  },

  mounted() {},
};
</script>

<style>
.form {
  height: 50vh;
  margin-top: 20px;
  padding: 20px;
  background-color: rgba(131, 163, 163, 0.116);
}
</style>
