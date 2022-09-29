<template>
    <div class='container'>
        <h2 class="header">Select your pass type</h2> 
    </div>
    

    <div class="container">
            <div class="row row-cols-auto g-4  d-flex justify-content-evenly">
                <div v-for="pass in PassAdmin_PassesCategory" :key="pass.cid" >
                        <div class="card h-100" style="width: 30rem;">
                            <img :src="getImageUrl(pass.image)" class="card-img-top">
                            <div class="card-body ">
                                <h5 class="card-title">{{pass.name}}</h5>
                                <p class="card-text">{{pass.description}}</p>
                            </div>
                            <ul class="list-group list-group-flush">
                                <span><b>Eligible places: </b><br>
                                    {{getPlaces(pass.cid)}}</span>
                            </ul>
                            <div class="card-footer btn-group-vertical">
                                <a class="btn btn-info" href="#" role="button">View</a>
                                <a class="btn btn-warning" href="#" role="button">Update</a>
                                <a class="btn btn-danger" href="#" role="button">Delete</a>
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
