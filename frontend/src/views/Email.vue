<template>
  <div class="container">
    <div class="flex-column d-flex align-items-center justify-content-center">
      <h1 class="pt-4 mb-4">Email</h1>
      <div class="tableBox position-relative">
        <hr/>
        <div class="d-flex mb-5 aligns-items-center justify-content-center">
          <span>
              <button
                type="button"
                class="btn btn-outline-success d-inline p-2"
                data-bs-toggle="modal"
                data-bs-target="#addNewEmailTemplate"
              >
                Add New Email Template
                <i class="bi bi-plus-square"></i>
              </button>
          </span>
        </div>
          
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
                            <div class="col-12 py-2 border templateHeader"><h1 class="text-center">Template ID {{template.templateId }}</h1></div>
                            <div class="col-4 border"><h2 class="text-center"><b>Template Name</b></h2></div>
                            <div class="col-8 border"><h2 class="text-center">{{template.templateName }}</h2></div>
                            <div class="col-4 border"><h2 class="text-center"><b>Template Subject</b></h2></div>
                            <div class="col-8 border"><h2 class="text-center">{{template.templateSubject }}</h2></div>
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

      </div>
    </div>
    <!-- <div class="row">
      <h1>Email</h1>
    </div> -->
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
          <TheToastr :toastrResponse="toastrResponse"></TheToastr>
  </div>
</template>

<script>
import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";
import EmailService from "@/api/services/EmailService";
import axios from "axios";
import ENDPOINT from "../constants"
import TheToastr from "@/components/TheToastr.vue";
import { Toast } from "bootstrap";

export default {
  name: "Email",
  components: {
    TheToastr,
    QuillEditor,
  },
  data() {
    return {
      templateURL: `${ENDPOINT}/templates`,
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
      this.backupContentData = draftToBackup;
    },
    cancelUpdate(templateID) {
      for (let i = 0; i < this.emailTemplates.length; i++) {
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

.templateHeader {
  background-color: var(--sss_orange);
  color: #273746;
  text-align: cente;
}
</style>
