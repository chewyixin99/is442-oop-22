<template>
    <div class="container">
        <h2 class="header">Select your pass type</h2>
    </div>

    <div class="container">
        <div class="row row-cols-auto g-4 d-flex justify-content-evenly">
            <div v-for="pass in PassStaff_PassesCategory" :key="pass.cid" >
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
                        </div>
                    </div>
            </div>
        </div>
    </div>

    
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
