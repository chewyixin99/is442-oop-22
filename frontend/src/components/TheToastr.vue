<template>
<div id="aria" aria-live="polite" aria-atomic="true" class="d-flex position-fixed">
    <div id="theToastr" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header">
            <div v-if="toastrResponse.status == 'Success'" class="rounded me-2 bg-success p-2"></div>
            <div v-else class="rounded me-2 bg-danger p-2"></div>
            <strong class="me-auto">{{toastrResponse.status}}!</strong>
            <small>1 second ago</small>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body">
            {{toastrResponse.msg}}
        </div>
    </div>
</div>
</template>
<script>
export default {
    props: [
        "toastrResponse"
    ],
    data(){
        return{
            
        }
    }
}
</script>
<style scoped>
    #aria{
        bottom: 5%;
        right: 5%;
    }
</style>