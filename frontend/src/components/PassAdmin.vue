<template>
    <div class='container'>
        <h2 class="header">Select your pass type</h2> 
    </div>
    

    <div class="container">
            <div class="row row-cols-auto g-4  d-flex justify-content-evenly">
                <div v-for="PassAdmin in PassAdmin_PassesCategory" :key="PassAdmin.cid" >
                        <div class="card h-100" style="width: 30rem;">
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
                                <button type="button" class="btn btn-primary" data-bs-toggle="modal" :data-bs-target="`#Admin`+PassAdmin.modal">
                                    View
                                </button>     
                                <a class="btn btn-warning" href="#" role="button">Update</a>
                                <a class="btn btn-danger" href="#" role="button">Delete</a>
                            </div>
                    </div>
                    <div class="modal fade" :id="`Admin`+PassAdmin.modal" tabindex="-1" aria-hidden="true">
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
                                <a class="btn btn-danger" href="#" role="button">Delete</a>
                                <button type="button" class="btn btn-warning">Update</button>
                                <button type="button" class="btn btn-dark" data-bs-dismiss="modal">Close</button>
                            </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row row-cols-auto g-4">
                    <div class="card" style="width: 25rem;">
                        <div class="card-body">
                        <img src="../assets/plus-square-fill.svg" class="card-img-top " href="#">
                            <a href="#" class="btn stretched-link"><b>Add new passes</b></a>
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
