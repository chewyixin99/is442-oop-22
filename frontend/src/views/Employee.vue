<template>
    <div id="employee" class="position-relative">
        <div v-if="loading" class="overlay">
            <div class="spinner-border-custom spinner-border" role="status">
                <span class="visually-hidden">Loading...</span>
            </div>
        </div>

        <div class="container-fluid">
            <div class="flex-column d-flex align-items-center justify-content-center">
                <h1 class="pt-4 mb-4">Employee</h1>
                <div class="container">
                    <div id="tableBox">
                        <hr/>
                        <div id="buttonsHolder" class="d-flex">
                            <button type="button" class="btn btn-info funcBtn dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false">
                                Actions &nbsp;
                                <span class="visually-hidden">Toggle Dropdown</span>
                            </button>
                            <ul id="ddm" class="dropdown-menu">
                                <li data-bs-toggle="modal" data-bs-target="#createModal">
                                    <a class="dropdown-item links" href="#">New
                                        <i class="bi bi-plus-square icons"></i>
                                    </a>
                                </li>
                                <li @click="deleteRecords()">
                                    <a class="dropdown-item links" href="#">Delete
                                        <i class="bi bi-trash icons"></i>
                                    </a>
                                </li>
                                <input id="fileId" type="file" accept=".xlsx,.csv" hidden/>
                                <li @click="openDialog">
                                    <a class="dropdown-item links" href="#">Import
                                        <i class="bi bi-file-earmark-plus icons"></i>
                                    </a>
                                </li>
                                <li @click="exportRecords()">
                                    <a class="dropdown-item links" href="#">Export
                                        <i class="bi bi-cloud-download icons"></i>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div id="wrapper"></div>
                    </div>
                </div>
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
                loading: false,
                form: { file: null },
                toastrResponse: "",
                recordsToDelete: [],
                gridJsTableData: [],
                selectedEmployeeToEdit: {},
                fullListOfEmployees: [],
                grid: new Grid({
                    resizable: true,
                    columns: [],
                    data: [],
                    search: true,
                    sort: true,
                    pagination: {
                        limit: 10
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
                            'background-color': 'var(--sss_orange)',
                            'color': '#273746',
                            'text-align': 'center'
                        },
                        td: {
                            'text-align': 'center',
                            'font-size': '0.9rem',
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
                    checkboxPlugin.props.store?.on("updated", (state) => {
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
                this.fullListOfEmployees = employees;
                this.gridJsTableData = [];
                for (let e of employees){
                    let gridJsEmployeeObject = { "id": e.userId, "name": e.username, "email": e.email, "contactNumber": e.contactNumber, "role": e.userType };
                    if (!e.defunct) { this.gridJsTableData.push(gridJsEmployeeObject); }
                }  
                
            },
            async createEmployee(details){
                var row = Object.keys(details).map((key) => details[key]);
                let doesNameOrEmailExist = this.fullListOfEmployees.some((e) => (e.username === details.username) || (e.email === details.email));
                if (!doesNameOrEmailExist && details.name != ""){ //check if name or email exist in current table, if name is empty, or is first row a header row
                    this.pushRow(row);
                    this.toastrResponse = {status: "Success", msg: "New employee has been created!"};    
                } else {
                    this.toastrResponse = {status: "Error", msg: "Employee name/email already exist!"};
                    this.showToast();     
                }
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
                    if (fileType == "csv"){
                        this.csvImport(selected, reader);   

                    } else if (fileType == "xlsx") {
                        this.xlsxImport(selected, reader);

                    } else {
                        this.toastrResponse = {status: "Error", msg: "Invalid file extension, please only use .csv or .xlsx!"};  
                    }
                    this.showToast();
                    file.value = ''; //clear input field so that we can upload the same file again
                }
            },
            csvImport(selected, reader){
                reader.onloadend = async () => { 
                    let data = reader.result.split("\r\n");
                    for (let i in data) {
                        data[i] = data[i].split(",");
                        let name = data[i][0];
                        let email = data[i][1];
                        let doesNameOrEmailExist = this.fullListOfEmployees.some((row) => (row.username === name) || (row.email === email));
                        if (!doesNameOrEmailExist && name != "" && name != "Name"){ //check if name exist in current table, if name is empty, or is first row a header row
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
                            let doesNameExist = this.fullListOfEmployees.some(z => row?.Name == undefined ? 
                            (z.username === Object.values(row)[1] || z.email === Object.values(row)[2])
                            : (z.username === row.Name || z.email === row.Email));

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
                    this.loading = true;
                    let record = { "username": row[0], "email": row[1], "contactNumber": row[2], "userType": row[3] };
                    await EmployeeService.whiteList(record);
                    await this.getAllEmployees();
                    this.refreshTable();
                    this.loading = false;
                } else {
                    this.toastrResponse = {status: "Error", msg: "Your data is invalid, please check your data set for missing fields or cells!"}; 
                    throw new Error("Invalid file");
                }
                this.showToast();
            },
            exportRecords(){
                let processedTableData = [];
                for (let d of this.gridJsTableData) {
                    processedTableData.push({
                        "Name": d.name,
                        "Email": d.email,
                        "Contact Number": d.contactNumber,
                        "Role": d.role
                    })
                }

                let workBook = XLSX.utils.book_new();
                const employeesDetails = XLSX.utils.json_to_sheet(processedTableData);
                employeesDetails['!cols'] = [ {wch:15}, {wch:25}, {wch:15}, {wch:15} ];
                XLSX.utils.book_append_sheet(workBook, employeesDetails, `Employees Details`);
                let exportFileName = `Employees Records.xlsx`;
                XLSX.writeFile(workBook, exportFileName);
            },
            refreshTable(){
                this.grid.updateConfig({
                    resizable: true,
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
                    { name: 'ID', width: '9%' },
                    { name: 'Name', width: '20%' },
                    { name: 'Email',  width: '25%' },
                    { name: 'Contact Number',  width: '15%' },
                    { name: 'Role', width: '15%'}, 
                    { 
                        name: 'Edit',
                        formatter: () => html(`<i class="fa-lg button bi bi-pencil text-primary" data-bs-toggle="modal" data-bs-target="#editModal"></i>`),
                        width: '10%'
                    }
                ],
                }).forceRender();
            },
        }
        
    }
</script>
<style>
    #tableBox{
        padding: 0 2.5%;
    }

    #buttonsHolder{
        position: relative;
    }

    #ddm {
        min-width: 120px !important;
    }   

    .funcBtn{
        position: relative;
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

    .links{
        text-decoration: none !important;
        color: #000 !important;
    }
    
    .icons{
        position: absolute;
        right:20px;
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

        .gridjs-table {
            width: 100% !important;
        }
    }

    .overlay {
        position: absolute;
        width: 100%;
        height: 100%;
        z-index: 1000;
        opacity: 0.5;
        filter: alpha(opacity=50);
    }

    .spinner-border-custom {
        position: absolute;
        top: 50%;
        left: 45%;
        z-index: 999;
        width: 120px !important;
        height: 120px !important;  
    }
</style>