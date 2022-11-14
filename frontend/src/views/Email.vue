<template>
  <div class="container">
    <!-- <h1>Email</h1>
        ------------------------------------
        <p>Admin functionalities:</p>
        ------------------------------------
        <p>1. Edit email template for the two types of email</p>
        <p>2. Settings for automated email</p> -->
    <div class="row">
      <h1>Email</h1>
    </div>
    <span >
      <h2 class="d-inline p-2 mt-3">
        View your email templates
        <button
          type="button"
          class="btn btn-outline-success d-inline p-2"
          data-bs-toggle="modal"
          data-bs-target="#addNewEmailTemplate"
        >
          Add New Email Template
          <i class="bi bi-plus-square"></i>
        </button>
      </h2>
    </span>
    <div class="container-fluid mt-3">
      <div class="row row-cols-1 g-5">
        <!-- change here start -->
        <template
          v-for="(template) in emailTemplates"
          :key="template.templateId"
        >
          <div v-if="!template.defunct">
            <!-- change here end -->
            <div class="card mb-3">
              <ul class="list-group list-group-flush">
                  <div class="container">
                      <div class="row">
                          <div class="col-12 border bg-info bg-gradient text-dark"><h2>Template ID {{template.templateId }}</h2></div>
                          <div class="col-3 border"><h2 class="text-center"><b>Template Name</b></h2></div>
                          <div class="col-9 border"><h2 class="text-center">{{template.templateName }}</h2></div>
                          <div class="col-3 border"><h2 class="text-center"><b>Template Subject</b></h2></div>
                          <div class="col-9 border"><h2 class="text-center">{{template.templateSubject }}</h2></div>
                      </div>
                  </div>
              </ul>
              
              <div class="card-body border-secondary">
                <p class="text-start" v-html="template.templateData"></p>
              </div>
              <div class="card-footer border-secondary btn-group-vertical">
                <button
                  type="button"
                  class="btn btn-primary"
                  data-bs-toggle="modal"
                  :data-bs-target="`#emailModal` + template.templateId"
                  @click="backupContent(template.templateData)"
                >
                  View
                </button>
              </div>
            </div>
            <!-- Update modal -->
            <div
              class="modal fade"
              data-bs-backdrop="static"
              :id="`emailModal` + template.templateId"
              tabindex="-1"
              aria-hidden="true"
            >
              <div class="modal-dialog modal-dialog-scrollable modal-xl">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">
                      Update template {{ template.templateId }} information
                    </h5>
                    <button
                      type="button"
                      class="btn-close"
                      data-bs-dismiss="modal"
                      aria-label="Close"
                      @click="cancelUpdate(template.templateId)"
                    ></button>
                  </div>
                  <div class="modal-body text-start">
                      
                    
                    <div class="mb-3">
                      <!-- <label class="col-form-label"><b>ID:</b>5</label><br> -->
                      <label class=""><b class="col-form-label">Template ID: {{template.templateId}}</b></label><br>
                      <div class="input-group mb-3 was-validated">
                          <label class="col-form-label col-12"><b>Template Name</b></label>
                          <div class="col-12">
                              <input class="form-control" v-model ="template.templateName" required >
                              <div class="invalid-feedback text-black-50">Please enter a valid template name. e.g: Account Management Template</div>
                          </div>
                      </div>
                      <div class="input-group mb-3 was-validated">
                          <label class="col-form-label col-12"><b>Template Subject</b></label>
                          <div class="col-12">
                              <input class="form-control" v-model ="template.templateSubject" required >
                              <div class="invalid-feedback text-black-50">Please enter a valid template subject. e.g: Singapore Sport School - Corp Pass Account Registration</div>
                          </div>
                      </div>
                      <label class="col-form-label col-12"><b>Template Data</b></label>
                      <QuillEditor
                        theme="snow"
                        toolbar="full"
                        v-model:content="template.templateData"
                        contentType="html"
                      >
                      </QuillEditor>
                    </div>
                  </div>
                  <div class="modal-footer">
                    <button
                      type="button"
                      class="btn btn-primary"
                      data-bs-dismiss="modal"
                      @click="updateTemplateMethod(template.templateId)"
                      :disabled="!templateCreationUpdateDisable(template.templateName, template.templateSubject)"
                    >
                      Update Now
                    </button>
                    <button
                      type="btn btn-danger"
                      class="btn btn-danger"
                      data-bs-dismiss="modal"
                      @click="deleteTemplateMethod(template.templateId)"
                    >
                      Delete
                    </button>
                    <button
                      type="button"
                      class="btn btn-secondary"
                      data-bs-dismiss="modal"
                      @click="cancelUpdate(template.templateId)"
                    >
                      Close
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </template>
      </div>
    </div>
    <!-- Add Pass Modal Start -->
    <div
      class="modal fade"
      id="addNewEmailTemplate"
      data-bs-backdrop="static"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-scrollable">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">
              Create new template
            </h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
              @click="newTemplateInitialState()"
            ></button>
          </div>

          <div class="modal-body text-start">
            <form>
              <div class="mb-3">
                <!-- <label class="col-form-label"><b>ID:</b>5</label><br> -->
                <div class="input-group mb-3 was-validated">
                    <label class="col-form-label col-12"><b>Template Name</b></label>
                    <div class="col-12">
                        <input class="form-control" v-model ="newTemplateName" required >
                        <div class="invalid-feedback text-black-50">Please enter a valid template name. e.g: Account Management Template</div>
                    </div>
                </div>
                <div class="input-group mb-3 was-validated">
                    <label class="col-form-label col-12"><b>Template Subject</b></label>
                    <div class="col-12">
                        <input class="form-control" v-model ="newTemplateSubject" required >
                        <div class="invalid-feedback text-black-50">Please enter a valid template subject. e.g: Singapore Sport School - Corp Pass Account Registration</div>
                    </div>
                </div>
                <label class="col-form-label">Template Data:</label>
                <QuillEditor
                  ref="myVueContent"
                  theme="snow"
                  toolbar="full"
                  v-model:content="newTemplateData"
                  contentType="html"
                >
                </QuillEditor>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-primary"
              @click="createTemplateMethod()"
              data-bs-dismiss="modal" :disabled="!templateCreationUpdateDisable(newTemplateName, newTemplateSubject)"
            >
              Create Template
            </button>
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
              @click="newTemplateInitialState"
            >
              Cancel
            </button>
          </div>
        </div>
      </div>
    </div>
    <!-- Add Pass Modal End -->
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
  <TheToastr :toastrResponse="toastrResponse"></TheToastr>
  </div>
</template>

<script>
import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";
import EmailService from "@/api/services/EmailService";
import axios from "axios";
import TheToastr from "@/components/TheToastr.vue";
import { Toast } from "bootstrap";


export default {
  name: "Email",
  components: {
    TheToastr,
    QuillEditor
  },
  data() {
    return {
      templateURL: "http://localhost:8081/templates",
      emailTemplates: [],
      newTemplateName: ``,
      newTemplateSubject: ``,
      newTemplateData: ``,
      EmailTemplates: [],
      vueContent: ``,
      backupContentData: ``,
      templateDataToUpdate: {
        templateName: "test1",
        templateSubject: "test1",
        templateData: "test1",
      },
      toastrResponse: "",
    };
  },
  async mounted() {
    this.getEmailDatas();
  },
  created() {
  },
  methods: {
    showToast(){
        var bsAlert = new Toast(document.getElementById('theToastr'));         
        bsAlert.show();
    },
    async getEmailDatas() {
      var tempEmailTemplates = await EmailService.getAllEmailTemplates();
      this.emailTemplates = tempEmailTemplates.data;
      console.log(this.emailTemplates);
    },
    async deleteTemplateMethod(templateID) {
      const bearer_token = `Bearer ${localStorage.getItem("token")}`;
      const config = {
        headers: {
          Authorization: bearer_token,
        },
      };
      try {
        await axios
          .delete(this.templateURL + "/" + templateID, config)
          .then((response) => {
            this.getEmailDatas();
            console.log(response);
            this.toastrResponse = {status: "Success", msg: "Email Template has been deleted!"};
            this.showToast(); 
          });
      } catch (err) {
        console.error(err);
        this.toastrResponse = {status: "Error", msg: "Email Template could not be deleted!"};
        this.showToast(); 
      }
    },
    updateTemplateMethod(templateID) {
      var temp;
      for (let i = 0; i < this.emailTemplates.length; i++) {
        if (this.emailTemplates[i]["templateId"] == templateID) {
          temp = {
            templateName: this.emailTemplates[i].templateName,
            templateSubject: this.emailTemplates[i].templateSubject,
            templateData: this.emailTemplates[i].templateData,
          };
        }
      }
      this.putTemplateMethod(templateID, temp);
      
    },
    async putTemplateMethod(templateID, templateDATA) {
      const bearer_token = `Bearer ${localStorage.getItem("token")}`;
      const config = {
        headers: {
          Authorization: bearer_token,
        },
      };
      try {
        await axios
          .put(this.templateURL + "/" + templateID, templateDATA, config)
          .then((response) => {
            this.backupContentData = ``;
            this.toastrResponse = {status: "Success", msg: "Email Template has been updated!"};
            this.showToast(); 
            this.getEmailDatas();
            console.log(response);
          });
      } catch (err) {
        console.error(err);
        this.toastrResponse = {status: "Error", msg: "Email Template could not be updated!"};
        this.showToast(); 
      }
    },
    createTemplateMethod() {
      var temp = {
        templateName: this.newTemplateName,
        templateData: this.newTemplateData,
        templateSubject: this.newTemplateSubject,

      };
      this.newTemplateName = ``;
      this.newTemplateSubject = ``;
      this.newTemplateData = ``;
      this.PostTemplateMethod(temp);
    },
    async PostTemplateMethod(templateDATAToPost) {
      const bearer_token = `Bearer ${localStorage.getItem("token")}`;
      const config = {
        headers: {
          Authorization: bearer_token,
        },
      };
      try {
        await axios
          .post(this.templateURL, templateDATAToPost, config)
          .then((response) => {
            this.getEmailDatas();
            this.toastrResponse = {status: "Success", msg: "Email Template has been created!"};
            this.showToast(); 
            console.log(response);
          });
      } catch (err) {
        console.error(err);
        this.toastrResponse = {status: "Error", msg: "Email Template could not be created!"};
        this.showToast(); 
      }
    },
    backupContent(draftToBackup) {
      console.log(draftToBackup);
      this.backupContentData = draftToBackup;
    },
    cancelUpdate(templateID) {
      for (let i = 0; i < this.emailTemplates.length; i++) {
        // console.log(this.EmailTemplates[i]["templateName"])
        if (this.emailTemplates[i]["templateId"] == templateID) {
          this.emailTemplates[i]["templateData"] = this.backupContentData;
        }
      }
      this.getEmailDatas();
    },
    newTemplateInitialState(){
      this.newTemplateName=``,
      this.newTemplateSubject=``,
      this.newTemplateData=``,
      this.$refs.myVueContent.setHTML("")
    },
    templateCreationUpdateDisable(templateName, templateSubject){
      return (templateName.length>0 && templateSubject.length>0)
    }
  },
};
</script>

<style scoped>
.break-text p {
  word-wrap: break-word;
}
</style>
