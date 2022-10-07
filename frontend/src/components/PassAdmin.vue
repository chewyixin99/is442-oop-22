<template>
    <div class='container'>
        <h2 class="header">Select your pass type</h2> 
    </div>
    

    <div class="container">
            <div class="row row-cols-auto g-5">
                <!-- Passes 'Card' -->
                <div v-for="PassAdmin in PassAdmin_PassesCategory" :key="PassAdmin.cid" >
                    <div class="card h-100" style="width: 25rem;">
                        <img :src="getImageUrl(PassAdmin.image)" class="card-img-top">
                            <div class="card-body ">
                                <h5 class="card-title">{{PassAdmin.name}}</h5>
                                <p class="card-text">{{PassAdmin.description}}</p>
                            </div>
                            <ul class="list-group list-group-flush">
                                <span><b>Eligible places: </b><br>
                                    {{getPlaces(PassAdmin.cid)}}</span>
                            </ul>
                            <div class="card-footer btn-group-vertical">
                                <!-- Update button prompt Pass modal -->
                                <button type="button" class="btn btn-primary" data-bs-toggle="modal" :data-bs-target="`#AdminView`+PassAdmin.cid">
                                    View
                                </button>     
                                <!-- Update button prompt Passes update modal -->
                                <button type="button" class="btn btn-warning" data-bs-toggle="modal" :data-bs-target="`#AdminUpdate`+PassAdmin.cid">
                                    Update
                                </button> 
                                <button type="button" class="btn btn-danger" data-bs-toggle="modal">
                                    Delete
                                </button> 
                            </div>
                    </div>

                    <!-- View Passes Modal  -->
                    <div class="modal fade" :id="`AdminView`+PassAdmin.cid" tabindex="-1" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-scrollable">
                            
                            <div class="modal-content">
                                <img :src="getImageUrl(PassAdmin.image)" alt="Image" class="img-fluid" style="background-color: #b2fcff;">

                            <div class="content-text p-4">
                                <h3 class="mb-4">{{PassAdmin.name}}</h3>
                                <p class="mb-4">{{PassAdmin.description}}</p>
                                <span><b>test places: </b><br>
                                {{getPlaces(PassAdmin.cid)}}</span>
                                
                                <div class="d-flex">
                                <div class="ml-auto">
                                </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-dark" data-bs-dismiss="modal">Close</button>
                            </div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Pass update modal -->
                    <div class="modal fade" :id="`AdminUpdate`+PassAdmin.cid" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Update pass information</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body text-start">
                                    <form>
                                        <div class="mb-3 has-validation">
                                            <label for="name" class="col-form-label">Name:</label>
                                            <input class="form-control" v-model ="PassAdmin.name" id="PassName" required >
                                        </div>
                                        <div class="mb-3">
                                            <label for="description" class="col-form-label">Description:</label>
                                            <input class="form-control" v-model ="PassAdmin.description" id="PassDescription" required >
                                        </div>
                                        <div class="mb-3">
                                            <label for="places" class="col-form-label">Places:</label>
                                            <input class="form-control" v-model ="PassAdmin.places" id="PassPlaces" required >
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" @click="UpdatePass" data-bs-dismiss="modal">Update Now</button>
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
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
                                    <div class="mb-3 has-validation">
                                        <label for="name" class="col-form-label">Name:</label>
                                        <input type="text" class="form-control" id="PassName" placeholder="Enter name here" required>

                                    </div>
                                    <div class="mb-3">
                                        <label for="description" class="col-form-label">Description:</label>
                                        <input type="text" class="form-control" id="PassDescription" placeholder="Enter descriptions here" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="places" class="col-form-label">Eligible Places:</label>
                                        <input type="text" class="form-control" id="PassPlaces" placeholder="Enter locations here" required>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" @click="CreateNewPass" data-bs-dismiss="modal">Save</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
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
            PassAdmin_PassesCategory: Array,
        },
        methods: {
            getImageUrl(pic){
                return require('../assets/'+pic)
            },
            getPlaces(card_id){
                for(const i in this.PassAdmin_PassesCategory){
                    if(card_id == this.PassAdmin_PassesCategory[i]["cid"]){
                        return this.PassAdmin_PassesCategory[i]["places"].join(', ')
                    }

                }
            }
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
