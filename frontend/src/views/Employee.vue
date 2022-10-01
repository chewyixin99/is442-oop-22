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
            <div class="position-relative">
                <input id='fileid' type='file' hidden/>
                <input id='buttonid' type='button' class="importButton btn btn-info" value='Import' @click="openDialog"/>
                <button type="button" class="newButton btn btn-warning" data-bs-toggle="modal" data-bs-target="#createModal">New</button>
                <button type="button" class="delButton btn btn-danger" @click="deleteRecords()">Delete</button>
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
                grid: new Grid({
                    columns: [
                        {
                            id: 'employeeCheckBox',
                            plugin: {
                                component: RowSelection,
                                props: {
                                    // use the "email" column as the row identifier
                                    id: (row) => row.cell(1).data
                                }
                            },
                            width: '5%',
                        },
                        {
                            name: 'ID',
                            width: '7%'
                        },
                        {
                            name: 'Name', 
                            width: '15%'
                        },
                        {
                            name: 'Email', 
                            width: '20%'
                        },
                        {
                            name: 'Phone Number', 
                            width: '15%'
                        },
                        'Department', 
                        'Role', 
                        { 
                            name: 'Action   ',
                            // formatter: (cell, row) => {
                            //     return h('button', {
                            //         className: 'py-2 px-3 border rounded text-white bg-primary',
                            //         onClick: () => alert(`Editing "${row.cells[0].data}" "${row.cells[1].data}"`)
                            //     }, 'Edit');
                            // }
                            formatter: () => html(`<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#editModal">Edit</button>`),
                            width: '10%'
                        },
                    ],
                    data: [
                        [1, 'John Cena', 'john@example.com', '(353) 01 222 3333', 'c', 'd'],
                        [2, 'Joe Mama', 'mark@gmail.com',   '(01) 22 888 4444' , 'c', 'd'],
                        [3, 'Imagine Dragon Deez Nuts', 'eo3n@yahoo.com',   '(05) 10 878 5554', 'c', 'd'],
                        [4, 'Nisen', 'nis900@gmail.com',   '313 333 1923', 'c', 'd']
                    ],
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
                            'text-align': 'center'
                        }
                    }
                })
            }
        },
        mounted() {
            this.grid.render(document.getElementById("wrapper"));
            this.grid.on('ready', () => {
                // find the plugin with the give plugin ID
                const checkboxPlugin = this.grid.config.plugin.get('employeeCheckBox');
                
                // subscribe to the store events
                checkboxPlugin.props.store.on('updated', (state, prevState) => {
                    console.log('checkbox updated', state, prevState);
                    this.recordsToDelete = state["rowIds"];
                });
            })
        },
        methods: {
            updateToastrMsg(res){
                this.toastrResponse = res;
            },
            async deleteRecords(){
                this.recordsToDelete.forEach((value, index) => {
                    console.log("Index to delete:", index, "Id value:", value);
                })
                // const employees = await EmployeeService.removeEmployees();
                // console.log(employees);
            },
            openDialog() {
                document.getElementById('fileid').click();
            }
        }
        
    }
</script>
<style>
    #tableBox{
        padding: 0 10%;
    }

    .gridjs-table{
        width: 100%;
    }
    .importButton{
        position: absolute;
        top: 10px;
        right: 160px;
        z-index: 999;
    }
    .newButton{
        position: absolute;
        top: 10px;
        right: 90px;
        z-index: 999;
    }
    .delButton{
        position: absolute;
        top: 10px;
        right: 5px;
        z-index: 999;
    }
</style>