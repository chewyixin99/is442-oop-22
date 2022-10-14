<template>
    <div class='container'>
        <h2 class="header">Select your pass type</h2> 
    </div>
    

    <div class="container">
            <div class="row row-cols-auto g-5">
                <!-- Passes 'Card' -->
                <div v-for="EachPass in PassAdminPasses" :key="EachPass.id" >
                    <!-- <div><p>{{EachPass[0]}}</p></div> -->
                    <div class="card border-secondary h-100" style="width: 25rem;">
                        <img src="../assets/passPic_test.jpg" class="card-img-top">
                            <div class="card-body ">
                                <b class="card-title">{{EachPass.passDesc}}</b>
                                <!-- <p class="card-text">{{Pass.description}}</p> -->
                            </div>
                            <ul class="list-group list-group-flush">
                                <span><b>Point of Interests </b><br>
                                {{EachPass.poi}}</span>
                            </ul>
                            <ul class="list-group list-group-flush">
                                <div class="container">
                                    <div class="row">
                                        <div class="col-5 border"><b>ID</b></div>
                                        <div class="col-7 border">{{EachPass.id}}</div>
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
                                <!-- <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#viewUM">
                                    123
                                </button>  -->
                                <button type="button" class="btn btn-primary" data-bs-toggle="modal" :data-bs-target="`#viewUM`+EachPass.id">
                                    View
                                </button> 
                                
                            </div>
                    </div>
                    <!-- Pass update modal -->
                    <!-- <div class="modal fade" id="viewUM" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true"> -->
                    <div class="modal fade" :id="`viewUM`+EachPass.id" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">

                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Update pass information</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body text-start">
                                    <form>
                                        <div class="mb-3">
                                            <label class="col-form-label"><b>ID: {{EachPass.id}}</b></label><br>

                                            <label class="col-form-label">Description:</label> 
                                            <input class="form-control" v-model ="EachPass.passDesc" required >
                                            <label class="col-form-label">Point of Interests:</label>
                                            <input class="form-control" v-model ="EachPass.poi" required >
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Guests Numbers</b></label>
                                            <div class="col-7">
                                                <input type="number" v-model="EachPass.numGuests" class="form-control">
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Replacement Fee</b></label>
                                            <div class="col-7">
                                                <div class="input-group">
                                                    <div class="input-group-text">$</div>
                                                    <input type="number" v-model="EachPass.replacementFee" class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Status</b></label>
                                            <div class="col-7">
                                                <input type="text" v-model="EachPass.passStatus" class="form-control" disabled>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Defunct?</b></label>
                                            <div class="col-7">
                                                <input type="text" v-model="EachPass.defunct" class="form-control" disabled>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Physical?</b></label>
                                            <div class="col-7">
                                                <input type="text" v-model="EachPass.physical" class="form-control" disabled>
                                            </div>
                                        </div>
                                            
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Update Now</button>
                                    <button type="button" class="btn btn-danger" data-bs-toggle="modal">Delete</button> 
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>







                <!-- ====================================Loop ends============================= -->
                <!-- Add new passes section -->
                <!-- Add new pass icon/button -->
                <div class="card border-light h-100 ms-4" style="width: 25rem;">
                    <img src="../assets/plus-square-fill.svg" class="card-img-top">
                    <div class="card-body">
                        <button type="button"  class="btn stretched-link" data-bs-toggle="modal" data-bs-target="#addNewPass"><b>Add new passes</b></button>
                    </div>
                </div> 
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
                                            <label class="col-form-label"><b>ID:</b></label><br>
                                            <input type="number" class="form-control" required >
                                            <label class="col-form-label">Description:</label> 
                                            <input class="form-control" required >
                                            <label class="col-form-label">Point of Interests:</label>
                                            <input class="form-control" required >
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Guests Numbers</b></label>
                                            <div class="col-7">
                                                <input type="number" class="form-control" required>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Replacement Fee</b></label>
                                            <div class="col-7">
                                                <div class="input-group">
                                                    <div class="input-group-text">$</div>
                                                    <input type="number" class="form-control" required>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Status</b></label>
                                            <div class="col-7">
                                                <input type="text" class="form-control" required>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Defunct?</b></label>
                                            <div class="col-7">
                                                <input type="text" class="form-control" required>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Physical?</b></label>
                                            <div class="col-7">
                                                <input type="text" class="form-control" required>
                                            </div>
                                        </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Save</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>




        
        
    </div>
        
</template>

<script>

export default ({
    name: 'PassAdmin',
        props: {
            PassAdminPasses: Array,
        },
        methods: {
            getImageUrl(pic){
                return require('../assets/'+pic)
            },
            // getPlaces(card_id){
            //     for(const i in this.PassAdmin_PassesCategory){
            //         if(card_id == this.PassAdmin_PassesCategory[i]["cid"]){
            //             return this.PassAdmin_PassesCategory[i]["places"].join(', ')
            //         }

            //     }
            // }
        },
        components: {
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
