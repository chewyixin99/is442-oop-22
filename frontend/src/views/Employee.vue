<template>
    <div id="employee">
        <h1>Employee</h1>
        ------------------------------------
        <p>Admin functionalities:</p>
        ------------------------------------
        <p>1. Table that displays all employee details with edit/delete functionalities</p>
        <p>2. Search function tied to the table</p>
        <p>3. Upload of CSV file</p>
        <div id="wrapper"></div>
    </div>
</template>
<script>
    import { Grid } from "gridjs";

    export default {
      name: 'GridComponent',
      components: {
      },
      data() {
        return {
            grid: new Grid({
                columns: ['Name', 'Email', 'Phone Number', 'Address', 'Department', 'Role', 'ID'],
                search: true,
                sort: true,
                pagination: {
                    limit: 10
                },
                data: [
                    ['John', 'john@example.com', '(353) 01 222 3333', 'a', 'b', 'c', 'd'],
                    ['Mark', 'mark@gmail.com',   '(01) 22 888 4444' , 'a', 'b', 'c', 'd'],
                    ['Eoin', 'eo3n@yahoo.com',   '(05) 10 878 5554', 'a', 'b', 'c', 'd'],
                    ['Nisen', 'nis900@gmail.com',   '313 333 1923', 'a', 'b', 'c', 'd']
                ],
                style: {
                    table: {
                    border: '3px solid #ccc'
                    },
                    th: {
                    'background-color': 'rgba(0, 0, 0, 0.1)',
                    color: '#000',
                    'border-bottom': '3px solid #ccc',
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
            
            // DOM instead of vue selector because we are using vanilla JS
            this.grid.render(document.getElementById("wrapper"));
        }
        
    }
</script>
<style>
    .gridjs-table{
        width:100%;
    }
</style>