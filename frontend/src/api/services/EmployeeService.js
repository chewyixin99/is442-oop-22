import { axiosClient } from "../axiosClient";
import BaseApiService from "../BaseApiService";

class EmployeeService extends BaseApiService {    
    async whiteList(employeeDetails) {
        const bearer_token = `Bearer ${localStorage.getItem("token")}`;
        const config = {
            headers: { Authorization: bearer_token },
        };
        try {
            let employee = await axiosClient.post("/whitelist", {...employeeDetails }, config);
            return employee.data

        } catch (error) {
            return this.handleError(error);
        }
    }
    
    async getAllEmployees() {
        const bearer_token = `Bearer ${localStorage.getItem("token")}`;
        const config = {
            headers: { Authorization: bearer_token },
        };
        try {
            let employees = await axiosClient.get("/users", config);
            return employees.data.data

        } catch (error) {
            return this.handleError(error);
        }
    }

    async editEmployeeDetails(employeeDetails) {
        const bearer_token = `Bearer ${localStorage.getItem("token")}`;
        const config = {
            headers: { Authorization: bearer_token },
        };
        try {
            let employee = await axiosClient.put("/users/" + employeeDetails.id, {
                ...employeeDetails
            }, config);
            return employee.data

        } catch (error) {
            return this.handleError(error);
        }
    }

    async removeEmployees(employeeID) {
        const bearer_token = `Bearer ${localStorage.getItem("token")}`;
        const config = {
            headers: { Authorization: bearer_token },
        };
        try {
            let deletionStatus = await axiosClient.delete("/users/" + employeeID, config);
            return deletionStatus.data.message

        } catch (error) {
            return this.handleError(error);
        }
    }
}

export default new EmployeeService();