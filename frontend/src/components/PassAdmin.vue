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
                        <img src="../assets/passPic_test.jpg" class="card-img-top">
                            <div class="card-body ">
                                <b class="card-title">{{EachPass.passDesc}}</b>
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
                                        <div class="col-5 border"><b>Guests Numbers</b></div>
                                        <div class="col-7 border">{{EachPass.numGuests}}</div>
                                        <div class="col-5 border"><b>Replacement Fee</b></div>
                                        <div class="col-7 border">${{EachPass.replacementFee}}</div>
                                        <div class="col-5 border"><b>Status</b></div>
                                        <div class="col-7 border">{{EachPass.passStatus}}</div>
                                        <!-- <div class="col-5 border"><b>Defunct?</b></div>
                                        <div class="col-7 border">{{EachPass.defunct}}</div> -->
                                        <div class="col-5 border"><b>Physical?</b></div>
                                        <div class="col-7 border">{{EachPass.physical}}</div>
                                        <div class="col-12"><b>Attachment(PDF)</b></div>
                                        <div class="col-12"><button class="btn btn-success mb-1" @click="downloadData(EachPass.passId)">{{EachPass.passAttachmentName}}</button></div>

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
                    <!-- <div class="modal fade" id="viewUM" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true"> -->
                    <div class="modal fade" :id="`viewUM`+EachPass.passId" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">

                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Update pass information</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="closeButtonMethod"></button>
                                </div>
                                <div class="modal-body text-start">
                                    <form>
                                        <div class="mb-3">
                                            <label class="col-form-label"><b>ID: {{EachPass.passId}}</b></label><br>

                                            <label class="col-form-label">Description:</label> 
                                            <input class="form-control" v-model ="EachPass.passDesc" required >
                                            <label class="col-form-label">Point of Interests:</label>
                                            <input class="form-control" v-model ="EachPass.poi" required >
                                            <label class="col-form-label">Point of Interests URL:</label>
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
                                                </select>
                                            </div>
                                        </div>
                                        <!-- <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Deactivated?(Defunct)</b></label>
                                            <div class="col-7">
                                                <input type="text" v-model="EachPass.defunct" class="form-control" disabled>
                                            </div>
                                        </div> -->
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Physical?</b></label>
                                            <div class="col-7">
                                                <select class="form-select" v-model="EachPass.physical">
                                                    <option value="true">true</option>
                                                    <option value="false">false</option>
                                                </select>
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="mb-3">
                                            <h3>Atttachment information</h3>
                                            <div class="row mb-3">
                                                <label class="col-5 col-form-label"><b>Current Attachment(PDF):</b></label>
                                                <div class="col-7">
                                                    <input type="text" class="form-control" :placeholder="EachPass.passAttachmentName" disabled>
                                                </div>
                                                <label class="col-5 col-form-label"><b>New Attachment Name(PDF):</b></label>
                                                <div class="col-7 mt-1">
                                                    {{selectedFileName}}
                                                </div>
                                                <label class="col-5 col-form-label"><b>New Attachment(PDF):</b></label>
                                                <div class="col-7">
                                                    <input class="mt-1" type="file" @change="onFileSelected">
                                                    <div v-if="warningMessage==true" class="alert alert-danger alert-dismissible fade show mt-1" role="alert">
                                                        <strong class="text-justify">Please select a PDF file before proceeding!</strong>
                                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" @click="closeButtonMethod"></button>
                                                    </div>
                                                </div>
                                                
                                                    

                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" @click="updatePassMethod(EachPass.passId)" :disabled="warningMessage==true">Update Now</button>
                                    <button type="button" class="btn btn-danger" @click="deletePassMethod(EachPass.passId)" data-bs-dismiss="modal">Delete</button> 
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="closeButtonMethod">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                </template>







                <!-- ====================================Loop ends============================= -->

                <!-- Add New Pass Form Modal-->
                <div class="modal fade" id="addNewPass" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Create new pass</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="closeButtonMethod"></button>
                            </div>

                            <div class="modal-body text-start">
                                <form>
                                    <div class="mb-3">
                                            <label class="col-form-label">Description:</label> 
                                            <input class="form-control" required v-model="NEWpassDesc">
                                            <label class="col-form-label">Point of Interests:</label>
                                            <input class="form-control" required v-model="NEWpoi">
                                            <label class="col-form-label">Point of Interests Website:</label>
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
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <label class="col-5 col-form-label"><b>Physical?</b></label>
                                            <div class="col-7">
                                                <select class="form-select" v-model="NEWphysical">
                                                    <option value="true">true</option>
                                                    <option value="false">false</option>
                                                </select>
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="mb-3">
                
                                            <h3>Atttachment information</h3>
                                            <label class="col-6 col-form-label"><b>New Attachment Name(PDF):</b></label>
                                            <div class="col-6 mt-1">
                                                {{selectedFileName}}
                                            </div>
                                            <label class="col-6 col-form-label"><b>New Attachment(PDF):</b></label>

                                            <input class="col-6" type="file" @change="onFileSelected">
                                            <div v-if="warningMessage==true" class="alert alert-danger alert-dismissible fade show mt-1" role="alert">
                                                <strong class="text-justify">Please select a PDF file before proceeding!</strong>
                                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" @click="closeButtonMethod"></button>
                                            </div>
                                        </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" @click="onUpload()" :disabled="warningMessage==true">Create Pass</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
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
                NEWpoiUrl: null,
                NEWnumGuests: 1,
                NEWreplacementFee: 0,
                NEWpassStatus: "AVAILABLE",
                NEWphysical: true,

                warningMessage: false,
            
            }
        },
        mounted(){

        },
        methods: {
            onFileSelected(event){
                this.selectedFile = event.target.files[0]
                this.selectedFileName = this.selectedFile.name
                // const allowedFileTypes = ["application/pdf"]
                if(this.selectedFile.length === 0){
                    this.warningMessage = true
                    this.selectedFile = null
                    return
                }
                if(this.selectedFile['type']!=='application/pdf'){
                    this.warningMessage = true
                    console.log("not ok")
                    return
                }
                this.warningMessage = false
                console.log(this.selectedFile)
            },
            onUpload(){
                const fd = new FormData()
                fd.append("passDesc", this.NEWpassDesc)
                fd.append("poi", this.NEWpoi)
                fd.append("poiUrl", this.NEWpoiUrl)
                fd.append("numGuests", this.NEWnumGuests)
                fd.append("replacementFee", this.NEWreplacementFee)
                fd.append("passStatus", this.NEWpassStatus)
                fd.append("passAttachmentName", this.selectedFile.name)
                fd.append("passAttachment", this.selectedFile)
                fd.append("physical", this.NEWphysical)
                console.log(fd)
                axios.post("http://localhost:8081/passes", fd)
                .then(res=>{
                    console.log(res)
                    this.selectedFile = null
                    this.$emit('getPassData')
                })
            },
            async updatePassMethod(passID){
                console.log(this.PassAdminPasses[passID-1].passDesc)
                console.log(this.PassAdminPasses[passID-1].poiUrl)
                
                const UPDATEfd = new FormData()
                if(this.selectedFile != null){
                    UPDATEfd.append("passDesc", this.PassAdminPasses[passID-1].passDesc)
                    UPDATEfd.append("poi", this.PassAdminPasses[passID-1].poi)
                    UPDATEfd.append("poiUrl", this.PassAdminPasses[passID-1].poiUrl)
                    UPDATEfd.append("numGuests", this.PassAdminPasses[passID-1].numGuests)
                    UPDATEfd.append("replacementFee", this.PassAdminPasses[passID-1].replacementFee)
                    UPDATEfd.append("passStatus", this.PassAdminPasses[passID-1].passStatus)
                    UPDATEfd.append("passAttachmentName", this.selectedFile.name)
                    UPDATEfd.append("passAttachment", this.selectedFile)
                    UPDATEfd.append("physical", this.PassAdminPasses[passID-1].physical)
                }else{
                    UPDATEfd.append("passDesc", this.PassAdminPasses[passID-1].passDesc)
                    UPDATEfd.append("poi", this.PassAdminPasses[passID-1].poi)
                    UPDATEfd.append("poiUrl", this.PassAdminPasses[passID-1].poiUrl)
                    UPDATEfd.append("numGuests", this.PassAdminPasses[passID-1].numGuests)
                    UPDATEfd.append("replacementFee", this.PassAdminPasses[passID-1].replacementFee)
                    UPDATEfd.append("passStatus", this.PassAdminPasses[passID-1].passStatus)
                    UPDATEfd.append("passAttachmentName", this.PassAdminPasses[passID-1].passAttachmentName)
                    UPDATEfd.append("passAttachment", this.PassAdminPasses[passID-1].passAttachment)
                    UPDATEfd.append("physical", this.PassAdminPasses[passID-1].physical)
                }
                console.log(UPDATEfd)
                try{
                    await axios.put(this.passURL+"/"+passID, UPDATEfd)
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
                const fileName = "abc.pdf";
                downloadLink.href = linkSource;
                downloadLink.download = fileName;
                downloadLink.click();
            },
            GetImageUrl(pic){
                return require('../assets/'+pic)
            },
            async deleteExistingPass(passID){
                try{
                    await axios.delete(this.passURL+"/"+passID)
                    .then(response => {
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
                this.selectedFile = null,
                this.selectedFileName = null
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
