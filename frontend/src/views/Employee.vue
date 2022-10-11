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
        <EmployeeModal @toastrMsg="updateToastrMsg" id="createModal" modalType="create" @createEmployee="createEmployee"></EmployeeModal>
        <EmployeeModal @toastrMsg="updateToastrMsg" id="editModal" modalType="edit" @editEmployeeDetails="editEmployeeDetails"></EmployeeModal>
        <TheToastr :toastrResponse="toastrResponse"></TheToastr>
    </div>
</template>
<script>
    // import EmployeeService from "@/api/services/EmployeeService";   
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
                gridJsTableData: [
                    {id: 1, name: 'John Cena', email: 'john@example.com', phoneNumber: '(353) 01 222 3333', role: 'c'},
                    {id: 2, name: 'Joe Mama', email: 'mark@gmail.com', phoneNumber: '(01) 22 888 4444' , role: 'c'},
                    {id: 3, name: 'Imagine Dragon Deez Nuts', email: 'eo3n@yahoo.com', phoneNumber: '(05) 10 878 5554', role: 'c'},
                    {id: 4, name: 'Nisen', email: 'nis900@gmail.com', phoneNumber: '313 333 1923', role: 'c'},
                    {id: 5, name: 'Marco Polo', email: 'mp@gmail.com', phoneNumber: '(05) 10 878 6757', role: 'c'},
                    {id: 6, name: 'Nisen', email: 'nis900@gmail.com', phoneNumber: '313 333 1923', role: 'c'}
                ],
                grid: new Grid({
                    resizable: true,
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
                    { name: 'Phone Number',  width: '20%' },
                    'Role', 
                    { 
                        name: '#',
                        formatter: () => html(`<i class="fa-lg button bi bi-pencil text-primary" data-bs-toggle="modal" data-bs-target="#editModal"></i>`),
                        width: '1%'
                    }
                ],
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
        mounted() {
            this.grid.render(document.getElementById("wrapper"));
            this.grid.on("ready", () => {
                // find the plugin with the give plugin ID
                const checkboxPlugin = this.grid.config.plugin.get("employeeCheckBox");
                
                // subscribe to the store events
                checkboxPlugin.props.store.on("updated", (state, prevState) => {
                    console.log("checkbox updated", state, prevState);
                    this.recordsToDelete = state["rowIds"];
                });
            })
            this.refreshTable();
        },
        methods: {
            updateToastrMsg(res){
                this.toastrResponse = res;
            },
            async createEmployee(details){
                // const employee = await EmployeeService.createEmployee();
                // console.log(employee);

                var row = Object.keys(details).map((key) => details[key]);
                let doesNameExist = this.gridJsTableData.some((e) => e.name === details.eName);
                if (!doesNameExist && details.name != ""){ //check if name exist in current table, if name is empty, or is first row a header row
                    this.pushRow(row);
                    this.toastrResponse = {status: "Success", msg: "New employee has been created!"};    
                } else {
                    this.toastrResponse = {status: "Error", msg: "Employee name already exist!"};            
                }
                this.refreshTable();

                var bsAlert = new Toast(document.getElementById('theToastr'));         
                bsAlert.show();
            },
            async editEmployeeDetails(details){
                // const employees = await EmployeeService.editEmployeeDetails("1");
                // console.log(employees);

                var row = Object.keys(details).map((key) => details[key]);
                console.log(row);
                // this.gridJsTableData.push(row);
                this.refreshTable();

                var bsAlert = new Toast(document.getElementById('theToastr'));
                // this.toastrResponse = {status: "Success", msg: "Your changes have been saved!"};
                this.toastrResponse = {status: "Error", msg: "Opps, something went wrong!"};
                bsAlert.show();
            },
            async deleteRecords(){
                if (this.recordsToDelete.length > 0){
                    this.recordsToDelete.forEach((value, index) => {
                        console.log("Index to delete:", index, "Id value:", value + 1);
                        this.gridJsTableData = this.gridJsTableData.filter(function( obj ) {
                            return obj.id !== value + 1;
                        });
                    })
                    this.refreshTable();
                    // const employees = await EmployeeService.removeEmployees();
                    // console.log(employees);
                    this.toastrResponse = {status: "Success", msg: "Records have been successfully deleted!"};

                } else {
                    this.toastrResponse = {status: "Error", msg: "Please select at least 1 record to delete!"};           
                }
                var bsAlert = new Toast(document.getElementById("theToastr"));
                bsAlert.show();
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

                    var bsAlert = new Toast(document.getElementById("theToastr"));
                    bsAlert.show();
                }
            },
            csvImport(selected, reader){
                reader.onloadend = () => { 
                    let data = reader.result.split("\r\n");
                    for (let i in data) {
                        data[i] = data[i].split(",");
                        let name = data[i][0];
                        let doesNameExist = this.gridJsTableData.some((row) => row.name === name);
                        if (!doesNameExist && name != "" && name != "Name"){ //check if name exist in current table, if name is empty, or is first row a header row
                            this.pushRow(data[i]);
                        }
                    }
                    this.refreshTable();
                }
                reader.readAsText(selected);
            },
            xlsxImport(selected, reader){
                reader.onloadend = (e) => {
                    var data = e.target.result;
                    var workbook = XLSX.read(data, { type: 'binary' });
                    workbook.SheetNames.forEach((sheetName) => {
                        var XL_row_object = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
                        for (let row of XL_row_object){
                            let doesNameExist = this.gridJsTableData.some(z => row?.Name == undefined ? z.name === Object.values(row)[1] : z.name === row.Name);
                            if (!doesNameExist && row.Name != ""){
                                var record = Object.keys(row).map((key) => String(row[key])); //convert object to array form
                                //If .xlsx file has no header, switch the position of affected elements in the array. From [Phone number, Name, Email] to [Name, Email, Phone Number]
                                [record[0], record[1], record[2]] = row.Name == undefined ? [record[1], record[2], record[0]] : [record[0], record[1], record[2]];
                                this.pushRow(record);
                            }
                        }
                    })
                    this.refreshTable();
                };

                reader.readAsBinaryString(selected);
            },
            pushRow(row){
                if (row.length == 4 && !row.includes("")){
                    let record = {
                        "id": this.gridJsTableData[this.gridJsTableData.length - 1].id + 1,
                        "name": row[0],
                        "email": row[1],
                        "phoneNumber": row[2],
                        "role": row[3]
                    };
                    this.gridJsTableData.push(record);
                } else {
                    this.toastrResponse = {status: "Error", msg: "Your data is invalid, please check your data set for missing fields or cells!"}; 
                    throw new Error("Invalid file");
                }
            },
            refreshTable(){
                //Need to redeclare grid js columns properties if not won't work
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
                    { name: 'Phone Number',  width: '20%' },
                    'Role', 
                    { 
                        name: '#',
                        formatter: () => html(`<i class="fa-lg button bi bi-pencil text-primary" data-bs-toggle="modal" data-bs-target="#editModal"></i>`),
                        width: '1%'
                    }
                ],
                }).forceRender();
            }
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