<template>
    <div class="container-fluid">
        <div class="flex-column d-flex align-items-center justify-content-center">
            <div class="tableBox position-relative">
                <div class="container">
                    <div class="row">
                        <PassAdmin :PassAdminPasses="passes.data" @getPassData="getAllPasses"/>
                    </div>
                </div>
            </div>
        </div>

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
    
    },
    methods:{
        async getAllPasses(){
            this.passes = await PassService.getAllPasses();
            console.log(this.passes.message)
        }
    }
}

</script>

<style>

</style>