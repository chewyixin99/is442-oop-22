<template>
  <div id="notfound">
    <img class="img-fluid" src="../assets/404.svg" alt="">
    <div>
      <button class="btn btn-outline-primary" @click="$router.push('/login')">
            Back To Login Page
      </button>
    </div>
  </div>
</template>
<style>
#notfound img {
    width: 100%;
}

@media only screen and (min-width: 800px){
  #notfound img {
    width: 80%;
  }
}

 @media only screen and (min-width: 1080px){
  #notfound img {
    width: 60%;
  }
}


@media only screen and (min-width: 1400px){
  #notfound img {
    width: 45%;
  }
}

</style>