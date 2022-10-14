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
            <PassAdmin :PassAdminPasses="Passes"/>

        </div>

    </div>
    
</template>

<script>
// import PassStaff from '../components/PassStaff.vue'
import PassAdmin from '../components/PassAdmin.vue'


export default {
    name: 'Pass',
    components: {
        // PassStaff,
        PassAdmin,
    },
    data() {
        return {
            PassesCategory: [],
            Passes: [],
        }
    },
    created(){
        this.PassesCategory = [
            {
                "cid": "1",
                "name": "Mandai Wildlife Reserve",
                "description": "Desc1",
                "places": ["Singapore Zoo", "River Wonders"],
                "image": "passPic_test.jpg"
            },
            {
                "cid": "2",
                "name": "Pass 2",
                "description": "Desc2",
                "places": ["Place 2.1"],
                "image": "passPic_test.jpg"

            },
            {
                "cid": "3",
                "name": "Pass 3",
                "description": "Desc3",
                "places": ["Place 3.1", "Place 3.2"],
                "image": "passPic_test.jpg"

            },
                        {
                "cid": "4",
                "name": "Pass 4",
                "description": "Desc4",
                "places": ["Place 4.1", "Place 4.2"],
                "image": "passPic_test.jpg"

            },
            
        ],

        this.Passes = [
            {
                "passDesc": "Pass 1 description",
                "poi": "Gardens By The Bay",
                "numGuests": 2,
                "replacementFee": 20,
                "passStatus": "ONLOAN",
                "defunct": false,
                "id": 1,
                "physical": true
            },
            {
                "passDesc": "Pass 2 description",
                "poi": "Singapore Zoo",
                "numGuests": 2,
                "replacementFee": 20,
                "passStatus": "AVAILABLE",
                "defunct": true,
                "id": 2,
                "physical": true
            },
            {
                "passDesc": "Pass 3 description",
                "poi": "Sea Aquarium",
                "numGuests": 2,
                "replacementFee": 20,
                "passStatus": "AVAILABLE",
                "defunct": false,
                "id": 3,
                "physical": false
            },
            {
                "passDesc": "Pass 4 description",
                "poi": "Sea Aquarium",
                "numGuests": 2,
                "replacementFee": 20,
                "passStatus": "ONLOAN",
                "defunct": false,
                "id": 4,
                "physical": false
            }
        ]
    }
}

</script>

<style>

</style>