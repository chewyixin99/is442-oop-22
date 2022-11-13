import { axiosClient } from "../axiosClient";
import BaseApiService from "../BaseApiService";

class LoanService extends BaseApiService {
    // async createEmployee(employeeDetails) {
    //     const bearer_token = `Bearer ${localStorage.getItem("token")}`;
    //     const config = {
    //         headers: { Authorization: bearer_token },
    //     };
    //     try {
    //         let employee = await axiosClient.post("/register", { ...employeeDetails }, config);
    //         return employee.data

    //     } catch (error) {
    //         return this.handleError(error);
    //     }
    // }

    async getLoan(id) {
        const bearer_token = `Bearer ${localStorage.getItem("token")}`;
        const config = {
            headers: { Authorization: bearer_token },
        };
        try {
            let loans = await axiosClient.get("/loan" + id, config);
            return loans.data

        } catch (error) {
            return this.handleError(error);
        }
    }

    async getAllLoans() {
        const bearer_token = `Bearer ${localStorage.getItem("token")}`;
        const config = {
            headers: { Authorization: bearer_token },
        };
        try {
            let loans = await axiosClient.get("/loan", config);
            return loans.data

        } catch (error) {
            return this.handleError(error);
        }
    }

    // async editEmployeeDetails(employeeDetails) {
    //     const bearer_token = `Bearer ${localStorage.getItem("token")}`;
    //     const config = {
    //         headers: { Authorization: bearer_token },
    //     };
    //     try {
    //         let employee = await axiosClient.put("/users/" + employeeDetails.id, {
    //             ...employeeDetails
    //         }, config);
    //         return employee.data

    //     } catch (error) {
    //         return this.handleError(error);
    //     }
    // }

    // async removeEmployees(employeeID) {
    //     const bearer_token = `Bearer ${localStorage.getItem("token")}`;
    //     const config = {
    //         headers: { Authorization: bearer_token },
    //     };
    //     try {
    //         let deletionStatus = await axiosClient.delete("/users/" + employeeID, config);
    //         return deletionStatus.data.message

    //     } catch (error) {
    //         return this.handleError(error);
    //     }
    // }
}

export default new LoanService();