<template>
    <div class="modal fade" :id="modalType" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel" v-html="modalType == 'create' ? 'New Employee' : 'Edit Employee Details'"></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body text-start">
                <form>
                    <div class="col-md">
                        <div class="mb-3" v-if="modalType == 'edit'">
                            <label for="employeeID" class="col-form-label">ID:</label>
                            <input type="text" readonly class="form-control-plaintext" id="employeeID" :placeholder="selectedEmployeeToEdit.id">
                        </div>
                    </div>
                    <div class="mb-3 has-validation">
                        <label for="name" class="col-form-label">Name:</label>
                        <input v-model="eName" type="text" class="form-control name" id="name" required>
                        <div class="invalid-feedback">
                            Please enter a valid name!
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="col-form-label">Email:</label>
                        <input v-model="email" type="text" class="form-control email" id="email" required>
                        <div class="invalid-feedback">
                            Please enter a valid email!
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="phoneNumber" class="col-form-label">Phone Number:</label>
                        <input v-model="phoneNumber" type="text" class="form-control phoneNumber" id="phoneNumber" required>
                        <div class="invalid-feedback">
                            Please enter a valid phone number!
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="role" class="col-form-label">Role:</label>
                        <select v-model="role" class="form-select" aria-label="Default select example">
                            <option value="" selected disabled>Open this select menu</option>
                            <option value="GOP">GOP</option>
                            <option value="ADMIN">ADMIN</option>
                            <option value="BORROWER">BORROWER</option>
                        </select>                           
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button v-if="modalType == 'create'" type="button" class="btn btn-primary" @click="sendEmployeeDetails" :disabled="!eName || !email || !phoneNumber || !role">Create</button>
                <button v-else type="button" class="btn btn-primary" @click="sendEmployeeDetails" :disabled="!eName || !email || !phoneNumber || !role">Save</button>
            </div>
            </div>
        </div>
    </div>
</template>
<script>
    // import EmployeeService from "@/api/services/EmployeeService";   
    import { Modal } from "bootstrap";

    export default {
        name: "EmployeeModal",
        props: {
            modalType: String,
            selectedEmployeeToEdit: Object,
        },
        data(){
            return{
                employeeDetails: Object,
                eName: "",
                email: "",
                phoneNumber: "",
                role: "",
            };
        }, 
        watch: {
            selectedEmployeeToEdit(data) {
                console.log(data);
                this.eName = data.eName;
                this.email = data.email;
                this.phoneNumber = data.phoneNumber;
                this.role = data.role;                
            }
        },
        methods: {
            validateFields(name, email, phoneNumber){
                var isValidName = (name.length < 2) ? this.isInvalid("name") : this.isValid("name");
                const validRegex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                var isValidEmail = (!email.match(validRegex)) ? this.isInvalid("email") : this.isValid("email");
                var isValidPhoneNumber = (phoneNumber.length != 8 || (phoneNumber.split("")[0] != "9" && phoneNumber.split("")[0] != "8")) ? this.isInvalid("phoneNumber") : this.isValid("phoneNumber");

                return isValidName && isValidEmail && isValidPhoneNumber;
            },
            isInvalid(ele){
                let index = this.modalType == "create" ? 0 : 1;
                document.getElementsByClassName(ele)[index].classList.add("is-invalid");
                return false;
            },
            isValid(ele){
                let index = this.modalType == "create" ? 0 : 1;
                document.getElementsByClassName(ele)[index].classList.remove("is-invalid");
                return true;
            },
            sendEmployeeDetails(){
                let isValid = this.validateFields(this.eName, this.email, this.phoneNumber);
                if (isValid){
                    this.employeeDetails = { "username": this.eName, "email": this.email, "contactNumber": this.phoneNumber,"userType": this.role }
                    this.$emit(this.modalType + "Employee", this.employeeDetails);
                    const modal = Modal.getInstance(document.getElementById(this.modalType + "Modal"));    
                    modal.hide();             
                }   
            }
        },
    }
</script>