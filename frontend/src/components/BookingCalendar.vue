<template>
  <div class="container-fluid">
    <div class="" v-if="selectedPass != null">
      <h1>{{ selectedPass.title }}</h1>
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

export default {
  props: ["passId", "selectedPass"],
  components: {
    FullCalendar,
  },
  data() {
    return {
      componentKey: 0,
      calendarOptions: {
        plugins: [dayGridPlugin, interactionPlugin, timeGridPlugin],
        initialView: "dayGridMonth",
        initialEvents: this.passId.events,
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
          { id: "1", title: "event 1", date: "2022-10-16", editable: false },
          { id: "2", title: "event 2", date: "2022-10-17", editable: false },
        ],
        droppable: true,
        eventDrop: this.handleDropChange,
      },
      selectedDates: {
        start: new Date().toISOString().replace(/T.*$/, ""),
        end: new Date().toISOString().replace(/T.*$/, ""),
        passData: {
          passId: this.passId.id,
          passTitle: this.passId.title,
        },
      },
      selectedData: {
        userID: 0,
        selectedData: this.passId.id,
        startDate: "",
        endDate: "",
      },

      // need to retrieve user events
      userEvents: ["1"],
      clickedEventId: ""
    };
  },
  methods: {
    forceRerender() {
      this.componentKey += 1;
    },
    handleDateClick: function (arg) {
      if (arg.dateStr < new Date().toISOString().replace(/T.*$/, "")) {
        alert("Please select a date in the future!");
      } 
      else if (this.clickedEventId == ""){
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
            title: this.selectedPass.title,
            date: arg.dateStr,
            allDay: true,
            color:"#18c735"
          });
          this.selectedData.startDate,this.selectedData.endDate = arg.dateStr;
          this.userEvents.push("3");
          this.clickedEventId = "3"
          this.$emit("selectedData", this.selectedData);
        }
      }
    },
    handleDropChange(info) {
      console.log(info.event.startStr);
      this.selectedData.startDate = info.event.startStr;
      this.selectedData.endDate = info.event.startStr;
      this.$emit("selectedData", this.selectedData);

      // this.selectedDates.start = info.event.startStr;
      // this.selectedDates.end = this.fullDayConversion(info.event.end);
    },
    handleDateSelect(selectInfo) {
      console.log(selectInfo);
      // this.selectedDates.start = selectInfo.startStr;
      // this.selectedDates.end = this.fullDayConversion(selectInfo.end);
      // this.$emit("selectedDates", this.selectedDates);
      // let calendarApi = selectInfo.view.calendar;

      //   calendarApi.addEvent({
      //     id: 1,
      //     title: "test",
      //     start: selectInfo.startStr,
      //     end: selectInfo.endStr,
      //     allDay: selectInfo.allDay,
      // });
    },
    handleEventClick(clickInfo) {
      let eventId = clickInfo.event.id;
      console.log(eventId)
      // if (eventId == this.clickedEventId) {
      //   if (
      //     confirm(
      //       `Are you sure you want to delete the event '${clickInfo.event.title}'`
      //     )
      //   ) {
      //     clickInfo.event.remove();
      //   }
      //   else {
      //     return true
      //   }
      // }
    },

    // fullDayConversion(endDate) {
    //   endDate.setMinutes(endDate.getMinutes() - 1);

    //   return endDate.toISOString().replace(/T.*$/, ""); // YYYY-MM-DD of today
    // },
  },

  watch: {
    selectedPass: function (newVal, oldVal) {
      console.log(newVal);
      console.log(oldVal);
    },
  },

  mounted() {
    let events = this.calendarOptions.events
    for (let i = 0; i < events.length; i++) {
      if (this.userEvents.includes(events[i].id)) {
        events[i].color = "#18c735";
      }
    }

  },
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
