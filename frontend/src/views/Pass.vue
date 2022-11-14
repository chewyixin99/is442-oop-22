<template>
    <div class="container">

        <div class="row">
            <h1>Passes(Admin view)</h1>
        </div>
        <div class="row">
            <PassAdmin :PassAdminPasses="passes.data" @getPassData="getAllPasses"/>
            
        </div>
        <TheToastr :toastrResponse="toastrResponse"></TheToastr>
    </div>
</template>

<script>
import PassAdmin from '../components/PassAdmin.vue'
import PassService from "@/api/services/PassService";  

export default {
    name: 'Pass',
    components: {
        PassAdmin,
    },
    data() {
        return {
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
            // console.log(this.passes.data)
            console.log(this.passes.message)
        }
    }
}

</script>

<style>

</style>