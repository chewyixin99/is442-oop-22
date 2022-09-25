<template>
    <div class="container-fluid">
        <div class="w-60 flex-column justify-content-center">
          <h1 class="pt-4 mb-4">Booking</h1>
          <h4 class="pt-4">Current Bookings</h4>
          <table class="table border">
            <thead>
              <tr>
                <th scope="col">#</th>
                <th scope="col">Pass Title</th>
                <th scope="col">Start Date</th>
                <th scope="col">End Date</th>
                <th scope="col">Previous Booker</th>
                <th scope="col">Following Booker</th>
                <th scope="col">Action</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <th scope="row">1</th>
                <td>Zoo 1</td>
                <td>25/10/2022</td>
                <td>26/10/2022</td>
                <td>Bob, 81723432</td>
                <td>-</td>
                <td>
                  <button class="btn btn-danger">Cancel</button>
                </td>
              </tr>
              <tr>
                <th scope="row">2</th>
                <td>Safari 1</td>
                <td>30/10/2022</td>
                <td>31/10/2022</td>
                <td>-</td>
                <td>Ted, 93748657</td>
                <td>
                  <button class="btn btn-danger">Cancel</button>
                </td>
              </tr>
              <tr>
                <th scope="row">3</th>
                <td>Zoo 2</td>
                <td>25/11/2022</td>
                <td>27/11/2022</td>
                <td>May, 83768532</td>
                <td>Mark, 93748231</td>
                <td>
                  <button class="btn btn-danger">Cancel</button>
                </td>
              </tr>
            </tbody>
          </table>
          <h4 class="pt-4">Make New Booking</h4>
          <p class="text-muted fs-7">You have 2 loans left this month</p>
          <form>
          <label>Number of pass(es): </label>
          <select v-model.number="numPass">
              <option value="1">1</option>
              <option value="2">2</option>
          </select>
          </form>
          <div class="" v-for="index in numPass" :key="index">
            <div class="formBox pt-4">
              <form class="pt-4">
                <label>Select Pass: </label>
                <select v-model="pass[index]">
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
            
            <BookingCalendar :key="componentKey" :passId="passFn('1')" @updateDates="selectedDates" class="mt-4" v-if="pass[index] == '1'"/>
            <BookingCalendar :key="componentKey" :passId="passFn('2')" @updateDates="selectedDates" class="mt-4" v-if="pass[index] == '2'"/>
            <BookingCalendar :key="componentKey" :passId="passFn('5')" @updateDates="selectedDates" class="mt-4" v-if="pass[index] == '5'"/>
            <!-- <BookingCalendar :passId="passFn('6')" class="mt-4" v-if="pass == '6'"/> -->
            <h1 v-if="pass == '6'">Temporarily Unavailable</h1>
            <!-- <BookingForm :calendarInput="calendarInput" @formOutput="callbackForm($event)"/> -->
          </div>
          </div>


          <button type="submit" @click="forceRerender()">Submit</button>

            
        </div>

        <h1 style="margin-top: 100vh">Booking</h1>
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
        <p>4. pass status for current booking</p>


        <div id="toast" class="toast fade position-fixed bottom-0 right-0" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header">
        <svg class="bd-placeholder-img rounded me-2" width="20" height="20" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" preserveAspectRatio="xMidYMid slice" focusable="false"><rect width="100%" height="100%" fill="#007aff"></rect></svg>
    
        <strong class="me-auto">Loan Success!</strong>
        <small>Just now</small>
        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
      </div>
      <div class="toast-body">
        This is supposed to be on the right
      </div>
    </div>

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
      pass: ["",""],
      numPass: 2,
      componentKey: 0,
      successFlag: false,
      retrievedData: []
    }
  },
  components: {
    BookingCalendar,
    // BookingForm
  },
  methods: {
    selectedDates($event){
      console.log($event);
      this.retrievedData.push({
        passId: $event.passData.passId,
        passTitle: $event.passData.passTitle,
        start: $event.start,
        end: $event.end
      })

    },
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
      this.successFlag = true;
      alert(JSON.stringify(this.retrievedData))
      document.getElementById("toast").classList.add("show")
    }

  },
      
}

</script>

<style>

</style>
