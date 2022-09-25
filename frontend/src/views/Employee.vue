<template>
    <div id="employee" class="px-3">
        <h1>Employee</h1>
        ------------------------------------
        <p>Admin functionalities:</p>
        ------------------------------------
        <p>1. Table that displays all employee details with edit/delete functionalities</p>
        <p>2. Search function tied to the table</p>
        <p>3. Upload of CSV file</p>
        <div class="tableBox position-relative">
            <button type="button" class="newButton btn btn-info">New</button>
            <button type="button" class="delButton btn btn-danger" @click="deleteRecords()">Delete</button>
            <div id="wrapper">
            </div>
        </div>
    </div>
</template>
<script>
    import { Grid, h } from "gridjs";
    import { RowSelection } from "gridjs/plugins/selection";

    export default {
        name: 'GridComponent',
        components: {
        },
        data() {
            return {
                recordsToDelete: [],
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
                            width: '6%'
                        },
                        'Name', 
                        'Email', 
                        'Phone Number', 
                        'Department', 
                        'Role', 
                        { 
                            name: 'Actions',
                            formatter: (cell, row) => {
                                return h('button', {
                                    className: 'py-2 px-3 border rounded text-white bg-primary',
                                    onClick: () => alert(`Editing "${row.cells[0].data}" "${row.cells[1].data}"`)
                                }, 'Edit');
                            }
                        },
                    ],
                    data: [
                        [1, 'John', 'john@example.com', '(353) 01 222 3333', 'c', 'd'],
                        [2, 'Mark', 'mark@gmail.com',   '(01) 22 888 4444' , 'c', 'd'],
                        [3, 'Eoin', 'eo3n@yahoo.com',   '(05) 10 878 5554', 'c', 'd'],
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
            deleteRecords(){
                this.recordsToDelete.forEach((value, index) => {
                    console.log("Index to delete:", index, "Id value:", value);
                })
            }
        }
        
    }
</script>
<style>
    .gridjs-table{
        width: 100%;
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