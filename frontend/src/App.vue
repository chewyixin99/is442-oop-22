<template>
    <!-- <Sidebar /> -->
    <router-view></router-view>
</template>


<script>
// import Sidebar from './components/Sidebar.vue'

export default {
  name: 'App',
  components: {
    // Sidebar
  },
}
</script>

<style>
#app {
  /* font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center; */
  color: #2c3e50;
  font-family: 'Montserrat', sans-serif;
  margin: 0;
  padding: 0;
  overflow-y: hidden;
}

:root {
  --sss_red: #f02846;
  --sss_orange: #ff8b39d0;
}


@import url('https://fonts.googleapis.com/css2?family=Montserrat&display=swap');

</style>
