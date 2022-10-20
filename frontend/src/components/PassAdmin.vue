<template>

    <div class="container">
        <span>
            <h2 class="d-inline p-2 ">
                Select Your Pass
                <button  type="button" class="btn btn-outline-success d-inline p-2" data-bs-toggle="modal" data-bs-target="#addNewPass">
                    Add New Pass
                    <i class="bi bi-plus-square"></i>
                </button>
            </h2>
            
        </span>


            <div class="row row-cols-auto g-5 my-2">
                <!-- Passes 'Card' -->
                <template v-for="EachPass in PassAdminPasses" :key="EachPass.passId" >
                <div v-if="EachPass.defunct=='0'">

                    <div class="card border-secondary h-100" style="width: 25rem;">
                        <img src="../assets/passPic_test.jpg" class="card-img-top">
                            <div class="card-body ">
                                <b class="card-title">{{EachPass.passDesc}}</b>
                            </div>
                            <ul class="list-group list-group-flush">
                                <span><b>Point of Interests </b><br>
                                {{EachPass.poi}}</span>
                            </ul>
                            <ul class="list-group list-group-flush">
                                <div class="container">
                                    <div class="row">
                                        <div class="col-5 border"><b>ID</b></div>
                                        <div class="col-7 border">{{EachPass.passId}}</div>
                                        <div class="col-5 border"><b>Guests Numbers</b></div>
                                        <div class="col-7 border">{{EachPass.numGuests}}</div>
                                        <div class="col-5 border"><b>Replacement Fee</b></div>
                                        <div class="col-7 border">${{EachPass.replacementFee}}</div>
                                        <div class="col-5 border"><b>Status</b></div>
                                        <div class="col-7 border">{{EachPass.passStatus}}</div>
                                        <div class="col-5 border"><b>Defunct?</b></div>
                                        <div class="col-7 border">{{EachPass.defunct}}</div>
                                        <div class="col-5 border"><b>Physical?</b></div>
                                        <div class="col-7 border">{{EachPass.physical}}</div>
                                    </div>
                                </div>
                            </ul>
                            
                            <div class="card-footer btn-group-vertical">
                                <!-- View button prompt Passes update modal -->
                                <button type="button" class="btn btn-primary" data-bs-toggle="modal" :data-bs-target="`#viewUM`+EachPass.passId">
                                    View
                                </button> 
                                
                            </div>
                    </div>
                    <!-- Pass update modal -->
                    <!-- <div class="modal fade" id="viewUM" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true"> -->
                    <div class="modal fade" :id="`viewUM`+EachPass.passId" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">

                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Update pass information</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body text-start">
                                    <form>
                                        <div class="mb-3">
                                            <label class="col-form-label"><b>ID: {{EachPass.passId}}</b></label><br>

                                            <label class="col-form-label">Description:</label> 
                                            <input class="form-control" v-model ="EachPass.passDesc" required >
                                            <label class="col-form-label">Point of Interests:</label>
                                            <input class="form-control" v-model ="EachPass.poi" required >
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Guests Numbers</b></label>
                                            <div class="col-7">
                                                <input type="number" v-model="EachPass.numGuests" class="form-control" min="1">
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Replacement Fee</b></label>
                                            <div class="col-7">
                                                <div class="input-group">
                                                    <div class="input-group-text">$</div>
                                                    <input type="number" v-model="EachPass.replacementFee" class="form-control" min="0">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Status</b></label>
                                            <div class="col-7">
                                                <select class="form-select" v-model="EachPass.passStatus">
                                                    <option value="AVAILABLE">Available</option>
                                                    <option value="ONLOAN">On Loan</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Deactivated?(Defunct)</b></label>
                                            <div class="col-7">
                                                <input type="text" v-model="EachPass.defunct" class="form-control" disabled>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Physical?</b></label>
                                            <div class="col-7">
                                                <input type="text" v-model="EachPass.physical" class="form-control">
                                            </div>
                                        </div>
                                            
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" @click="updatePassMethod(EachPass.passId)">Update Now</button>
                                    <button type="button" class="btn btn-danger" @click="deletePassMethod(EachPass.passId)" data-bs-dismiss="modal">Delete</button> 
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                </template>







                <!-- ====================================Loop ends============================= -->
                <!-- Add new passes section -->
                <!-- Add new pass icon/button -->

                <!-- <div class="card border-light h-100 ms-4" style="width: 25rem;">
                    <img src="../assets/plus-square-fill.svg" class="card-img-top">
                    <div class="card-body">
                        <button type="button"  class="btn stretched-link" data-bs-toggle="modal" data-bs-target="#addNewPass"><b>Add new passes</b></button>
                    </div>
                </div>  -->

                <!-- Add New Pass Form Modal-->
                <div class="modal fade" id="addNewPass" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Create new pass</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>

                            <div class="modal-body text-start">
                                <form>
                                    <div class="mb-3">
                                            <!-- <label class="col-form-label"><b>ID:</b>5</label><br> -->
                                            <label class="col-form-label">Description:</label> 
                                            <input class="form-control" required v-model="NewPassObject.passDesc">
                                            <label class="col-form-label">Point of Interests:</label>
                                            <input class="form-control" required v-model="NewPassObject.poi">
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Guests Numbers</b></label>
                                            <div class="col-7">
                                                <input type="number" class="form-control" required v-model="NewPassObject.numGuests" min="1">
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Replacement Fee</b></label>
                                            <div class="col-7">
                                                <div class="input-group">
                                                    <div class="input-group-text">$</div>
                                                    <input type="number" class="form-control" required v-model="NewPassObject.replacementFee" min="0">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Status</b></label>
                                            <div class="col-7">
                                                <select class="form-select" v-model="NewPassObject.passStatus">
                                                    <option value="AVAILABLE">Available</option>
                                                    <option value="ONLOAN">On Loan</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Deactivated?(defunct)</b></label>
                                            <div class="col-7">
                                                <select class="form-select" disabled v-model="NewPassObject.defunct">
                                                    <option value="true">true</option>
                                                    <option value="false" selected>false</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Physical?</b></label>
                                            <div class="col-7">
                                                <select class="form-select" v-model="NewPassObject.physical">
                                                    <option value="true">true</option>
                                                    <option value="false">false</option>
                                                </select>
                                            </div>
                                        </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" @click="addNewPass()" data-bs-dismiss="modal">Create Pass</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>




        
        
    </div>
        
</template>

<script>
import axios from 'axios'
export default ({
    name: 'PassAdmin',
        props: {
            PassAdminPasses: Array,
        },
        
        components: {
        },
        data() {
        return {
                // Capital for testing, small capital for database data
                passURL: "http://localhost:8081/passes",
                passIDtoDelete: null,

                NewPassObject: {
                    passDesc: "",
                    poi: "",
                    numGuests: 1,
                    replacementFee: 0,
                    passStatus: "AVAILABLE",
                    defunct: false,
                    physical: true
                },

                UpdatePassObject:{
                    passDesc: "",
                    poi: "",
                    numGuests: 1,
                    replacementFee: 0,
                    passStatus: "AVAILABLE",
                    defunct: false,
                    physical: true
                }
            
            }
        },
        methods: {
            GetImageUrl(pic){
                return require('../assets/'+pic)
            },
            async addNewPass(){
                try{
                    await axios.post(this.passURL, this.NewPassObject)
                    .then(response => {
                        this.$emit('getPassData')
                        console.log(response);
                    });
                } catch(err){
                    console.error(err);
                }
            },

            async deleteExistingPass(passID){
                try{
                    await axios.delete(this.passURL+"/"+passID)
                    .then(response => {
                        this.NewPassObject={
                                passDesc: "",
                                poi: "",
                                numGuests: 1,
                                replacementFee: 0,
                                passStatus: "AVAILABLE",
                                defunct: false,
                                physical: true
                            }
                        this.$emit('getPassData')
                        console.log(response);
                    });
                } catch(err){
                    console.error(err);
                }
            },

            deletePassMethod(passID){
                this.deleteExistingPass(passID)
            },
            // updatePassMethod(passID){
            //     console.log(passID)
            // },
            async updatePassMethod(passID){
                
                try{
                    await axios.put(this.passURL+"/"+passID, this.PassAdminPasses[passID-1])
                    .then(response => {
                        this.$emit('getPassData')
                        console.log(response);
                    });
                } catch(err){
                    console.error(err);
                }
            }
        },

})

</script>
<style scoped>
.header {
    text-align: center;
    padding: 20px;
    font-size:30px;
}

</style>
