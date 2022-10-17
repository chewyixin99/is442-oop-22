<template>
<!-- THIS COMPONENT IS NOT IN USE -->
  <ViewBooking
  :key="componentKey"
  @reloadComponent="reloadComponent"
  />
</template>
<script>

import ViewBooking from "./ViewBooking.vue"

export default {
  name: 'Booking',
  components: {
    ViewBooking
  },
  data() {
    return {
      componentKey: 0,
    }
  },
  methods: {
    reloadComponent() {
      console.log(";sdflkjgijkdsfbkgjf")
      this.forceRerender()
    },
    forceRerender() {
      console.log("sdjknf")
      this.componentKey += 1;
    },
  }
}

</script>

<style>

</style>
