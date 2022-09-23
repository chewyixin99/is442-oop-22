<template>
    <h2 class="header">Select your pass type</h2>
    
        <div class="container d-flex justify-content-evenly">
            <div class="row row-cols-1 row-cols-md-3 g-4">
                <div v-for="pass in PassStaff_PassesCategory" :key="pass.cid" >
                    <div class="card h-100" style="width: 18rem;">
                        <img :src="getImageUrl(pass.image)" class="card-img-top">
                        <div class="card-body">
                            <h5 class="card-title">{{pass.name}}</h5>
                            <p class="card-text">{{pass.description}}</p>
                        </div>
                        <ul class="list-group list-group-flush">
                            <li v-for="place in pass.places" :key="place" class="list-group-item">{{place}}</li>
                        </ul>
                        <div class="card-body">
                            <a class="btn btn-info" href="#" role="button">View passes</a>
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
            }
        },
        components: {
            // Pass
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
