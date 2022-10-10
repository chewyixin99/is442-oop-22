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
        // events: [
        //   { title: 'event 1', date: '2022-09-01' },
        //   { title: 'event 2', date: '2022-09-02' }
        // ],
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
    };
  },
    methods: {
    forceRerender() {
      this.componentKey += 1;
    },
    handleDateClick: function (arg) {
      alert("date click! " + arg.dateStr);
    },
    handleDropChange(info) {
      console.log(info.event.startStr);
      this.selectedDates.start = info.event.startStr;
      this.selectedDates.end = this.fullDayConversion(info.event.end);
    },
    handleDateSelect(selectInfo) {
      this.selectedDates.start = selectInfo.startStr;
      this.selectedDates.end = this.fullDayConversion(selectInfo.end);
      this.$emit("selectedDates", this.selectedDates);
      let calendarApi = selectInfo.view.calendar;

        calendarApi.addEvent({
          id: 1,
          title: "test",
          start: selectInfo.startStr,
          end: selectInfo.endStr,
          allDay: selectInfo.allDay,
      });
    },
    handleEventClick(clickInfo) {
      if (
        confirm(
          `Are you sure you want to delete the event '${clickInfo.event.title}'`
        )
      ) {
        clickInfo.event.remove();
      }
    },

    fullDayConversion(endDate) {
      endDate.setMinutes(endDate.getMinutes() - 1);

      return endDate.toISOString().replace(/T.*$/, ""); // YYYY-MM-DD of today
  },
  },

  watch: {
    selectedPass: function (newVal, oldVal) {
      console.log(newVal);
      console.log(oldVal);
    },
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
