<template>
    <div class="container">
        <!-- <h1>Email</h1>
        ------------------------------------
        <p>Admin functionalities:</p>
        ------------------------------------
        <p>1. Edit email template for the two types of email</p>
        <p>2. Settings for automated email</p> -->
        <h1>Email</h1>
        <h2>Email Types and Templates</h2>
        <div class="container-fluid">
          <div class="row row-cols-1 g-5">
            <div v-for="(template, index) in EmailTemplates" :key="template.templateName" >
                <div class="card">
                  <div class="card-body">
                    <h5 class="card-title">{{template.templateName}}</h5>
                    <div v-html="template.templateDraft">
                    </div>
                    <!-- <p class="card-text break-text">
                      {{template.templateDraft}}
                      </p> -->
                    <!-- Update button -->
                    <button type="button" class="btn btn-warning" data-bs-toggle="modal" :data-bs-target="`#emailModal`+template.templateName" @click="backupContent(template.templateDraft)">
                      Update
                    </button> 
                    <!-- Update modal -->
                    <div class="modal fade" data-bs-backdrop="static" :id="`emailModal`+template.templateName" tabindex="-1"  aria-hidden="true">
                        <div class="modal-dialog modal-xl">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Update {{template.templateName}} information</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="cancelUpdate(template.templateName)"></button>
                                </div>
                                <div class="modal-body">
                                  <QuillEditor theme="snow" toolbar="full"  v-model:content="EmailTemplates[index]['templateDraft']" contentType="html">
                                  </QuillEditor>
                                </div>
                                <div class="modal-footer">
                                  <button type="button" class="btn btn-warning" data-bs-dismiss="modal" @click="saveContent(template.templateName)">Save</button>
                                  <button type="button" class="btn btn-danger" data-bs-dismiss="modal" @click="cancelUpdate(template.templateName)">Cancel</button>
                                </div>
                            </div>
                        </div>
                    </div>
                  </div>
                </div>
            </div>

          </div>
        </div>
<!-- 
        <hr>
        <hr> -->

        <!-- <h2>Emails Editor Test</h2> -->
        <!-- <div class="container" id="vueQuillEditor_1">
          <QuillEditor v-model:content="content_1" contentType="text" theme="snow" toolbar="#custom-toolbar_1">
            <template #toolbar>
              <div id="custom-toolbar_1">
                <select class="ql-size">
                  <option value="small"></option>
                  <option selected></option>
                  <option value="large"></option>
                  <option value="huge"></option>
                </select>
                <select class="ql-header">
                  <option :value="1"></option>
                  <option :value="2"></option>
                  <option :value="3"></option>
                  <option :value="4"></option>
                  <option :value="5"></option>
                  <option :value="6"></option>
                  <option selected></option>
                </select>
                <button class="ql-bold"></button>
                <button class="ql-italic"></button>
                <button class="ql-underline"></button>
                <button class="ql-strike"></button>
                <button class="ql-script" value="sub"></button>
                <button class="ql-script" value="super"></button>
                <select class="ql-align">
                  <option selected></option>
                  <option value="center"></option>
                  <option value="right"></option>
                  <option value="justify"></option>
                </select>
                <button class="ql-list" value="ordered"></button>
                <button class="ql-list" value="bullet"></button>
                <button class="ql-blockquote"></button>
                <button class="ql-code-block"></button>
                <button class="ql-link"></button>
                <button class="ql-image"></button>
                <button id="saveButton"  @click="saveContent()"></button>
              
              </div>
            </template>
          </QuillEditor>
        </div> -->
        <!-- <div class="container" id="vueQuillEditor_TemplateTest">
          <QuillEditor theme="snow" toolbar="full" v-model:content="vueContent" contentType="html">
          </QuillEditor>
          <button type="button" class="btn btn-warning" @click="saveContentTest()">Save</button>
        </div> -->
          <!-- <QuillEditor theme="snow" toolbar="full" v-model:content="EmailTemplates[0]['templateDraft']" contentType="text">
          </QuillEditor>

          <QuillEditor theme="snow" toolbar="full" v-model:content="EmailTemplates[1]['templateDraft']" contentType="text">
          </QuillEditor> -->
        
          


        

        
        
          
    </div>
</template>

<script>
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'

export default {
  name: 'Email',
  components: {
    QuillEditor
  },
  data(){
    return {
    EmailTemplates: [],
    vueContent: ``,
    backupContentData: ``,
    // content_1: "Content 1",
    // content_2: "Content 2",
    // custom toolbar
    }
  },
  created(){
      this.EmailTemplates = [
          {
            "templateName": "ePass",
            "templateDraft": `Dear borrower’s name,
                      ePass Draft this is
                      Enjoy your visit to attraction name11! `,
              
          },
          { 
            "templateName": "physicalPass",
            "templateDraft": `Dear borrower’s name,
                      physicalPAss Draft this is
                      HR Department `,
          }
      ]
    },
    methods: {
            saveContent(){
              this.backupContentData = ``
              console.log(this.EmailTemplates[0]["templateDraft"])
            },
            backupContent(draftToBackup){
              this.backupContentData = draftToBackup
            },
            cancelUpdate(selectedTemplateName){
              for (let i = 0; i < this.EmailTemplates.length; i++) {
                console.log(this.EmailTemplates[i]["templateName"])
                if(this.EmailTemplates[i]["templateName"]==selectedTemplateName){
                  this.EmailTemplates[i]["templateDraft"] = this.backupContentData
                }
              }
            },
        },
}

</script>

<style scoped>
.break-text p {
  word-wrap: break-word;
}
</style>
