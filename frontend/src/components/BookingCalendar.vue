<template>
    <div class="w-50 d-flex flex-column">  
      <div class="">
        <h1>{{passId.title}}</h1>
        <FullCalendar id="calendar" :options="calendarOptions" />
      </div>

      <div class="form container">
        <div class="row">
          <div class="col">
            <label id="date">Start Date</label>
            <input type="date" disabled :value="selectedDates.start">
          </div>
        </div>
        <div class="row mt-4">
          <div class="col">
            <label id="date">End Date</label>
            <input type="date" disabled :value="selectedDates.end">
          </div>
        </div>
        
        <div class="row mt-4">
          <div class="col">
            <label>Staff Name</label>
            <input type="text" placeholder="">
          </div>
          <div class="col">
            <label>Staff ID</label>
            <input type="text" placeholder="">
          </div>
        </div>
        <div class="row mt-4">
          <div class="col">
            <label>Guest Name</label>
            <input type="text" placeholder="">
          </div>
           <div class="col">
            <label>Guest ID</label>
            <input type="text" placeholder="">
          </div>
        </div>
          <div class="row mt-4">
          <div class="col">
            <label>Guest Name</label>
            <input type="text" placeholder="">
          </div>
           <div class="col">
            <label>Guest ID</label>
            <input type="text" placeholder="">
          </div>
        </div>
        <div class="row mt-4">
          <div class="col">
            <label>Guest Name</label>
            <input type="text" placeholder="">
          </div>
           <div class="col">
            <label>Guest ID</label>
            <input type="text" placeholder="">
          </div>
        </div>


      </div>
      
    </div>

</template>

<script>
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import timeGridPlugin from '@fullcalendar/timegrid'
import interactionPlugin from '@fullcalendar/interaction'
// import { INITIAL_EVENTS } from './event-utils'

export default {

  props: ["passId"],
  components: {
    FullCalendar,

  },
  data() {
    return {
      calendarOptions: {
        plugins: [ dayGridPlugin, interactionPlugin,timeGridPlugin ],
        initialView: 'dayGridMonth',
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
        eventDrop: this.handleDropChange
      },
      selectedDates: {
        start: new Date().toISOString().replace(/T.*$/, ''),
        end: new Date().toISOString().replace(/T.*$/, ''),
        passData: {
          passId : this.passId.id,
          passTitle: this.passId.title
        }
      }
     

    }
  },
    methods: {

    handleDateClick: function(arg) {
      alert('date click! ' + arg.dateStr)
    },
    handleDropChange(info){
      console.log(info.event.startStr);
      this.selectedDates.start = info.event.startStr
      this.selectedDates.end = this.fullDayConversion(info.event.end)
    },
    handleDateSelect(selectInfo) {
      console.log(selectInfo);
      this.selectedDates.start = selectInfo.startStr
      this.selectedDates.end = this.fullDayConversion(selectInfo.end)
      this.$emit('updateDates',  this.selectedDates)
      let calendarApi = selectInfo.view.calendar


        calendarApi.addEvent({
          id: 1,
          title: "test",
          start: selectInfo.startStr,
          end: selectInfo.endStr,
          allDay: selectInfo.allDay,
      })
      

    },
    handleEventClick(clickInfo) {
      if (confirm(`Are you sure you want to delete the event '${clickInfo.event.title}'`)) {
        clickInfo.event.remove()
      }
    },

    fullDayConversion(endDate){

    endDate.setMinutes(endDate.getMinutes() - 1)

    return endDate.toISOString().replace(/T.*$/, '') // YYYY-MM-DD of today
  },
    
  },

}
</script>

<style>

.form {
  height: 50vh;
  margin-top: 20px;
  padding: 20px;
  background-color: rgba(131, 163, 163, 0.116);
}
</style>
