<template>
    <div class="col-sm-10 float-end">
        <h1>Email</h1>
        ------------------------------------
        <p>Admin functionalities:</p>
        ------------------------------------
        <p>1. Edit email template for the two types of email</p>
        <p>2. Settings for automated email</p>
        <QuillEditor theme="snow" />
    </div>

</template>

<script>
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css';

export default {
  components: {
    QuillEditor
  }
}

</script>

