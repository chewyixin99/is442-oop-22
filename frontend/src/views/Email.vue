<template>
    <div class="col-sm-10 float-end">
        <!-- <h1>Email</h1>
        ------------------------------------
        <p>Admin functionalities:</p>
        ------------------------------------
        <p>1. Edit email template for the two types of email</p>
        <p>2. Settings for automated email</p> -->
        <!-- https://www.youtube.com/watch?v=pk5y34LpEPw -->
        <h1>Email</h1>
        <h2>Current email templates</h2>
        
        

        <div class="container">
          <QuillEditor theme="snow" />
        </div>
        
    </div>

</template>

<script>
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css';

export default {
  name: 'Email',
  components: {
    QuillEditor
  },
  data(){
    return {
        EmailTemplates: [],
    }
  },
  created(){
        this.EmailTemplates = [
            {
              "tid": "1",
              "template": "This is email one"
            },
        ]
    }
}

</script>

