<template>
    <div class="container">
        <div class="row">
            <h1>Pass(User)</h1>
        </div>
        <div class="row">
            <div class="container">
                <span>
                    <h2 class="d-inline p-2">View Passes</h2>
                </span>
                
            <div class="row row-cols-auto g-5 my-2">
                <template v-for="EachPass in userPasses" :key="EachPass.passId" >
                        <div v-if="EachPass.defunct=='0'">
                            <div class="card border-secondary h-100" style="width: 25rem;">
                                <template v-if="EachPass.passImage">
                                    <img v-bind:src="`data:image/*;base64,${EachPass.passImage}`" class="mx-auto mt-2 d-block border border-2 border-secondary" height="250" width="375">
                                </template>
                                <template v-else>
                                    <img src="../assets/PassAssets/noImageAvailable.jpg" class="mx-auto mt-2 d-block border border-2 border-secondary" height="250" width="375">
                                </template>
                                    <div class="card-body ">
                                        <b class="card-title">{{EachPass.passDesc}}</b>
                                    </div>
                                    <ul class="list-group list-group-flush">
                                        <span><b>Point of Interests </b><br>
                                        {{EachPass.poi}}</span>
                                    </ul>
                                    <ul class="list-group list-group-flush">
                                        <span><b>Point of Interests URL</b></span>
                                        <div class="text-wrap w-90 justify-content-center">
                                            <span>{{EachPass.poiUrl}}</span>
                                        </div>
                                    </ul>
                                    <ul class="list-group list-group-flush">
                                        <div class="container">
                                            <div class="row">
                                                <div class="col-5 border"><b>ID</b></div>
                                                <div class="col-7 border">{{EachPass.passId}}</div>
                                                <div class="col-5 border"><b>Pass Number</b></div>
                                                <div class="col-7 border">{{EachPass.passNumber}}</div>
                                                <div class="col-5 border"><b>Guests Numbers</b></div>
                                                <div class="col-7 border">{{EachPass.numGuests}}</div>
                                                <div class="col-5 border"><b>Replacement Fee</b></div>
                                                <div class="col-7 border">${{EachPass.replacementFee}}</div>
                                                <div class="col-5 border"><b>Status</b></div>
                                                <div class="col-7 border">{{EachPass.passStatus}}</div>
                                                <div class="col-5 border"><b>Physical/EPass</b></div>
                                                <template v-if="EachPass.isPhysical==true">
                                                    <div class="col-7 border">Physical Pass</div>
                                                </template>
                                                <template v-else>
                                                    <div class="col-7 border">EPass</div>
                                                </template>
                                            </div>
                                        </div>
                                    </ul>
                            </div>
                        </div>
                </template>
            </div>
                
                
            </div>
        </div>
    </div>
    

    
    
</template>

<script>
import PassService from "@/api/services/PassService";  

export default {
    name: 'PassUser',
    data() {
        return {
            userPasses: [],
        }
    },
    async mounted(){
        this.getAllPasses()
        console.log(this.userPasses)
    },
    created(){
    },
    methods:{
        async getAllPasses(){
            var tempPass = await PassService.getAllPasses();
            this.userPasses = tempPass.data
            console.log("here: ")
            // console.log(tempPass.data)
            console.log(tempPass.message)
        }
    }
}  
</script>

<style scoped>
.header {
    text-align: center;
    padding: 20px;
    font-size:30px;
}
</style>