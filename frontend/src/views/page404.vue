<template>
  <div id="notfound">
    <img src="../assets/404.svg" alt="">
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

@media only screen and (min-width: 767px){
  #notfound img {
    width: 50%;
  }
}

</style>