<template>
    <div class="">
        <div class="w-100 flex-column justify-content-center">
          <h1 class="pt-4">Loan</h1>
          <h3>You have 2 loans left this month</h3>
          <label >Number of Pass</label>
          <select v-model.number="numPass">
                <option value="1">1</option>
                <option value="2">2</option>
              </select>
          <div class="" v-for="index in numPass" :key="index">
            <div class="formBox pt-4">
              <form class="pt-4">
                <label>Select Pass</label>
                <select v-model="pass">
                  <option value="1">Zoo 1</option>
                  <option value="2">Zoo 2</option>
                  <option value="3" disabled>Zoo 3</option>
                  <option value="4" disabled>Safari 1</option>
                  <option value="5">Safari 2</option>
                  <option value="6">Gardens By The Bay 1</option>
                </select>
              </form>
            </div>
            <div class="d-flex justify-content-center gap-5">
            
            <BookingCalendar :key="componentKey" :passId="passFn('1')" class="mt-4" v-if="pass == '1'"/>
            <BookingCalendar :key="componentKey" :passId="passFn('2')" class="mt-4" v-if="pass == '2'"/>
            <BookingCalendar :key="componentKey" :passId="passFn('5')" class="mt-4" v-if="pass == '5'"/>
            <!-- <BookingCalendar :passId="passFn('6')" class="mt-4" v-if="pass == '6'"/> -->
            <h1 v-if="pass == '6'">Temporarily Unavailable</h1>
            <!-- <BookingForm :calendarInput="calendarInput" @formOutput="callbackForm($event)"/> -->
          </div>
          </div>


          <button type="submit" @click="forceRerender()">Submit</button>
            
        </div>
        <!-- <h1>Booking</h1>
        ------------------------------------
        <p>Staff functionalities:</p>
        ------------------------------------
        <p>1. View current and past bookings</p>
        <p>2. Create new bookings</p>
        <p>3. Edit/cancel current booking</p>
        <p>4. View number of bookings count left</p>
        <p>5. View contact info of previous booker</p>
        <p>6. View physical pass status: Issued/Available/Unavailable</p>
        ------------------------------------
        <p>Admin functionalities:</p>
        ------------------------------------
        <p>1. View current and pass bookings</p>
        <p>2. Create new bookings</p>
        <p>3. Edit/cancel current booking</p>
        <p>4. View number of bookings count left</p>
        <p>5. View contact info of previous booker</p>
        <p>6. View current and past staff booking - booking log</p>
        <p>7. Create new booking for staff</p>
        <p>8. Edit/cancel current booking for staff</p>
        <p>9. View number of bookings count left for staff</p>
        <p>10. View physical pass status: Issued/Available/Unavailable</p>
        <p>11. Edit physical pass status: Issued/Available/Unavailable</p>
        ------------------------------------
        <p>GOP functionalities:</p>
        ------------------------------------
        <p>1. View current and pass staff booking - booking log</p>
        <p>2. Edit physical pass status: Issued/Available/Unavailable</p>    

        <p>1. booking form</p>
        <p>2. coloured booked dates</p>
        <p>3. conflicted dates</p>
        <p>4. pass status for current booking</p> -->



        </div>
</template>
<script>
import BookingCalendar from '../components/BookingCalendar.vue'
// import BookingForm from '../components/BookingForm.vue'

export default {
  name: 'Booking',
  data(){
    return {
      formInput : {startStr: "null"},
      calendarInput: {
        startStr: "null",
        endStr: "null"
        },
      pass: "",
      numPass: 2,
      componentKey: 0,
      
    }
  },
  components: {
    BookingCalendar,
    // BookingForm
  },
  methods: {
    passFn(id){
      if (id == '1'){
        return {
          title: "Zoo 1",
          id: '1',
          events: [{
            id: 1,
            title: "Team 1",
            start: new Date(2022,8,29).toISOString().replace(/T.*$/, ''),
          },
          {
            id: 2,
            title: "Team 3",
            start: new Date(2022,8,30).toISOString().replace(/T.*$/, ''),
          }]
        }
      }
      else if (id == '2'){
        return {
          title: "Zoo 2",
          id: '2',
          events: [{
            id: 1,
            title: "Finance Dept",
            start: new Date(2022,8,24).toISOString().replace(/T.*$/, ''),
          },
          {
            id: 2,
            title: "Teaching Dept",
            start: new Date(2022,8,26).toISOString().replace(/T.*$/, ''),
          }]
        }
      }
      else if (id == '5'){
        return {
          title: "Safari 2",
          id: '5'
        }
      }
      else if (id == '6'){
        return {
          title: "Gardens By The Bay",
          id: '6'
        }
      }
    },
    forceRerender() {
      this.pass = ""
      this.componentKey += 1;
    }

  },
      
}

</script>

<style>

</style>
