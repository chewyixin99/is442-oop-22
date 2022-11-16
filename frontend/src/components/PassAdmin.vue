<template>
  <div class="container">
    <div class="row">
      <h1 class="d-inline pt-4 mb-4 text-center">Pass</h1>
    </div>
    <hr />
    <div class="d-flex aligns-items-center justify-content-center">
      <span>
        <button
          type="button"
          class="btn btn-outline-success d-inline p-2"
          data-bs-toggle="modal"
          data-bs-target="#addNewPass"
        >
          New Pass
        </button>
      </span>
    </div>

    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 my-2 justify-content-center">
      <!-- Passes 'Card' -->
      <template v-for="EachPass in PassAdminPasses" :key="EachPass.passId">
        <div v-if="EachPass.defunct == '0'">
          <div class="card text-center border h-100">
            <!-- Show Image here -->
            <div
              class="card-header"
              style="background-color: var(--sss_orange); color: #273746"
            >
              <div>
                <h4>
                  <strong>Card ID - {{ EachPass.passId }}</strong>
                </h4>
              </div>
            </div>
            <template v-if="EachPass.passImage">
              <img
                v-bind:src="`data:image/*;base64,${EachPass.passImage}`"
                class="mx-auto mt-2 d-block border border-2"
                height="250"
              />
            </template>

            <template v-else>
              <img
                src="../assets/PassAssets/noImageAvailable.jpg"
                class="mx-auto mt-2 d-block border border-2"
                height="250"
                
              />
            </template>

            <div class="card-body">
              <b class="card-title">{{ EachPass.passDesc }} </b>
            </div>

            <ul class="px-3 py-2 list-group list-group-flush">
              <span><b>Point of Interest </b></span>
              <div class="text-wrap w-90 justify-content-center">
                <span>{{ EachPass.poi }}</span>
              </div>
            </ul>

            <ul class="px-3 py-2 list-group list-group-flush">
              <span><b>Website Link</b></span>
              <div class="text-wrap w-90 justify-content-center">
                <span>{{ EachPass.poiUrl }}</span>
              </div>
            </ul>

            <ul class="list-group list-group-flush">
              <div class="container">
                <div class="p-1 row">
                  <div class="col-7 border"><b>Pass No.</b></div>
                  <div class="col-5 border">{{ EachPass.passNumber }}</div>
                  <div class="col-7 border"><b>No. of Guests</b></div>
                  <div class="col-5 border">{{ EachPass.numGuests }}</div>
                  <div class="col-7 border"><b>Replacement Fee</b></div>
                  <div class="col-5 border">${{ EachPass.replacementFee }}</div>
                  <div class="col-7 border"><b>Status</b></div>
                  <div class="col-5 border">{{ EachPass.passStatus }}</div>
                  <div class="col-7 border"><b>Physical / EPass</b></div>
                  <template v-if="EachPass.isPhysical == true">
                    <div class="col-5 border">Physical Pass</div>
                  </template>
                  <template v-else>
                    <div class="col-5 border">EPass</div>
                  </template>
                </div>
                <div class="px-1 py-2 col-12 d-flex justify-content-center">
                  <b>Attachment(PDF)</b>
                </div>
                <template v-if="EachPass.passAttachment">
                  <div class="col-12 d-flex justify-content-center">
                    <button
                      class="btn btn-success mb-1"
                      @click="downloadData(EachPass.passId)"
                    >
                      {{ EachPass.passAttachmentName }}
                    </button>
                  </div>
                </template>

                <template v-else>
                  <div class="col-12 py-2 d-flex justify-content-center">
                      No File Attached
                  </div>
                </template>
              </div>
            </ul>

            <div
              class="card-footer btn-group-vertical"
            >
              <!-- View button prompt Passes update modal -->
              <button
                type="button"
                class="btn btn-info"
                data-bs-toggle="modal"
                :data-bs-target="`#viewUM` + EachPass.passId"
              >
                View
              </button>
            </div>
          </div>
          <!-- Pass update modal -->
          <div
            class="modal fade"
            data-bs-backdrop="static"
            :id="`viewUM` + EachPass.passId"
            tabindex="-1"
            aria-labelledby="exampleModalLabel"
            aria-hidden="true"
          >
            <div class="modal-dialog modal-dialog-scrollable modal-lg">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="exampleModalLabel">
                    Update pass information
                  </h5>
                  <button
                    type="button"
                    class="btn-close"
                    data-bs-dismiss="modal"
                    aria-label="Close"
                    @click="initialStateEachPass(EachPass.passId)"
                  ></button>
                </div>
                <div class="modal-body text-start">
                  <form>
                    <div class="mb-3">
                      <div class="row mb-3 d-flex justify-content-center">
                        <div>
                          <template v-if="localImageSrc">
                            <img
                              :src="localImageSrc"
                              class="mx-auto d-block border border-2"
                              height="200"
                              width="300"
                            />
                          </template>
                          <template v-else-if="EachPass.passImage">
                            <img
                              v-bind:src="`data:image/*;base64,${EachPass.passImage}`"
                              class="mx-auto d-block border border-2"
                              height="200"
                              width="300"
                            />
                          </template>
                          <template v-else>
                            <img
                              src="../assets/PassAssets/noImageAvailable.jpg"
                              class="mx-auto d-block border border-2"
                              height="200"
                              width="300"
                            />
                          </template>
                        </div>
                        <div class="d-flex justify-content-center my-1">
                          <button
                            type="button"
                            class="btn btn-outline-danger"
                            @click="removeImageFromDB(EachPass.passId)"
                            :disabled="!EachPass.passImage"
                          >
                            <svg
                              xmlns="http://www.w3.org/2000/svg"
                              width="16"
                              height="16"
                              fill="currentColor"
                              class="bi bi-trash"
                              viewBox="0 0 16 16"
                            >
                              <path
                                d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"
                              ></path>
                              <path
                                fill-rule="evenodd"
                                d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"
                              ></path>
                            </svg>
                            Remove Image
                          </button>
                        </div>

                        <div class="input-group mb w-50">
                          <label class="col-12 col-form-label text-center"
                            ><b
                              >Replace current image</b
                            ></label
                          >
                          <input
                            class="col-8 col-md-5 form-control form-control"
                            type="file"
                            accept="image/*"
                            @change="onImageSelected"
                            :id="`updateImageUploadInput` + EachPass.passId"
                          />
                          <button
                            class="col-4 col-md-2 btn btn-outline-danger"
                            type="button"
                            @click="
                              removeImageSelect_newAndUpdatePass(
                                EachPass.passId
                              )
                            "
                          >
                            X
                          </button>
                        </div>
                      </div>
                      <hr />
                      <label class="col-form-label"
                        ><b>Pass ID: {{ EachPass.passId }}</b></label
                      ><br />
                      <div class="input-group mb-1 was-validated">
                        <label class="col-form-label col-12"
                          ><b>Pass No.</b></label
                        >
                        <div class="col-12">
                          <input
                            type="number"
                            min="0"
                            class="form-control"
                            v-model="EachPass.passNumber"
                            required
                          />
                          <div class="invalid-feedback text-black-50">
                            Please enter pass numbers. e.g: 213750
                          </div>
                        </div>
                      </div>
                      <div class="input-group mb-1 was-validated">
                        <label class="col-form-label col-12"
                          ><b>Pass Description</b></label
                        >
                        <div class="col-12">
                          <input
                            class="form-control"
                            v-model="EachPass.passDesc"
                            required
                          />
                          <div class="invalid-feedback text-black-50">
                            Please enter pass descriptions. e.g: Take a break!
                          </div>
                        </div>
                      </div>
                      <div class="input-group mb-1 was-validated">
                        <label class="col-form-label col-12"
                          ><b>Point of Interest</b></label
                        >
                        <div class="col-12">
                          <input
                            class="form-control"
                            v-model="EachPass.poi"
                            required
                          />
                          <div class="invalid-feedback text-black-50">
                            Please enter place of interest(s). e.g: Singapore
                            Zoo
                          </div>
                        </div>
                      </div>
                      <div class="input-group mb-3 was-validated">
                        <label class="col-form-label col-12"
                          ><b>Website Link</b></label
                        >
                        <div class="col-12">
                          <input
                            class="form-control"
                            v-model="EachPass.poiUrl"
                            required
                          />
                          <div class="invalid-feedback text-black-50">
                            Please enter website of place of interest(s). e.g:
                            www.sportsschool.edu.sg
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="input-group mb-3 was-validated">
                      <label class="col-5 col-form-label"
                        ><b>No. of Guests</b></label
                      >
                      <div class="col-7">
                        <input
                          type="number"
                          v-model="EachPass.numGuests"
                          class="form-control"
                          required
                        />
                        <div class="invalid-feedback text-black-50">
                          Please enter more than 1 guest(s). e.g: 5
                        </div>
                      </div>
                    </div>
                    <div class="input-group mb-3 was-validated">
                      <label class="col-5 col-form-label"
                        ><b>Replacement Fee</b></label
                      >
                      <div class="col-7">
                        <div class="input-group">
                          <div class="input-group-text">$</div>
                          <input
                            type="number"
                            v-model="EachPass.replacementFee"
                            class="form-control"
                            step="0.01"
                            min="0.00"
                            required
                          />
                          <div class="invalid-feedback text-black-50">
                            Please enter replacement fee. e.g: 15.50
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="input-group mb-3 was-validated">
                      <label class="col-5 col-form-label"><b>Status</b></label>
                      <div class="col-7">
                        <select
                          class="form-select"
                          required
                          v-model="EachPass.passStatus"
                        >
                          <option value="">Select Pass Status</option>
                          <option value="AVAILABLE">Available</option>
                          <option value="ONLOAN">On Loan</option>
                          <option value="UNRETURNED">Unreturned</option>
                        </select>
                        <div class="invalid-feedback text-black-50">
                          Please select a valid pass status.
                        </div>
                      </div>
                    </div>
                    <div class="input-group mb-3 was-validated">
                      <label class="col-5 col-form-label"
                        ><b>Physical/Epass</b></label
                      >
                      <div class="col-7">
                        <select
                          class="form-select"
                          required
                          v-model="EachPass.isPhysical"
                        >
                          <option value="">Select Pass Type</option>
                          <option value="true">Epass</option>
                          <option value="false">Physical Pass</option>
                        </select>
                        <div class="invalid-feedback text-black-50">
                          Please select a valid pass type.
                        </div>
                      </div>
                    </div>
                    <hr />
                    <div class="mb-3">
                      <h3>Atttachment information</h3>
                      <div class="row mb-3">
                        <div class="input-group mb-3">
                          <label class="col-5 col-form-label"
                            ><b>Current Attachment(PDF)</b></label
                          >
                          <input
                            type="text"
                            class="col-5 form-control"
                            :placeholder="EachPass.passAttachmentName"
                            ref="updatePassAttachmentText"
                            disabled
                          />
                          <button
                            type="button"
                            class="btn btn-outline-danger"
                            @click="removeFileFromDB(EachPass.passId)"
                            :disabled="!EachPass.passAttachmentName"
                          >
                            <svg
                              xmlns="http://www.w3.org/2000/svg"
                              width="16"
                              height="16"
                              fill="currentColor"
                              class="bi bi-trash"
                              viewBox="0 0 16 16"
                            >
                              <path
                                d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"
                              ></path>
                              <path
                                fill-rule="evenodd"
                                d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"
                              ></path>
                            </svg>
                            <span class="visually-hidden">Button</span>
                          </button>
                        </div>

                        <div class="input-group">
                          <label class="col-5 col-form-label"
                            ><b>New Attachment(PDF)</b></label
                          >
                          <input
                            class="col-7 form-control"
                            type="file"
                            accept=".pdf"
                            @change="onFileSelected"
                          />
                        </div>
                      </div>
                    </div>
                  </form>
                </div>
                <div class="modal-footer">
                  <button
                    type="button"
                    class="btn btn-secondary"
                    data-bs-dismiss="modal"
                    @click="initialStateEachPass(EachPass.passId)"
                  >
                    Cancel
                  </button>
                  <button
                    type="button"
                    class="btn btn-danger"
                    @click="deletePassMethod(EachPass.passId)"
                    data-bs-dismiss="modal"
                  >
                    Delete
                  </button>
                  <button
                    type="button"
                    class="btn btn-primary"
                    @click="updatePassMethod(EachPass.passId)"
                    data-bs-dismiss="modal"
                    :disabled="
                      !passNewUpdateButtonIsDisabled(
                        EachPass.passNumber,
                        EachPass.passDesc,
                        EachPass.poi,
                        EachPass.poiUrl,
                        EachPass.numGuests,
                        EachPass.replacementFee,
                        EachPass.passStatus,
                        EachPass.isPhysical
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
      <!-- ====================================Loop ends============================= -->
    </div>
    <!-- Add New Pass Form Modal/New Pass Modal-->
    <div
      class="modal fade"
      data-bs-backdrop="static"
      id="addNewPass"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-scrollable modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Create new pass</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
              @click="initialState"
            ></button>
          </div>

          <div class="modal-body text-start">
            <form>
              <div class="row mb-3 d-flex justify-content-center">
                <div v-if="localImageSrc">
                  <img
                    :src="localImageSrc"
                    class="mx-auto d-block img-thumbnail"
                    height="200"
                    width="300"
                  />
                </div>
                <div class="input-group mb w-75">
                  <label class="col-12 col-form-label text-center"
                    ><b>Insert image here(if any)</b></label
                  >
                  <input
                    class="col-8 col-md-5 form-control form-control"
                    type="file"
                    accept="image/*"
                    @change="onImageSelected"
                    id="newImageUploadInput"
                  />
                  <button
                    class="col-4 col-md-2 btn btn-outline-danger"
                    type="button"
                    @click="removeImageSelect_newPass()"
                  >
                    X
                  </button>
                </div>
              </div>
              <hr />
              <div class="input-group mb-1 was-validated">
                <label class="col-form-label col-12"><b>Pass No.</b></label>
                <div class="col-12">
                  <input
                    type="number"
                    min="0"
                    class="form-control col-12"
                    required
                    v-model="NEWpassNumber"
                  />
                  <div class="invalid-feedback text-black-50">
                    Please enter pass numbers. e.g: 213750
                  </div>
                </div>
              </div>
              <div class="input-group mb-1 was-validated">
                <label class="col-form-label col-12"><b>Description</b></label>
                <div class="col-12">
                  <input
                    class="form-control col-12"
                    required
                    v-model="NEWpassDesc"
                  />
                  <div class="invalid-feedback text-black-50">
                    Please enter pass descriptions. e.g: Take a break!
                  </div>
                </div>
              </div>
              <div class="input-group mb-1 was-validated">
                <label class="col-form-label col-12"
                  ><b>Point of Interest</b></label
                >
                <div class="col-12">
                  <input
                    class="form-control col-12"
                    required
                    v-model="NEWpoi"
                  />
                  <div class="invalid-feedback text-black-50">
                    Please enter place of interest(s). e.g: Singapore Zoo
                  </div>
                </div>
              </div>
              <div class="input-group mb-3 was-validated">
                <label class="col-form-label col-12"
                  ><b>Website Link</b></label
                >
                <div class="col-12">
                  <input class="form-control" required v-model="NEWpoiUrl" />
                  <div class="invalid-feedback text-black-50">
                    Please enter website of place of interest(s). e.g:
                    www.sportsschool.edu.sg
                  </div>
                </div>
              </div>
              <div class="input-group mb-3 was-validated">
                <label class="col-5 col-form-label"
                  ><b>No. of Guests</b></label
                >
                <div class="col-7">
                  <input
                    class="form-control"
                    type="number"
                    required
                    v-model="NEWnumGuests"
                  />
                  <div class="invalid-feedback text-black-50">
                    Please enter more than 1 guest(s). e.g: 5
                  </div>
                </div>
              </div>
              <div class="input-group mb-3 was-validated">
                <label class="col-5 col-form-label"
                  ><b>Replacement Fee</b></label
                >
                <div class="col-7">
                  <div class="input-group">
                    <div class="input-group-text">$</div>
                    <input
                      type="number"
                      step="0.01"
                      class="form-control"
                      min="0.00"
                      required
                      v-model="NEWreplacementFee"
                    />
                    <div class="invalid-feedback text-black-50">
                      Please enter replacement fee. e.g: 15.50
                    </div>
                  </div>
                </div>
              </div>
              <div class="input-group mb-3 was-validated">
                <label class="col-5 col-form-label"><b>Status</b></label>
                <div class="col-7">
                  <select class="form-select" required v-model="NEWpassStatus">
                    <option value="">Select Pass Status</option>
                    <option value="AVAILABLE">Available</option>
                    <option value="ONLOAN">On Loan</option>
                    <option value="UNRETURNED">Unreturned</option>
                  </select>
                  <div class="invalid-feedback text-black-50">
                    Please select a valid pass status.
                  </div>
                </div>
              </div>
              <div class="input-group mb-3 was-validated">
                <label class="col-5 col-form-label"
                  ><b>Physical/Epass</b></label
                >
                <div class="col-7">
                  <select class="form-select" required v-model="NEWisPhysical">
                    <option value="">Select Pass Type</option>
                    <option value="true">Epass</option>
                    <option value="false">Physical Pass</option>
                  </select>
                  <div class="invalid-feedback text-black-50">
                    Please select a valid pass type.
                  </div>
                </div>
              </div>
              <hr />
              <div class="mb-3">
                <h3>Atttachment information</h3>
                <div class="row mb-3">
                  <div class="input-group mb-3">
                    <label class="col-12 col-md-5 col-form-label"
                      ><b>New Attachment(PDF)</b></label
                    >
                    <input
                      class="col-8 col-md-5 form-control"
                      type="file"
                      accept=".pdf"
                      @change="onFileSelected"
                      ref="newFileUpload"
                    />
                    <button
                      class="col-4 col-md-2 btn btn-outline-danger"
                      type="button"
                      @click="removeFileSelect_addNewPass()"
                    >
                      X
                    </button>
                  </div>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
              @click="initialState()"
            >
              Close
            </button>
            <button
              type="button"
              class="btn btn-primary"
              @click="onUpload()"
              data-bs-dismiss="modal"
              :disabled="
                !passNewUpdateButtonIsDisabled(
                  NEWpassNumber,
                  NEWpassDesc,
                  NEWpoi,
                  NEWpoiUrl,
                  NEWnumGuests,
                  NEWreplacementFee,
                  NEWpassStatus,
                  NEWisPhysical
                )
              "
            >
              Create Pass
            </button>
          </div>
        </div>
      </div>
    </div>
    <TheToastr :toastrResponse="toastrResponse"></TheToastr>
  </div>
</template>

<script>
import axios from "axios";
import ENDPOINT from "../constants";
import TheToastr from "@/components/TheToastr.vue";
import { Toast } from "bootstrap";

export default {
  name: "PassAdmin",
  props: {
    PassAdminPasses: Array,
  },
  components: {
    TheToastr,
  },
  data() {
    return {
      passURL: `${ENDPOINT}/passes`,
      selectedFile: null,
      selectedFileName: null,
      NEWpassDesc: null,
      NEWpoi: null,
      NEWpassNumber: null,
      NEWpoiUrl: null,
      NEWnumGuests: null,
      NEWreplacementFee: null,
      NEWpassStatus: "",
      NEWisPhysical: "",
      selectedImage: null,
      localImageSrc: null,

      toastrResponse: "",
    };
  },
  mounted() {},
  methods: {
    showToast() {
      var bsAlert = new Toast(document.getElementById("theToastr"));
      bsAlert.show();
    },
    onFileSelected(event) {
      this.selectedFile = event.target.files[0];
      this.selectedFileName = this.selectedFile.name;
      if (this.selectedFile.length === 0) {
        this.selectedFile = null;
        return;
      }
    },
    onImageSelected(event) {
      this.selectedImage = event.target.files[0];
      this.localImageSrc = URL.createObjectURL(this.selectedImage);
    },
    onUpload() {
      const bearer_token = `Bearer ${localStorage.getItem("token")}`;
      const config = {
        headers: {
          Authorization: bearer_token,
        },
      };
      const fd = new FormData();
      fd.append("passDesc", this.NEWpassDesc);
      fd.append("poi", this.NEWpoi);
      fd.append("poiUrl", this.NEWpoiUrl);
      fd.append("passNumber", this.NEWpassNumber);
      fd.append("numGuests", this.NEWnumGuests);
      fd.append("replacementFee", this.NEWreplacementFee);
      fd.append("isPhysical", this.NEWisPhysical);
      fd.append("passStatus", this.NEWpassStatus);
      if (this.selectedFile !== null) {
        fd.append("passAttachmentName", this.selectedFile.name);
        fd.append("passAttachment", this.selectedFile);
      }
      if (this.selectedImage !== null) {
        fd.append("passImage", this.selectedImage);
      }
      axios.post(`${ENDPOINT}/passes`, fd, config).then((res) => {
        console.log(res);
        this.toastrResponse = { status: "Success", msg: "New pass created!" };
        this.showToast();
        this.initialState();
        this.$emit("getPassData");
      });
    },
    async updatePassMethod(passID) {
      const bearer_token = `Bearer ${localStorage.getItem("token")}`;
      const config = {
        headers: {
          Authorization: bearer_token,
        },
      };
      const UPDATEfd = new FormData();
      UPDATEfd.append("passDesc", this.PassAdminPasses[passID - 1].passDesc);
      UPDATEfd.append("poi", this.PassAdminPasses[passID - 1].poi);
      UPDATEfd.append("poiUrl", this.PassAdminPasses[passID - 1].poiUrl);
      UPDATEfd.append(
        "passNumber",
        this.PassAdminPasses[passID - 1].passNumber
      );
      UPDATEfd.append("numGuests", this.PassAdminPasses[passID - 1].numGuests);
      UPDATEfd.append(
        "replacementFee",
        this.PassAdminPasses[passID - 1].replacementFee
      );
      UPDATEfd.append(
        "isPhysical",
        this.PassAdminPasses[passID - 1].isPhysical
      );
      UPDATEfd.append(
        "passStatus",
        this.PassAdminPasses[passID - 1].passStatus
      );
      if (this.selectedFile !== null) {
        UPDATEfd.append("passAttachmentName", this.selectedFile.name);
        UPDATEfd.append("passAttachment", this.selectedFile);
      }
      if (this.selectedImage !== null) {
        UPDATEfd.append("passImage", this.selectedImage);
      }
      try {
        await axios
          .put(this.passURL + "/" + passID, UPDATEfd, config)
          .then((response) => {
            this.selectedFile = null;
            this.toastrResponse = { status: "Success", msg: "Pass updated!" };
            this.showToast();
            this.$emit("getPassData");
            console.log(response);
          });
      } catch (err) {
        console.error(err);
        this.toastrResponse = {
          status: "Error",
          msg: "Pass could not be updated!",
        };
        this.showToast();
      }
    },
    downloadData(passID) {
      const data = this.PassAdminPasses[passID - 1].passAttachment;
      const linkSource = `data:application/pdf;base64,${data}`;
      const downloadLink = document.createElement("a");
      const fileName = this.PassAdminPasses[passID - 1].passAttachmentName;
      downloadLink.href = linkSource;
      downloadLink.download = fileName;
      downloadLink.click();
    },
    async deletePassMethod(passID) {
      const bearer_token = `Bearer ${localStorage.getItem("token")}`;
      const config = {
        headers: {
          Authorization: bearer_token,
        },
      };
      try {
        await axios
          .delete(this.passURL + "/" + passID, config)
          .then((response) => {
            this.toastrResponse = { status: "Success", msg: "Pass deleted!" };
            this.showToast();
            this.$emit("getPassData");
            console.log(response);
          });
      } catch (err) {
        this.toastrResponse = {
          status: "Error",
          msg: "Pass could not be deleted!",
        };
        this.showToast();
        console.error(err);
      }
    },
    async removeFileFromDB(passID) {
      const bearer_token = `Bearer ${localStorage.getItem("token")}`;
      const config = {
        headers: {
          Authorization: bearer_token,
        },
      };
      try {
        await axios
          .put(this.passURL + "/deletePassAttachment/" + passID, config)
          .then((response) => {
            this.$refs.updatePassAttachmentText.value = null;
            this.$emit("getPassData");
            console.log(response);
          });
      } catch (err) {
        console.error(err);
      }
    },
    async removeImageFromDB(passID) {
      const bearer_token = `Bearer ${localStorage.getItem("token")}`;
      const config = {
        headers: {
          Authorization: bearer_token,
        },
      };
      try {
        await axios
          .put(this.passURL + "/deletePassImage/" + passID, config)
          .then((response) => {
            this.removeImageSelect_newAndUpdatePass(passID);
            this.$emit("getPassData");
            console.log(response);
          });
      } catch (err) {
        console.error(err);
      }
    },
    removeFileSelect_addNewPass() {
      this.selectedFile = null;
      this.selectedFileName = null;
      this.$refs.newFileUpload.value = null;
    },
    removeImageSelect_newPass() {
      if (document.getElementById("newImageUploadInput").value !== null) {
        document.getElementById("newImageUploadInput").value = null;
      }
      this.selectedImage = null;
      this.localImageSrc = null;
    },
    removeImageSelect_newAndUpdatePass(passId) {
      var temp = "updateImageUploadInput" + passId;
      if (document.getElementById("newImageUploadInput").value !== null) {
        document.getElementById("newImageUploadInput").value = null;
      }
      if (document.getElementById(temp).value !== null) {
        document.getElementById(temp).value = null;
      }
      this.selectedImage = null;
      this.localImageSrc = null;
    },
    initialState() {
      this.selectedFile = null;
      this.selectedFileName = null;
      this.$refs.newFileUpload.value = null;

      this.NEWpassDesc = null;
      this.NEWpoi = null;
      this.NEWpoiUrl = null;
      this.NEWpassNumber = null;
      this.NEWnumGuests = null;
      this.NEWreplacementFee = null;
      this.NEWpassStatus = "";
      this.NEWisPhysical = "";

      this.selectedImage = null;
      this.localImageSrc = null;
      document.getElementById("newImageUploadInput").value = null;
      this.removeImageSelect_newPass();
      this.$emit("getPassData");
    },
    initialStateEachPass(PassId) {
      this.initialState();
      this.$emit("getPassData");
      this.removeImageSelect_newAndUpdatePass(PassId);
    },
    passNewUpdateButtonIsDisabled(
      passNum,
      passDesc,
      passPoi,
      passUrl,
      passGuests,
      passReplacementFee,
      passStatus,
      isPhysical
    ) {
        var decimalsLength = 0
        if(passReplacementFee% 1 !== 0){
            decimalsLength = String(passReplacementFee).split(".")[1]?.length;
        }

      return (
        passNum > 0 &&
        passNum % 1 == 0 &&
        passNum !== "" &&
        passDesc !== null &&
        passPoi !== null &&
        passUrl !== null &&
        passGuests > 0 &&
        passGuests % 1 == 0 &&
        passGuests !== null &&
        passReplacementFee > 0 &&
        decimalsLength< 3 &&
        passStatus !== "" &&
        isPhysical !== ""
      );
    },
  },
};
</script>
<style>
.passHeader {
  background-color: var(--sss_orange);
  color: #273746;
  text-align: center;
}
</style>