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
          v-for="(template, index) in emailTemplates"
          :key="template.templateId"
        >
          <div v-if="!template.defunct">
            <!-- change here end -->
            <div class="card mb-3 border-secondary">
              <div class="card-header text-dark bg-light">
                Template {{ index + 1 }}
              </div>
              <div class="card-header text-white bg-dark">
                <h2>{{ template.templateName }}</h2>
              </div>
              <div class="card-body border-secondary">
                <p class="text-start" v-html="template.templateData"></p>
              </div>
              <div class="card-footer border-secondary btn-group-vertical">
                <button
                  type="button"
                  class="btn btn-warning"
                  data-bs-toggle="modal"
                  :data-bs-target="`#emailModal` + template.templateId"
                  @click="backupContent(template.templateData)"
                >
                  Edit
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
              <div class="modal-dialog modal-xl">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">
                      Update {{ template.templetId }} information
                    </h5>
                    <button
                      type="button"
                      class="btn-close"
                      data-bs-dismiss="modal"
                      aria-label="Close"
                      @click="cancelUpdate(template.templateId)"
                    ></button>
                  </div>
                  <div class="modal-body">
                    <div class="mb-3">
                      <!-- <label class="col-form-label"><b>ID:</b>5</label><br> -->
                      <label class="col-form-label">Template Name:</label>
                      <input
                        class="form-control"
                        required
                        v-model="template.templateName"
                      />
                      <label class="col-form-label">Template Data:</label>
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
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
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
            ></button>
          </div>

          <div class="modal-body text-start">
            <form>
              <div class="mb-3">
                <!-- <label class="col-form-label"><b>ID:</b>5</label><br> -->
                <label class="col-form-label">Template Name:</label>
                <input
                  class="form-control"
                  required
                  v-model="newTemplateName"
                />
                <label class="col-form-label">Template Data:</label>
                <QuillEditor
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
              data-bs-dismiss="modal"
            >
              Create Template
            </button>
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
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
  </div>
</template>

<script>
import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";
import EmailService from "@/api/services/EmailService";
import axios from "axios";

export default {
  name: "Email",
  components: {
    QuillEditor,
  },
  data() {
    return {
      templateURL: "http://localhost:8081/templates",
      emailTemplates: [],
      newTemplateName: ``,
      newTemplateData: ``,
      EmailTemplates: [],
      vueContent: ``,
      backupContentData: ``,
      templateDataToUpdate: {
        templateName: "test1",
        templateData: "test1",
        defunct: true,
      },
    };
  },
  async mounted() {
    this.getEmailDatas();
  },
  created() {
    // this.EmailTemplates = [
    //     {
    //       "templateName": "ePass",
    //       "templateData": "<h1>Dear %s!!borrower's name!!,</h1><p>We are pleased to inform that your booking to %s!!attraction name!! is confirmed as follows:&nbsp;</p><p><br></p><p>Date of Visit :Saturday, 12 February 2022 (1 day only)</p><p>Membership ID :%d!!corp pass number!!</p><p><strong>&nbsp;</strong></p><p>For any change in visit date, you are required to cancel your booking (at least 1 day before) and to submit a new booking in the system.&nbsp;</p><p><br></p><p>Attached is the Corporate Membership letter to %s!!attraction name!!. Please check that the details are accurate.&nbsp;</p><p><br></p><p>Please take note of the following on the day of your visit to %s!!attraction name!!:</p><ul><li>Present this email, the attached corporate membership letter and your staff pass at the entrance of %s!!attraction name!!.&nbsp;</li><li>Entry is strictly based on your details in this email and corporate membership letter.</li><li>Your presence is required on the day of visit. Entry will be denied without staff's presence.&nbsp;</li><li>Your booking is non-transferable. Entry is strictly based on the details in this email and Corporate Membership letter.&nbsp;</li><li>Visit date is strictly based on the date stated in this email and Corporate Membership letter.&nbsp;</li><li>Staff found abusing the Corporate Membership letter will be subject to disciplinary actions.&nbsp;</li></ul><p>Enjoy your visit to %s!!attraction name!!!&nbsp;</p><p><br></p><p>Warm regards</p><p>HR Department </p>",
    //       "defunct": false,
    //       "id": 1,
    //     },
    //     {
    //       "templateName": "physicalPass",
    //       "templateData": "<h1>Dear %s!!borrower's name!!,</h1><p><br></p><p>We are pleased to inform that your booking to %s!!attraction name!! is confirmed as follows:&nbsp;</p><p><br></p><p>Date of Visit :Saturday, 12 February 2022 (1 day only)</p><p>Membership ID :%d!!corp pass number!!</p><p><strong>&nbsp;</strong></p><p>For any change in visit date, you are required to cancel your booking (at least 1 day before) and to submit a new booking in the system.&nbsp;</p><p><br></p><p>Attached is the authorisation letter to %s!!attraction name!!. Please check that the details are accurate.&nbsp;</p><p><br></p><p>Please take note of the following for your visit to %s!!attraction name!!:</p><ul><li>Present this email confirmation to the General Office to collect the membership card(s).</li><li>Collect the membership card(s) from the General Office one day before your visit date and return the membership card(s) by 9am the next working day after your visit.&nbsp;</li><li>Present the membership card(s), the authorisation letter and your staff pass at the entrance of %s!!attraction name!!.&nbsp;</li><li>Entry is strictly based on the membership card(s) and the authorisation letter.</li><li>Your presence is required on the day of visit. Entry will be denied without staff’s presence.&nbsp;</li><li>Your booking is non-transferable.&nbsp;</li><li>Visit date is strictly based on the date stated in this email and the authorisation letter.&nbsp;</li><li>Staff found abusing the membership(s) will be subject to disciplinary actions.&nbsp;</li></ul><p><br></p><p>Enjoy your visit to %s!!attraction name!!!&nbsp;</p><p><br></p><p>Warm regards</p><p>HR Department </p><p>",
    //       "defunct": false,
    //       "id": 2,
    //     }
    // ]
  },
  methods: {
    async getEmailDatas() {
      var tempEmailTemplates = await EmailService.getAllEmailTemplates();
      this.emailTemplates = tempEmailTemplates.data;
      console.log(this.emailTemplates);
    },
    async deleteTemplateMethod(templateID) {
      try {
        await axios
          .delete(this.templateURL + "/" + templateID)
          .then((response) => {
            this.getEmailDatas();
            console.log(response);
          });
      } catch (err) {
        console.error(err);
      }
    },
    updateTemplateMethod(templateID) {
      var temp;
      for (let i = 0; i < this.emailTemplates.length; i++) {
        if (this.emailTemplates[i]["templateId"] == templateID) {
          temp = {
            templateName: this.emailTemplates[i].templateName,
            templateData: this.emailTemplates[i].templateData,
            defunct: this.emailTemplates[i].defunct,
          };
        }
      }
      this.putTemplateMethod(templateID, temp);
    },
    async putTemplateMethod(templateID, templateDATA) {
      try {
        await axios
          .put(this.templateURL + "/" + templateID, templateDATA)
          .then((response) => {
            this.backupContentData = ``;
            this.getEmailDatas();
            console.log(response);
          });
      } catch (err) {
        console.error(err);
      }
    },
    createTemplateMethod() {
      var temp = {
        templateName: this.newTemplateName,
        templateData: this.newTemplateData,
        defunct: false,
      };
      this.newTemplateName = ``;
      this.newTemplateData = ``;
      this.PostTemplateMethod(temp);
    },
    async PostTemplateMethod(templateDATAToPost) {
      try {
        await axios
          .post(this.templateURL, templateDATAToPost)
          .then((response) => {
            this.getEmailDatas();
            console.log(response);
          });
      } catch (err) {
        console.error(err);
      }
    },
    // saveContent(){
    //   this.backupContentData = ``
    //   console.log(this.EmailTemplates[1]["templateData"])
    // },
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
    },
  },
};
</script>

<style scoped>
.break-text p {
  word-wrap: break-word;
}
</style>
