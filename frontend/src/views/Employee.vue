<template>
    <div id="employee" class="px-3">
        <h1>Employee</h1>
        ------------------------------------
        <p>Admin functionalities:</p>
        ------------------------------------
        <p>1. Table that displays all employee details with edit/delete functionalities</p>
        <p>2. Search function tied to the table</p>
        <p>3. Upload of CSV file</p>
        <div id="tableBox">
            <div id="buttonsHolder" class="d-flex">

                <div>
                    <input id="fileId" type="file" hidden/>
                    <input id="buttonId" type="button" class="importBtn funcBtn btn btn-info" value='Import' @click="openDialog"/>
                </div>
                <div class="px-3"><button type="button" class="newBtn funcBtn btn btn-warning" data-bs-toggle="modal" data-bs-target="#createModal">New</button></div>
                <div><button type="button" class="delBtn funcBtn btn btn-danger" @click="deleteRecords()">Delete</button></div>
            </div>
            <div id="wrapper">
            </div>
        </div>
        <EmployeeModal id="createModal" modalType="create" @createEmployee="createEmployee"></EmployeeModal>
        <EmployeeModal id="editModal" modalType="edit" @editEmployee="editEmployee" :selectedEmployeeToEdit="selectedEmployeeToEdit"></EmployeeModal>
        <TheToastr :toastrResponse="toastrResponse"></TheToastr>
    </div>
</template>
<script>
    import EmployeeService from "@/api/services/EmployeeService";   
    import { Grid, html } from "gridjs";
    import { RowSelection } from "gridjs/plugins/selection";
    import TheToastr from "@/components/TheToastr.vue";
    import { Toast } from "bootstrap";
    import EmployeeModal from "../components/EmployeeModal.vue"
    import * as XLSX from 'xlsx/xlsx.mjs';

    export default {
        name: "GridComponent",
        components: {
            TheToastr,
            EmployeeModal,
        },
        data() {
            return {
                form: { file: null },
                toastrResponse: "",
                recordsToDelete: [],
                gridJsTableData: [],
                selectedEmployeeToEdit: {},
                grid: new Grid({
                    resizable: true,
                    columns: [],
                    data: [],
                    search: true,
                    sort: true,
                    pagination: {
                        limit: 5
                    },
                    language: {
                        'search': {
                            'placeholder': '🔍 Search...'
                        },
                        'pagination': {
                            'showing': '😃 Displaying',
                            'results': () => 'Records'
                        }
                    },
                    loading: 'Loading...',
                    noRecordsFound: 'No matching records found',
                    error: 'An error happened while fetching the data',
                    style: {
                        th: {
                            'background-color': 'rgba(0, 0, 0, 0.1)',
                            'text-align': 'center'
                        },
                        td: {
                            'text-align': 'center',
                        }
                    }
                })
            }
        },
        async mounted() {
            await this.getAllEmployees();

            this.grid.render(document.getElementById("wrapper"));
            this.grid.on("ready", () => {
                // find the plugin with the give plugin ID
                const checkboxPlugin = this.grid.config.plugin.get("employeeCheckBox");
                
                // subscribe to the store events
                checkboxPlugin.props.store.on("updated", (state) => {
                    // console.log("checkbox updated", state, prevState);
                    this.recordsToDelete = state["rowIds"];
                });
            })
            this.grid.on('rowClick', (...args) => {
                let allArgs = args[1]["_cells"];
                this.selectedEmployeeToEdit = { "id": allArgs[1].data, "name": allArgs[2].data, "email": allArgs[3].data, "contactNumber": allArgs[4].data, "role": allArgs[5].data }
            });

            this.refreshTable();
        },
        methods: {
            showToast(){
                var bsAlert = new Toast(document.getElementById('theToastr'));         
                bsAlert.show();
            },
            async getAllEmployees(){
                var employees = await EmployeeService.getAllEmployees();
                this.gridJsTableData = [];
                for (let e of employees){
                    let gridJsEmployeeObject = { "id": e.userId, "name": e.username, "email": e.email, "contactNumber": e.contactNumber, "role": e.userType };
                    if (!e.defunct) { this.gridJsTableData.push(gridJsEmployeeObject); }
                }  
            },
            async createEmployee(details){
                var row = Object.keys(details).map((key) => details[key]);
                let doesNameExist = this.gridJsTableData.some((e) => e.name === details.username);
                if (!doesNameExist && details.name != ""){ //check if name exist in current table, if name is empty, or is first row a header row
                    this.pushRow(row);
                    this.toastrResponse = {status: "Success", msg: "New employee has been created!"};    
                } else {
                    this.toastrResponse = {status: "Error", msg: "Employee name already exist!"};            
                }
                this.showToast();
            },
            async editEmployee(details){
                details.password = "test";
                details.id = this.selectedEmployeeToEdit.id;

                try{
                    await EmployeeService.editEmployeeDetails(details);
                    await this.getAllEmployees();
                    this.refreshTable();
                    this.toastrResponse = {status: "Success", msg: "Your changes have been saved!"};
                } catch (e){
                    this.toastrResponse = {status: "Error", msg: "Opps, something went wrong!"};
                } finally {
                    this.showToast();
                }
            },
            async deleteRecords(){
                if (this.recordsToDelete.length > 0){
                    for (let value of this.recordsToDelete){ await EmployeeService.removeEmployees(value + 1); }
                    await this.getAllEmployees();
                    this.refreshTable();
                    this.toastrResponse = {status: "Success", msg: "Records have been successfully deleted!"};

                } else {
                    this.toastrResponse = {status: "Error", msg: "Please select at least 1 record to delete!"};           
                }
                this.showToast();
            },
            openDialog() {
                let file = document.getElementById("fileId");
                file.click();
                file.onchange = () => {
                    let selected = file.files[0];
                    let reader = new FileReader();
                    let fileType = selected.name.split('.')[1];
                    this.toastrResponse = {status: "Success", msg: "Records from ." + fileType + " file have been imported successfully!"};
                    if (fileType == "csv" || fileType == "txt"){
                        this.csvImport(selected, reader);   

                    } else if (fileType == "xlsx") {
                        this.xlsxImport(selected, reader);

                    } else {
                        this.toastrResponse = {status: "Error", msg: "Invalid file extension, please only use .csv, .txt, or .xlsx!"};  
                    }

                    file.value = ''; //clear input field so that we can upload the same file again
                    this.showToast();
                }
            },
            csvImport(selected, reader){
                reader.onloadend = async () => { 
                    let data = reader.result.split("\r\n");
                    for (let i in data) {
                        data[i] = data[i].split(",");
                        let name = data[i][0];
                        let doesNameExist = this.gridJsTableData.some((row) => row.name === name);
                        if (!doesNameExist && name != "" && name != "Name"){ //check if name exist in current table, if name is empty, or is first row a header row
                            await this.pushRow(data[i]);
                        }
                    }
                }
                reader.readAsText(selected);
            },
            xlsxImport(selected, reader){
                reader.onloadend = async (e) => {
                    var data = e.target.result;
                    var workbook = XLSX.read(data, { type: 'binary' });
                    for (let sheetName of workbook.SheetNames) {
                        var XL_row_object = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
                        for (let row of XL_row_object){
                            let doesNameExist = this.gridJsTableData.some(z => row?.Name == undefined ? z.name === Object.values(row)[1] : z.name === row.Name);
                            if (!doesNameExist && row.Name != ""){
                                var record = Object.keys(row).map((key) => String(row[key])); //convert object to array form
                                //If .xlsx file has no header, switch the position of affected elements in the array. From [Contact number, Name, Email] to [Name, Email, Contact Number]
                                [record[0], record[1], record[2]] = row.Name == undefined ? [record[1], record[2], record[0]] : [record[0], record[1], record[2]];
                                await this.pushRow(record);
                            }
                        }
                    }
                };

                reader.readAsBinaryString(selected);
            },
            async pushRow(row){
                if (row.length == 4 && !row.includes("")){
                    let record = { "username": row[0], "email": row[1], "contactNumber": row[2], "userType": row[3], "password": "test" };
                    await EmployeeService.createEmployee(record);
                    await this.getAllEmployees();
                    this.refreshTable();
                } else {
                    this.toastrResponse = {status: "Error", msg: "Your data is invalid, please check your data set for missing fields or cells!"}; 
                    throw new Error("Invalid file");
                }
            },
            refreshTable(){
                this.grid.updateConfig({
                    data: this.gridJsTableData,
                    columns: [
                    {
                        id: 'employeeCheckBox',
                        plugin: {
                            component: RowSelection,
                            props: {
                                // use the "id" column as the row identifier
                                id: (row) => row.cell(1).data - 1
                            }
                        },
                        width: '5%',
                    },
                    { name: 'ID', width: '10%' },
                    { name: 'Name', width: '20%' },
                    { name: 'Email',  width: '25%' },
                    { name: 'Contact Number',  width: '20%' },
                    'Role', 
                    { 
                        name: '#',
                        formatter: () => html(`<i class="fa-lg button bi bi-pencil text-primary" data-bs-toggle="modal" data-bs-target="#editModal"></i>`),
                        width: '1%'
                    }
                ],
                }).forceRender();
            },
        }
        
    }
</script>
<style>
    #tableBox{
        padding: 0 7.5%;
    }

    #buttonsHolder{
        position: relative;
    }

    .funcBtn{
        position: relative;
    }


    .gridjs-tr td:last-child{
        padding: 0;
    }

    .bi-pencil{
        cursor: pointer;
    }

    .bi-pencil:hover{
        transition: all 300ms ease;
        -o-transition: all 300ms ease;
        -ms-transition: all 300ms ease;
        -moz-transition: all 300ms ease;
        -webkit-transition: all 300ms ease;
        font-size:22px;
    }

    @media only screen and (min-width: 992px){
        #buttonsHolder{
            display: flex;
            justify-content: flex-end;
        }

        .funcBtn{
            position: absolute;
            top: 10px;
            z-index: 999;
        }
        
        .importBtn{
            right: 160px;
        }
        
        .newBtn{
            right: 90px;
        }

        .delBtn{
            right: 5px;
        }
    }
</style>