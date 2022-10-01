import { axiosClient } from "../axiosClient";
import BaseApiService from "../BaseApiService";

class EmployeeService extends BaseApiService {
    async createEmployee(employeeDetails) {
        try {
            let employee = await axiosClient.post("/create_employee", {
                params: {...employeeDetails}
            });
            console.log(employee);
            return employee.data

        } catch (error) {
            return this.handleError(error);
        }
    }

    async getAllEmployees() {
        try {
            let employees = await axiosClient.get("/get_all_employees");
            console.log(employees);
            return employees.data

        } catch (error) {
            return this.handleError(error);
        }
    }

    async editEmployeeDetails(employeeDetails) {
        try {
            let employee = await axiosClient.patch("/edit_employee", {
                params: {...employeeDetails}
            });
            console.log(employee);
            return employee.data

        } catch (error) {
            return this.handleError(error);
        }
    }

    async removeEmployees(employeeIDs) {
        try {
            let deletionStatus = await axiosClient.delete("/remove_employees", {
                params : {
                    employee_id: employeeIDs
                }
            });
            console.log(deletionStatus);
            return deletionStatus.data.message

        } catch (error) {
            return this.handleError(error);
        }
    }

    async importEmployees(employeeDetails) {
        try {
            let employees = await axiosClient.post("/import_employees", {
                params : {
                    ...employeeDetails
                }
            });
            console.log(employees);
            return employees.data.message

        } catch (error) {
            return this.handleError(error);
        }
    }
}

export default new EmployeeService();