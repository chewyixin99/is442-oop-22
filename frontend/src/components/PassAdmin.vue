<template>
    <h2 class="header">Select your pass type</h2>

    <div class="container d-flex justify-content-evenly">
        <div class="row row-cols-1 row-cols-md-3 g-4">
            <div v-for="pass in PassAdmin_PassesCategory" :key="pass.cid" >
                <div class="col">
                    <div class="card h-100" style="width: 18rem;">
                        <img :src="getImageUrl(pass.image)" class="card-img-top">
                        <div class="card-body">
                            <h5 class="card-title">{{pass.name}}</h5>
                            <p class="card-text">{{pass.description}}</p>
                        </div>
                        <ul class="list-group list-group-flush">
                            <li v-for="place in pass.places" :key="place" class="list-group-item">{{place}}</li>
                        </ul>
                        <div class="card-body btn-group-vertical">
                            <a class="btn btn-info" href="#" role="button">View</a>
                            <a class="btn btn-danger" href="#" role="button">Delete</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card" style="width: 18rem;">
                    <img src="../assets/PassAdmin_add.jpg" class="card-img-top" alt="...">
                    <div class="card-body">
                        <a href="#" class="btn btn-primary stretched-link">Add new passes</a>
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
            }
        },
        components: {
        },
})

</script>
