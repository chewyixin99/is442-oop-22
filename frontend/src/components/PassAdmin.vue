<template>

    <div class="container">
        <span>
            <h2 class="d-inline p-2 ">
                Select Your Pass
                <button  type="button" class="btn btn-outline-success d-inline p-2" data-bs-toggle="modal" data-bs-target="#addNewPass">
                    Add New Pass
                    <i class="bi bi-plus-square"></i>
                </button>
            </h2>
            
        </span>


            <div class="row row-cols-auto g-5 my-2">
                <!-- Passes 'Card' -->
                <template v-for="EachPass in PassAdminPasses" :key="EachPass.passId" >
                <div v-if="EachPass.defunct=='0'">

                    <div class="card border-secondary h-100" style="width: 25rem;">
                        <!-- Show Image here -->
                            
                            <template v-if="EachPass.passImage">
                                <img v-bind:src="`data:image/*;base64,${EachPass.passImage}`" class="mx-auto mt-2 d-block border border-2 border-secondary" height="200" width="375">
                            </template>
                            <template v-else>
                                <img src="../assets/PassAssets/noImageAvailable.jpg" class="mx-auto mt-2 d-block border border-2 border-secondary" height="200" width="375">
                            </template>
                            <div class="card-body ">
                                <b class="card-title">{{EachPass.passDesc}}
                                </b>
                                
                            </div>
                            <ul class="list-group list-group-flush">
                                <span><b>Point of Interests </b></span>
                                <div class="text-wrap w-90 justify-content-center">
                                    <span>{{EachPass.poi}}</span>
                                </div>
                            </ul>
                            <ul class="list-group list-group-flush">
                                <span><b>Point of Interests URL</b></span>
                                <div class="text-wrap w-90 justify-content-center">
                                    <span>{{EachPass.poiUrl}}</span>
                                </div>
                            </ul>
                            <ul class="list-group list-group-flush">
                                <div class="container">
                                    <div class="row">
                                        <div class="col-5 border"><b>ID</b></div>
                                        <div class="col-7 border">{{EachPass.passId}}</div>
                                        <div class="col-5 border"><b>Pass Number</b></div>
                                        <div class="col-7 border">{{EachPass.passNumber}}</div>
                                        <div class="col-5 border"><b>Guests Numbers</b></div>
                                        <div class="col-7 border">{{EachPass.numGuests}}</div>
                                        <div class="col-5 border"><b>Replacement Fee</b></div>
                                        <div class="col-7 border">${{EachPass.replacementFee}}</div>
                                        <div class="col-5 border"><b>Status</b></div>
                                        <div class="col-7 border">{{EachPass.passStatus}}</div>
                                        <div class="col-5 border"><b>Physical/EPass</b></div>
                                        <template v-if="EachPass.isPhysical==true">
                                            <div class="col-7 border">Physical Pass</div>
                                        </template>
                                        <template v-else>
                                            <div class="col-7 border">EPass</div>
                                        </template>
                                        
                                            <div class="col-12"><b>Attachment(PDF)</b></div>
                                                <template v-if="EachPass.passAttachment">
                                                    <div class="col-12"><button class="btn btn-success mb-1" @click="downloadData(EachPass.passId)">{{EachPass.passAttachmentName}}</button></div>
                                                </template>
                                                <template v-else>
                                                    <div class="col-12"><button class="btn btn-success mb-1" disabled>No File Attached</button></div>
                                                </template>
                                    </div>
                                </div>
                            </ul>
                            
                            <div class="card-footer btn-group-vertical">
                                <!-- View button prompt Passes update modal -->
                                <button type="button" class="btn btn-primary" data-bs-toggle="modal" :data-bs-target="`#viewUM`+EachPass.passId">
                                    View
                                </button> 
                                
                            </div>
                    </div>
                    <!-- Pass update modal -->
                    <div class="modal fade" data-bs-backdrop="static"  :id="`viewUM`+EachPass.passId" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">

                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Update pass information</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="initialStateEachPass(EachPass.passId)"></button>
                                </div>
                                <div class="modal-body text-start">
                                    <form>
                                        <div class="mb-3">
                                            <div class="row mb-3 d-flex justify-content-center">
                                                <div>
                                                    <template v-if="localImageSrc">
                                                        <img :src="localImageSrc" class="mx-auto d-block border border-2 border-secondary" height="200" width="300">
                                                    </template>
                                                    <template v-else-if="EachPass.passImage">
                                                        <img v-bind:src="`data:image/*;base64,${EachPass.passImage}`" class="mx-auto d-block border border-2 border-secondary" height="200" width="300">
                                                    </template>
                                                    <template v-else>
                                                        <img src="../assets/PassAssets/noImageAvailable.jpg" class="mx-auto d-block border border-2 border-secondary" height="200" width="300">
                                                    </template>
                                                    
                                                </div>
                                                <div class="input-group mb w-75 ">
                                                    <label class="col-12 col-form-label text-center"><b>Insert card image here(if any)</b></label>
                                                    <input class="col-8 col-md-5 form-control form-control" type="file" accept="image/*" @change="onImageSelected" :id="`updateImageUploadInput`+EachPass.passId">
                                                    <button class="col-4 col-md-2 btn btn-outline-danger" type="button" @click="removeImageSelect_newAndUpdatePass(EachPass.passId)">X</button>
                                                </div>
                                            </div>
                                            <hr>
                                            <label class="col-form-label"><b>ID: {{EachPass.passId}}</b></label><br>
                                            <label class="col-form-label"><b>Pass Number:</b></label> 
                                            <input class="form-control" v-model ="EachPass.passNumber" required >
                                            <label class="col-form-label"><b>Description:</b></label> 
                                            <input class="form-control" v-model ="EachPass.passDesc" required >
                                            <label class="col-form-label"><b>Point of Interests:</b></label>
                                            <input class="form-control" v-model ="EachPass.poi" required >
                                            <label class="col-form-label"><b>Point of Interests URL:</b></label>
                                            <input class="form-control" v-model ="EachPass.poiUrl" required >
                                            
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Guests Numbers</b></label>
                                            <div class="col-7">
                                                <input type="number" v-model="EachPass.numGuests" class="form-control" min="1">
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Replacement Fee</b></label>
                                            <div class="col-7">
                                                <div class="input-group">
                                                    <div class="input-group-text">$</div>
                                                    <input type="number" v-model="EachPass.replacementFee" class="form-control" min="0">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Status</b></label>
                                            <div class="col-7">
                                                <select class="form-select" v-model="EachPass.passStatus">
                                                    <option value="AVAILABLE">Available</option>
                                                    <option value="ONLOAN">On Loan</option>
                                                    <option value="UNRETURNED">Unreturned</option>

                                                </select>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Physical/EPass</b></label>
                                            <div class="col-7">
                                                <select class="form-select" v-model="EachPass.isPhysical">
                                                    <option value="true">Physical Pass</option>
                                                    <option value="false">EPass</option>
                                                </select>
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="mb-3">
                                            <h3>Atttachment information</h3>
                                            <div class="row mb-3">


                                                <div class="input-group mb-3">
                                                    <label class="col-5 col-form-label"><b>Current Attachment(PDF):</b></label>
                                                    <input type="text" class="col-5 form-control" :placeholder="EachPass.passAttachmentName" ref="updatePassAttachmentText" disabled>
                                                    <button class="col-2 btn btn-outline-danger" type="button" @click="removeFileFromDB(EachPass.passId)">X</button>
                                                </div>
                                                
                                                <div class="input-group">
                                                    <label class="col-5 col-form-label"><b>New Attachment(PDF)</b></label>
                                                    <input class="col-7 form-control"  type="file" accept=".pdf" @change="onFileSelected">
                                                </div>
                                                
                                                    
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" @click="updatePassMethod(EachPass.passId)" data-bs-dismiss="modal">Update Now</button>
                                    <button type="button" class="btn btn-danger" @click="deletePassMethod(EachPass.passId)" data-bs-dismiss="modal">Delete</button> 
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="initialStateEachPass(EachPass.passId)">Cancel</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                </template>

                <!-- ====================================Loop ends============================= -->

                <!-- Add New Pass Form Modal-->
                <div class="modal fade" data-bs-backdrop="static"  id="addNewPass" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Create new pass</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="initialState"></button>
                            </div>

                            <div class="modal-body text-start">
                                <form>
                                        <div class="mb-3">
                                            <div class="row mb-3 d-flex justify-content-center">
                                                <div v-if="localImageSrc">
                                                    <img :src="localImageSrc" class="mx-auto d-block img-thumbnail" height="200" width="300">
                                                </div>
                                                <div class="input-group mb w-75 ">
                                                    <label class="col-12 col-form-label text-center"><b>Insert card image here(if any)</b></label>
                                                    <input class="col-8 col-md-5 form-control form-control"  type="file" accept="image/*" @change="onImageSelected" id="newImageUploadInput">
                                                    <button class="col-4 col-md-2 btn btn-outline-danger" type="button" @click="removeImageSelect_newAndUpdatePass()">X</button>
                                                </div>
                                            </div>
                                            <hr>
                                            <label class="col-form-label"><b>Pass Number:</b></label> 
                                            <input type="number"  class="form-control" required v-model="NEWpassNumber">
                                            <label class="col-form-label"><b>Description:</b></label> 
                                            <input class="form-control" required v-model="NEWpassDesc">
                                            <label class="col-form-label"><b>Point of Interests:</b></label>
                                            <input class="form-control" required v-model="NEWpoi">
                                            <label class="col-form-label"><b>Point of Interests Website:</b></label>
                                            <input class="form-control" required v-model="NEWpoiUrl">
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Guests Numbers</b></label>
                                            <div class="col-7">
                                                <input type="number" class="form-control" required v-model="NEWnumGuests" min="1">
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Replacement Fee</b></label>
                                            <div class="col-7">
                                                <div class="input-group">
                                                    <div class="input-group-text">$</div>
                                                    <input type="number" class="form-control" required v-model="NEWreplacementFee" min="0">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Status</b></label>
                                            <div class="col-7">
                                                <select class="form-select" v-model="NEWpassStatus">
                                                    <option value="AVAILABLE">Available</option>
                                                    <option value="ONLOAN">On Loan</option>
                                                    <option value="UNRETURNED">Unreturned</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Physical?</b></label>
                                            <div class="col-7">
                                                <select class="form-select" v-model="NEWisPhysical">
                                                    <option value="true">true</option>
                                                    <option value="false">false</option>
                                                </select>
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="mb-3">
                                            <h3>Atttachment information</h3>
                                            
                                            
                                            <div class="row mb-3">
                                                <div class="input-group mb-3">
                                                    <label class="col-12 col-md-5 col-form-label"><b>New Attachment(PDF)</b></label>
                                                    <input class="col-8 col-md-5 form-control form-control"  type="file" accept=".pdf" @change="onFileSelected" ref="newFileUpload">
                                                    <button class="col-4 col-md-2 btn btn-outline-danger" type="button" @click="removeFileSelect_addNewPass()">X</button>
                                                </div>
                                            </div>
                                        </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" @click="onUpload()" data-bs-dismiss="modal">Create Pass</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="initialState">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>




        
        
    </div>
        
</template>

<script>
import axios from 'axios'


export default({

    name: 'PassAdmin',
        props: {
            PassAdminPasses: Array,
        },
        
        components: {
        },
        data() {
        return {
                passURL: "http://localhost:8081/passes",
                passIDtoDelete: null,
                selectedFile: null,
                selectedFileName: null,
                NEWpassDesc: null,
                NEWpoi: null,
                NEWpassNumber: null,
                NEWpoiUrl: null,
                NEWnumGuests: 1,
                NEWreplacementFee: 0,
                NEWpassStatus: "AVAILABLE",
                NEWisPhysical: true,
                selectedImage: null,
                localImageSrc: null
            
            }
        },
        mounted(){

        },
        methods: {
            onFileSelected(event){
                this.selectedFile = event.target.files[0]
                this.selectedFileName = this.selectedFile.name
                if(this.selectedFile.length === 0){
                    this.selectedFile = null
                    return
                }
                console.log(this.selectedFile)
            },
            onImageSelected(event){
                this.selectedImage = event.target.files[0]
                this.localImageSrc = URL.createObjectURL(this.selectedImage) // for local preview image 
                console.log(this.selectedImage)
                console.log(this.localImageSrc)
                
            },
            onUpload(){ //This function POST new data information into DB
                const bearer_token = `Bearer ${localStorage.getItem("token")}`;
                const config = {
                    headers: {
                    Authorization: bearer_token,
                    },
                };
                const fd = new FormData()
                fd.append("passDesc", this.NEWpassDesc)
                fd.append("poi", this.NEWpoi)
                fd.append("poiUrl", this.NEWpoiUrl)
                fd.append("passNumber", this.NEWpassNumber)
                fd.append("numGuests", this.NEWnumGuests)
                fd.append("replacementFee", this.NEWreplacementFee)
                fd.append("isPhysical", this.NEWisPhysical)
                fd.append("passStatus", this.NEWpassStatus)
                if(this.selectedFile!==null){
                    fd.append("passAttachmentName", this.selectedFile.name)
                    fd.append("passAttachment", this.selectedFile)
                }
                if(this.selectedImage!==null){
                    fd.append("passImage", this.selectedImage)
                }
                axios.post("http://localhost:8081/passes", fd, config)
                .then(res=>{
                    console.log(res)
                    this.initialState()
                    this.$emit('getPassData')
                })
            },
            async updatePassMethod(passID){
                console.log(this.PassAdminPasses[passID-1].passDesc)
                console.log(this.PassAdminPasses[passID-1].poiUrl)
                const bearer_token = `Bearer ${localStorage.getItem("token")}`;
                const config = {
                    headers: {
                    Authorization: bearer_token,
                    },
                };
                const UPDATEfd = new FormData()
                UPDATEfd.append("passDesc", this.PassAdminPasses[passID-1].passDesc)
                UPDATEfd.append("poi", this.PassAdminPasses[passID-1].poi)
                UPDATEfd.append("poiUrl", this.PassAdminPasses[passID-1].poiUrl)
                UPDATEfd.append("passNumber", this.PassAdminPasses[passID-1].passNumber)
                UPDATEfd.append("numGuests", this.PassAdminPasses[passID-1].numGuests)
                UPDATEfd.append("replacementFee", this.PassAdminPasses[passID-1].replacementFee)
                UPDATEfd.append("isPhysical", this.PassAdminPasses[passID-1].isPhysical)
                UPDATEfd.append("passStatus", this.PassAdminPasses[passID-1].passStatus)
                if(this.selectedFile !== null){
                    UPDATEfd.append("passAttachmentName", this.selectedFile.name)
                    UPDATEfd.append("passAttachment", this.selectedFile)
                }
                if(this.selectedImage!==null){
                    UPDATEfd.append("passImage", this.selectedImage)
                }
                
                console.log(UPDATEfd)
                try{
                    await axios.put(this.passURL+"/"+passID, UPDATEfd, config)
                    .then(response => {
                        this.selectedFile = null
                        this.$emit('getPassData')
                        console.log(response);
                    });
                } catch(err){
                    console.error(err);
                }
            },
            downloadData(passID){
                const data = this.PassAdminPasses[passID-1].passAttachment
                console.log(data)
                const linkSource = `data:application/pdf;base64,${data}`;
                const downloadLink = document.createElement("a");
                const fileName = this.PassAdminPasses[passID-1].passAttachmentName;
                downloadLink.href = linkSource;
                downloadLink.download = fileName;
                downloadLink.click();
            },
            async deleteExistingPass(passID){
                const bearer_token = `Bearer ${localStorage.getItem("token")}`;
                const config = {
                    headers: {
                    Authorization: bearer_token,
                    },
                };
                try{
                    await axios.delete(this.passURL+"/"+passID, config)
                    .then(response => {
                        this.$emit('getPassData')
                        console.log(response);
                    });
                } catch(err){
                    console.error(err);
                }
            },
            async removeFileFromDB(passID){
                const bearer_token = `Bearer ${localStorage.getItem("token")}`;
                const config = {
                    headers: {
                    Authorization: bearer_token,
                    },
                };
                try{
                    await axios.put(this.passURL+"/deletePassAttachment/"+passID, config)
                    .then(response => {
                        this.$refs.updatePassAttachmentText.value = null;
                        this.$emit('getPassData')
                        console.log(response);
                    });
                } catch(err){
                    console.error(err);
                }
            },
            deletePassMethod(passID){
                this.deleteExistingPass(passID)
            },
            closeButtonMethod(){
                this.initialState()
            },
            removeFileSelect_addNewPass(){
                this.selectedFile= null
                this.selectedFileName= null
                this.$refs.newFileUpload.value = null;
            },
            removeImageSelect_newAndUpdatePass(passId){
                var temp = "updateImageUploadInput"+passId
                if(document.getElementById("newImageUploadInput").value !== null){
                    document.getElementById("newImageUploadInput").value = null
                }
                if(document.getElementById(temp).value !== null){
                    document.getElementById(temp).value = null
                }
                this.selectedImage= null
                this.localImageSrc = null;
            },
            initialStateEachPass(PassId){
                this.passIDtoDelete= null,
                
                this.selectedFile= null,
                this.selectedFileName= null,

                this.NEWpassDesc= null,
                this.NEWpoi= null,
                this.NEWpoiUrl= null,
                this.NEWpassNumber
                this.NEWnumGuests= 1,
                this.NEWreplacementFee= 0,
                this.NEWpassStatus= "AVAILABLE",
                this.NEWisPhysical= true

                this.selectedImage= null
                this.localImageSrc = null;
                document.getElementById("newImageUploadInput").value = null;
                this.removeImageSelect_newAndUpdatePass(PassId);
            },
            initialState(){
                this.passIDtoDelete= null,
                
                this.selectedFile= null,
                this.selectedFileName= null,

                this.NEWpassDesc= null,
                this.NEWpoi= null,
                this.NEWpoiUrl= null,
                this.NEWpassNumber
                this.NEWnumGuests= 1,
                this.NEWreplacementFee= 0,
                this.NEWpassStatus= "AVAILABLE",
                this.NEWisPhysical= true

                this.selectedImage= null
                this.localImageSrc = null;
                document.getElementById("newImageUploadInput").value = null;
                
            }
        },

})

</script>
<style scoped>
.header {
    text-align: center;
    padding: 20px;
    font-size:30px;
}

</style>
