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
                            <input  type="text" readonly class="form-control-plaintext" id="employeeID" placeholder="1">
                        </div>
                    </div>
                    <div class="mb-3 has-validation">
                        <label for="name" class="col-form-label">Name:</label>
                        <input type="text" class="form-control is-invalid" id="name" required>
                        <div class="invalid-feedback">
                            Please enter a valid name!
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="col-form-label">Email:</label>
                        <input type="text" class="form-control" id="email" required>
                        <div class="invalid-feedback">
                            Please enter a valid email!
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="phoneNumber" class="col-form-label">Phone Number:</label>
                        <input type="text" class="form-control" id="phoneNumber" required>
                        <div class="invalid-feedback">
                            Please enter a valid phone number!
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="department" class="col-form-label">Department:</label>
                        <select class="form-select" aria-label="Default select example">
                            <option selected>Open this select menu</option>
                            <option value="1">Computing</option>
                            <option value="2">Economics</option>
                            <option value="3">Business</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="role" class="col-form-label">Role:</label>
                        <select class="form-select" aria-label="Default select example">
                            <option selected>Open this select menu</option>
                            <option value="1">HR</option>
                            <option value="2">Staff</option>
                            <option value="3">Manager</option>
                        </select>                        
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button v-if="modalType == 'create'" type="button" class="btn btn-primary" @click="createEmployee" data-bs-dismiss="modal">Create</button>
                <button v-else type="button" class="btn btn-primary" @click="editEmployeeDetails" data-bs-dismiss="modal">Save</button>
            </div>
            </div>
        </div>
    </div>
</template>
<script>
    // import EmployeeService from "@/api/services/EmployeeService";   
    import { Toast } from "bootstrap";

    export default {
        name: "EmployeeModal",
        props: {
            modalType: String,
            employeeDetails: Object
        },
        data(){
            return{

            };
        }, 
        methods: {
            async createEmployee(){
                // const employee = await EmployeeService.createEmployee();
                // console.log(employee);
                var bsAlert = new Toast(document.getElementById('theToastr'));//inizialize it
                this.$emit("toastrMsg", {status: "Success", msg: "New employee has been created!"});
                // this.$emit("toastrMsg", {status: "Error", msg: "Opps, something went wrong!"});
                bsAlert.show();
            },
            async editEmployeeDetails(){
                // const employees = await EmployeeService.editEmployeeDetails("1");
                // console.log(employees);
                var bsAlert = new Toast(document.getElementById('theToastr'));//inizialize it
                // this.$emit("toastrMsg", {status: "Success", msg: "Your changes have been saved!"});
                this.$emit("toastrMsg", {status: "Error", msg: "Opps, something went wrong!"});
                bsAlert.show();
            }
        }
    }
</script>