import { axiosClient } from "../axiosClient";
import BaseApiService from "../BaseApiService";

class EmployeeService extends BaseApiService {
    async createEmployee(employeeDetails) {
        try {
            let employee = await axiosClient.post("/users", { ...employeeDetails });
            console.log(employee);
            return employee.data

        } catch (error) {
            return this.handleError(error);
        }
    }

    async getEmployee(val) {
        try {
            let employee = await axiosClient.get("users/" + val);
            console.log(employee);
            return employee.data

        } catch (error) {
            return this.handleError(error);
        }
    }

    async getAllEmployees() {
        try {
            let employees = await axiosClient.get("/users");
            console.log(employees);
            return employees.data

        } catch (error) {
            return this.handleError(error);
        }
    }

    async editEmployeeDetails(employeeDetails) {
        try {
            let employee = await axiosClient.put("/users/9", {
                ...employeeDetails
            });
            console.log(employee);
            return employee.data

        } catch (error) {
            console.log(error);
            return this.handleError(error);
        }
    }

    async removeEmployees(employeeID) {
        try {
            let deletionStatus = await axiosClient.delete("/users", {
                params : {
                    "userId": employeeID
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