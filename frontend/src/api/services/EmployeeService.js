import { axiosClient } from "../axiosClient";
import BaseApiService from "../BaseApiService";

class EmployeeService extends BaseApiService {
    async createEmployee(employeeDetails) {
        try {
            let employee = await axiosClient.post("/users", { ...employeeDetails });
            return employee.data

        } catch (error) {
            return this.handleError(error);
        }
    }

    async getEmployee(val) {
        try {
            let employee = await axiosClient.get("users/" + val);
            return employee.data

        } catch (error) {
            return this.handleError(error);
        }
    }

    async getAllEmployees() {
        try {
            let employees = await axiosClient.get("/users");
            return employees.data

        } catch (error) {
            return this.handleError(error);
        }
    }

    async editEmployeeDetails(employeeDetails) {
        console.log(employeeDetails);
        try {
            let employee = await axiosClient.put("/users/" + employeeDetails.id, {
                ...employeeDetails
            });
            return employee.data

        } catch (error) {
            return this.handleError(error);
        }
    }

    async removeEmployees(employeeID) {
        try {
            let deletionStatus = await axiosClient.delete("/users/" + employeeID);
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