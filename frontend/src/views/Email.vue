<template>
  <div class="container">
    <div class="flex-column d-flex align-items-center justify-content-center">
      <h2 class="pt-4 mb-4">Email</h2>
      <div class="tableBox position-relative">
        <hr />
        <div class="d-flex mb-5 aligns-items-center justify-content-center">
          <span>
            <button
              type="button"
              class="btn btn-outline-success d-inline p-2"
              data-bs-toggle="modal"
              data-bs-target="#addNewEmailTemplate"
            >
              New Email Template
            </button>
          </span>
        </div>

        <div class="container-fluid mt-3">
          <div class="row row-cols-1 g-5">
            <template
              v-for="template in emailTemplates"
              :key="template.templateId"
            >
              <div v-if="!template.defunct">
                <div class="card text-center mb-3">
                  <div
                    class="card-header"
                    style="background-color: var(--sss_orange); color: #273746"
                  >
                    <div>
                      <h2>
                        <strong>Template ID - {{ template.templateId }}</strong>
                      </h2>
                    </div>
                  </div>
                  <ul class="list-group list-group-flush">
                    <div class="container">
                      <div class="row">
                        <div class="col-4 border pt-2">
                          <h4 class="text-center"><b>Template Name</b></h4>
                        </div>
                        <div class="col-8 border pt-2">
                          <h4 class="text-center">
                            {{ template.templateName }}
                          </h4>
                        </div>
                        <div class="col-4 border pt-2">
                          <h4 class="text-center"><b>Template Subject</b></h4>
                        </div>
                        <div class="col-8 border pt-2">
                          <h4 class="text-center">
                            {{ template.templateSubject }}
                          </h4>
                        </div>
                      </div>
                    </div>
                  </ul>

                  <div class="card-body border-secondary">
                    <p class="text-start" v-html="template.templateData"></p>
                  </div>
                  <div
                    class="card-footer border-secondary btn-group-vertical"
                  >
                    <button
                      type="button"
                      class="btn btn-info"
                      data-bs-toggle="modal"
                      :data-bs-target="`#emailModal` + template.templateId"
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
                          @click="cancelUpdate()"
                        ></button>
                      </div>
                      <div class="modal-body text-start">
                        <div class="mb-3">
                          <label class=""
                            ><b class="col-form-label"
                              >Template ID: {{ template.templateId }}</b
                            ></label
                          ><br />
                          <div class="input-group mb-3 was-validated">
                            <label class="col-form-label col-12"
                              ><b>Template Name</b></label
                            >
                            <div class="col-12">
                              <input
                                class="form-control"
                                v-model="template.templateName"
                                required
                              />
                              <div class="invalid-feedback text-black-50">
                                Please enter a valid template name. e.g: Account
                                Management Template
                              </div>
                            </div>
                          </div>
                          <div class="input-group mb-3 was-validated">
                            <label class="col-form-label col-12"
                              ><b>Template Subject</b></label
                            >
                            <div class="col-12">
                              <input
                                class="form-control"
                                v-model="template.templateSubject"
                                required
                              />
                              <div class="invalid-feedback text-black-50">
                                Please enter a valid template subject. e.g:
                                Singapore Sport School - Corp Pass Account
                                Registration
                              </div>
                            </div>
                          </div>
                          <label class="col-form-label col-12"
                            ><b>Template Data</b></label
                          >
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
                          class="btn btn-secondary"
                          data-bs-dismiss="modal"
                          @click="cancelUpdate()"
                        >
                          Close
                        </button>
                        <button
                          type="btn"
                          class="btn btn-danger"
                          data-bs-dismiss="modal"
                          @click="deleteTemplateMethod(template.templateId)"
                        >
                          Delete
                        </button>
                        <button
                          type="button"
                          class="btn btn-primary"
                          data-bs-dismiss="modal"
                          @click="updateTemplateMethod(template.templateId)"
                          :disabled="
                            !templateCreationUpdateDisable(
                              template.templateName,
                              template.templateSubject
                            )
                          "
                        >
                          Save
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

    <!-- Add Email Modal Below -->
    <div
      class="modal fade"
      data-bs-backdrop="static"
      id="addNewEmailTemplate"
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
                <div class="input-group mb-3 was-validated">
                  <label class="col-form-label col-12"
                    ><b>Template Name</b></label
                  >
                  <div class="col-12">
                    <input
                      class="form-control"
                      v-model="newTemplateName"
                      required
                    />
                    <div class="invalid-feedback text-black-50">
                      Please enter a valid template name. e.g: Account
                      Management Template
                    </div>
                  </div>
                </div>
                <div class="input-group mb-3 was-validated">
                  <label class="col-form-label col-12"
                    ><b>Template Subject</b></label
                  >
                  <div class="col-12">
                    <input
                      class="form-control"
                      v-model="newTemplateSubject"
                      required
                    />
                    <div class="invalid-feedback text-black-50">
                      Please enter a valid template subject. e.g: Singapore
                      Sport School - Corp Pass Account Registration
                    </div>
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
              class="btn btn-secondary"
              data-bs-dismiss="modal"
              @click="newTemplateInitialState"
            >
              Cancel
            </button>
            <button
              type="button"
              class="btn btn-primary"
              @click="createTemplateMethod()"
              data-bs-dismiss="modal"
              :disabled="
                !templateCreationUpdateDisable(
                  newTemplateName,
                  newTemplateSubject
                )
              "
            >
              Create
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
import ENDPOINT from "../constants";
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
      toastrResponse: "",
    };
  },
  async mounted() {
    this.getEmailDatas();
  },
  created() {},
  methods: {
    showToast() {
      var bsAlert = new Toast(document.getElementById("theToastr"));
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
            this.toastrResponse = {
              status: "Success",
              msg: "Email Template has been deleted!",
            };
            this.showToast();
          });
      } catch (err) {
        console.error(err);
        this.toastrResponse = {
          status: "Error",
          msg: "Email Template could not be deleted!",
        };
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
            this.toastrResponse = {
              status: "Success",
              msg: "Email Template has been updated!",
            };
            this.showToast();
            this.getEmailDatas();
            console.log(response);
          });
      } catch (err) {
        console.error(err);
        this.toastrResponse = {
          status: "Error",
          msg: "Email Template could not be updated!",
        };
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
            this.toastrResponse = {
              status: "Success",
              msg: "Email Template has been created!",
            };
            this.showToast();
            console.log(response);
          });
      } catch (err) {
        console.error(err);
        this.toastrResponse = {
          status: "Error",
          msg: "Email Template could not be created!",
        };
        this.showToast();
      }
    },
    cancelUpdate() {
      this.getEmailDatas();
    },
    newTemplateInitialState() {
      (this.newTemplateName = ``),
        (this.newTemplateSubject = ``),
        (this.newTemplateData = ``),
        this.$refs.myVueContent.setHTML("");
    },
    templateCreationUpdateDisable(templateName, templateSubject) {
      return templateName.length > 0 && templateSubject.length > 0;
    },
  },
};
</script>

<style>
.break-text p {
  word-wrap: break-word;
}

.templateHeader {
  background-color: var(--sss_orange);
  color: #273746;
  text-align: center;
}
</style>
