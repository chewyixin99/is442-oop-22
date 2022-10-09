<template>
    <div class="container">
        <h2 class="header">Select your pass type</h2>
    </div>

    <div class="container">
        <div class="row row-cols-md-auto g-5 d-flex justify-content-between">
            <div v-for="PassStaff in PassStaff_PassesCategory" :key="PassStaff.cid" >
                    <div class="card h-100" style="width: 25rem;">
                        <img :src="getImageUrl(PassStaff.image)" class="card-img-top">
                        <div class="card-body ">
                            <h5 class="card-title">{{PassStaff.name}}</h5>
                            <p class="card-text">{{PassStaff.description}}</p>
                        </div>
                        <ul class="list-group list-group-flush">
                            <span><b>Eligible places: </b><br>
                                {{getPlaces(PassStaff.cid)}}</span>
                        </ul>
                        <div class="card-footer btn-group-vertical">
                            <!-- <a class="btn btn-info" href="#" role="button">View</a> -->
                            <button type="button" class="btn btn-primary" data-bs-toggle="modal" :data-bs-target="`#`+PassStaff.modal">
                                View
                            </button>
                        </div>
                    </div>
                    <!-- Modal -->
                    <div class="modal fade" :id="PassStaff.modal" tabindex="-1" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-scrollable">
                            
                            <div class="modal-content">
                                <img :src="getImageUrl(PassStaff.image)" alt="Image" class="img-fluid" style="background-color: #b2fcff;">

                            <div class="content-text p-4">
                                <h3 class="mb-4">{{PassStaff.name}}</h3>
                                <p class="mb-4">{{PassStaff.description}}</p>
                                <span><b>Eligible places: </b><br>
                                {{getPlaces(PassStaff.cid)}}</span>
                                
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
            </div>
            
        </div>
    </div>

    <!-- Modal
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
            </div>
        </div>
    </div> -->
    
    
</template>

<script>
// import Pass from '../views/Pass.vue'
    export default ({
        name: 'PassStaff',
        props: {
            PassStaff_PassesCategory: Array,
        },
        methods: {
            getImageUrl(pic){
                return require('../assets/'+pic)
            },
            getPlaces(card_id){
                // console.log(this.PassStaff_PassesCategory)
                for(const i in this.PassStaff_PassesCategory){
                    if(card_id == this.PassStaff_PassesCategory[i]["cid"]){
                        // console.log(this.PassStaff_PassesCategory[i]["places"])
                        return this.PassStaff_PassesCategory[i]["places"].join(', ')
                    }

                }
            }
        },
        components: {
            // Pass
        },
        created(){
            // this.getPlaces()
        }
    })

</script>

<style scoped>

.header {
    text-align: center;
    padding: 20px;
    font-size:30px;
}

</style>
