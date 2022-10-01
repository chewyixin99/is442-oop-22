<template>
    <div id="theToastr" class="toast mt-2" role="alert" aria-live="assertive" aria-atomic="true">
        <!-- <div class="toast-header">
            <img src="..." class="rounded me-2" alt="...">
            <strong class="me-auto">Bootstrap</strong>
            <small>11 mins ago</small>
        </div> -->
        <div class="toast-body bg-success text-white">
            <span>{{msg}}</span>
            <button type="button" class="btn-close float-end btn-close-white" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
    </div>
</template>
<script>
export default {
    props: [
        "msg"
    ],
    data(){
        return{
            
        }
    }
}
</script>
<style scoped>
    #theToastr{
        width: 100%;
        font-size: 20px;
    }
</style>