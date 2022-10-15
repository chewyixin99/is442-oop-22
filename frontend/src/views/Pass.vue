<template>
    <!-- <div class="">
        <h1>Pass</h1>
        ------------------------------------
        <p>Staff functionalities:</p>
        ------------------------------------
        <p>1. View all passes and their details - card UI</p>
        ------------------------------------
        <p>Admin functionalities:</p>
        ------------------------------------
        <p>1. View all passes and their details - card UI</p>
        <p>2. Create new passes</p>
        <p>3. Edit/delete passes</p>
    </div> -->
    <div class="container">
<!-- 
        <div class="row">
            <h1>Passes(staff view)</h1>
        </div>
        <div class="row ">
            <PassStaff :PassStaff_PassesCategory="PassesCategory"/>
        </div>

        <br>
        <hr>
        <br> -->

        <div class="row">
            <h1>Passes(Admin view)</h1>
        </div>
        <div class="row">
            <!-- <PassAdmin :PassAdmin_PassesCategory="PassesCategory"/> -->
            <PassAdmin :PassAdminPasses="passes" @getPassData="getAllPasses"/>

        </div>

    </div>
    
</template>

<script>
// import PassStaff from '../components/PassStaff.vue'
import PassAdmin from '../components/PassAdmin.vue'
import PassService from "@/api/services/PassService";  

export default {
    name: 'Pass',
    components: {
        // PassStaff,
        PassAdmin,
    },
    data() {
        return {
            // Capital for testing, small capital for database data
            // Passes: [],
            
            passes: [],

        }
    },
    async mounted(){
        this.getAllPasses()
    },
    created(){
        // this.Passes = [
        //     {
        //         "passDesc": "Passx  description",
        //         "poi": "Gardens By The Bay",
        //         "numGuests": 2,
        //         "replacementFee": 20,
        //         "passStatus": "ONLOAN",
        //         "defunct": false,
        //         "id": 1,
        //         "physical": true
        //     },
        //     {
        //         "passDesc": "Pass 2 description",
        //         "poi": "Singapore Zoo",
        //         "numGuests": 2,
        //         "replacementFee": 20,
        //         "passStatus": "AVAILABLE",
        //         "defunct": true,
        //         "id": 2,
        //         "physical": true
        //     },
        //     {
        //         "passDesc": "Pass 3 description",
        //         "poi": "Sea Aquarium",
        //         "numGuests": 2,
        //         "replacementFee": 20,
        //         "passStatus": "AVAILABLE",
        //         "defunct": false,
        //         "id": 3,
        //         "physical": false
        //     },
        //     {
        //         "passDesc": "Pass 4 description",
        //         "poi": "Sea Aquarium",
        //         "numGuests": 2,
        //         "replacementFee": 20,
        //         "passStatus": "ONLOAN",
        //         "defunct": false,
        //         "id": 4,
        //         "physical": false
        //     }
        // ]
    },
    methods:{
        async getAllPasses(){
            this.passes = await PassService.getAllPasses();
        }
    }
}

</script>

<style>

</style>