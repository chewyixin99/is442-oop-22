<template>
    <div class="">
         <!-- {{inputData.allDay ? 'FULL DAY': 'PARTIAL DAY'}} -->
        

        <form>
            <label id="date">Start Date</label>
            <input type="date" v-model="outputData.startStr">
            <!-- {{calendarInput.startStr}} -->
            <label id="date">End Date</label>
            <input type="date"  v-model="outputData.endStr">
            <button type="submit" @click.prevent="submitForm()">Submit</button>
        </form>
    </div>


</template>

<script>

export default {
    name: "BookingForm", 
    // props: ["calendarInput"],
    props: {
        modelValue: {startStr: "", endStr: ""}
    },

    emits: ['update:modelValue'],

    data(){
        return{
            startInput: "",
            endInput: "",
            outputData: {startStr: "", endStr: ""}
        };
    },

    methods:{
        async signup() {

            if (this.email == "" || this.password == "" || this.firstName == "" || this.lastName == "" ){
                this.error = true;
                this.errorMsg = "Please fill out all the fields!";
            }
            else if (!(this.email.includes("@"))){
                this.error = true;
                this.errorMsg = "Please enter a valid email!";
            }
            else if (!(this.email.includes("."))){
                this.error = true;
                this.errorMsg = "Please enter a valid email!";
            }
            else if (this.password.length < 6){
                this.error = true;
                this.errorMsg = "Password must be more than 6 characters!";
            }
            
            },
        
        fullDayConversion(endDate){

            endDate.setMinutes(endDate.getMinutes() - 1)

            return endDate.toISOString().replace(/T.*$/, '') // YYYY-MM-DD of today
        },

        submitForm(){

            this.$emit("formOutput",this.outputData)
        }
    
    },

    // created(){
    //     this.startInput = this.calendarInput.startStr
    //     this.endInput = this.calendarInput.endStr
    // }


}
</script>