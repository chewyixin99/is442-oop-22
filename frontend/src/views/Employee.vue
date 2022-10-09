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
                    <input id="fileid" type="file" hidden/>
                    <input id="buttonid" type="button" class="importBtn funcBtn btn btn-info" value='Import' @click="openDialog"/>
                </div>
                <div class="px-3"><button type="button" class="newBtn funcBtn btn btn-warning" data-bs-toggle="modal" data-bs-target="#createModal">New</button></div>
                <div><button type="button" class="delBtn funcBtn btn btn-danger" @click="deleteRecords()">Delete</button></div>
            </div>
            <div id="wrapper">
            </div>
        </div>
        <EmployeeModal @toastrMsg="updateToastrMsg" id="createModal" modalType="create"></EmployeeModal>
        <EmployeeModal @toastrMsg="updateToastrMsg" id="editModal" modalType="edit"></EmployeeModal>
        <TheToastr :toastrResponse="toastrResponse"></TheToastr>
    </div>
</template>
<script>
    // import EmployeeService from "@/api/services/EmployeeService";   
    import { Grid, html } from "gridjs";
    import { RowSelection } from "gridjs/plugins/selection";
    import TheToastr from "@/components/TheToastr.vue";
    import * as bootstrap from "bootstrap";
    import EmployeeModal from "../components/EmployeeModal.vue"

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
                        [1, 'John Cena', 'john@example.com', '(353) 01 222 3333', 'c', 'd'],
                        [2, 'Joe Mama', 'mark@gmail.com',   '(01) 22 888 4444' , 'c', 'd'],
                        [3, 'Imagine Dragon Deez Nuts', 'eo3n@yahoo.com',   '(05) 10 878 5554', 'c', 'd'],
                        [4, 'Nisen', 'nis900@gmail.com',   '313 333 1923', 'c', 'd'],
                        [5, 'Marco Polo', 'mp@gmail.com',   '(05) 10 878 6757', 'c', 'd'],
                        [6, 'Nisen', 'nis900@gmail.com',   '313 333 1923', 'c', 'd']
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
                            // formatter: (cell, row) => {
                            //     return h('button', {
                            //         className: 'py-2 px-3 border rounded text-white bg-primary',
                            //         onClick: () => alert(`Editing "${row.cells[0].data}" "${row.cells[1].data}"`)
                            //     }, 'Edit');
                            // }
                            formatter: () => html(`<i class="fa-lg button bi bi-pencil text-primary" data-bs-toggle="modal" data-bs-target="#editModal"></i>`),
                            width: '1%'
                        },
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
                        table: {
                            // border: '3px solid #ccc'
                        },
                        th: {
                            'background-color': 'rgba(0, 0, 0, 0.1)',
                            // 'color': '#000',
                            // 'border-bottom': '3px solid #ccc',
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
            this.updateGridJsTable();
        },
        methods: {
            updateToastrMsg(res){
                this.toastrResponse = res;
            },
            async deleteRecords(){
                var bsAlert = new bootstrap.Toast(document.getElementById("theToastr"));//inizialize it
                if (this.recordsToDelete.length > 0){
                    this.recordsToDelete.forEach((value, index) => {
                        console.log("Index to delete:", index, "Id value:", value);
                        this.gridJsTableData.splice(value, 1);
                    })
                    this.updateGridJsTable();
                    // const employees = await EmployeeService.removeEmployees();
                    // console.log(employees);
                    this.toastrResponse = {status: "Success", msg: "Records have been successfully deleted!"};

                } else {
                    this.toastrResponse = {status: "Error", msg: "Please select at least 1 record to delete!"};           
                }
                bsAlert.show();
            },
            openDialog() {
                document.getElementById('fileid').click();
            },
            updateGridJsTable(){
                this.grid.updateConfig({
                    search: true,
                    data: this.gridJsTableData
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