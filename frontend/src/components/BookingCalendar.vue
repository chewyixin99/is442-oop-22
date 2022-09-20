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
            <input type="date" disabled :value="selectedStart">
          </div>
        </div>
        <div class="row mt-4">
          <div class="col">
            <label id="date">End Date</label>
            <input type="date" disabled :value="selectedEnd">
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
      selectedStart: new Date().toISOString().replace(/T.*$/, ''),
      selectedEnd: new Date().toISOString().replace(/T.*$/, ''),

    }
  },
    methods: {

    handleDateClick: function(arg) {
      alert('date click! ' + arg.dateStr)
    },
    handleDropChange(info){
      console.log(info.event.startStr);
      this.selectedStart = info.event.startStr
      this.selectedEnd = this.fullDayConversion(info.event.end)
    },
    handleDateSelect(selectInfo) {
      console.log(selectInfo);
      this.selectedStart = selectInfo.startStr
      this.selectedEnd = this.fullDayConversion(selectInfo.end)

        let calendarApi = selectInfo.view.calendar

        // calendarApi.unselect() // clear date selection
        // let title = prompt('Please enter a new title for your event')
        // alert(selectInfo.endStr)

        calendarApi.addEvent({
          id: 1,
          title: "test",
          start: selectInfo.startStr,
          end: selectInfo.endStr,
          allDay: selectInfo.allDay,
      })
      

      
      // let title = prompt('Please enter a new title for your event')
      // let calendarApi = selectInfo.view.calendar

      
      
      

      // if (title) {
      //   calendarApi.addEvent({
      //     id: 1,
      //     title,
      //     start: selectInfo.startStr,
      //     end: selectInfo.endStr,
      //     allDay: selectInfo.allDay
      //   })
      // }
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
    
  }
  
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
