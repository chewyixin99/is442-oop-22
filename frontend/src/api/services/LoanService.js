import { axiosClient } from "../axiosClient";
import BaseApiService from "../BaseApiService";

class LoanService extends BaseApiService {

    async createLoan(data) {
        const bearer_token = `Bearer ${localStorage.getItem("token")}`;
        const config = {
            headers: { Authorization: bearer_token },
        };
        try {
            let response = await axiosClient.post("/loan", data, config);
            return response

        } catch (error) {
            return this.handleError(error);
        }
    }

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
            return loans.data.data

        } catch (error) {
            return this.handleError(error);
        }
    }

    async getLoansByPassId(selectedPass) {
        console.log(selectedPass);
        const bearer_token = `Bearer ${localStorage.getItem("token")}`;
        const config = {
            headers: { Authorization: bearer_token },
        };
        try {
            let loans = await axiosClient.get("/loan", config);
            return loans.data.data.filter(
                (pass) =>
                    pass.pass.passId == selectedPass.passId &&
                    pass.defunct == false
            );

        } catch (error) {
            return this.handleError(error);
        }
    }

    async checkSecondPass(primaryPassId, primaryPassPOI) {
        const bearer_token = `Bearer ${localStorage.getItem("token")}`;
        const config = {
            headers: { Authorization: bearer_token },
        };

        try {
            let passes = await axiosClient.get("/passes", config);
            console.log(passes);
            return passes.data.data.filter(
                (pass) =>
                    pass.poi == primaryPassPOI && primaryPassId != pass.passId
            );
        } catch (error) {
            return this.handleError(error);
        }
    }
    async checkValidityOfSecondaryPass(primaryPassId, primaryPassPOI, primaryPassStartDate) {
        const bearer_token = `Bearer ${localStorage.getItem("token")}`;
        const config = {
            headers: { Authorization: bearer_token },
        };

        let secondaryPass = await this.checkSecondPass(primaryPassId, primaryPassPOI)
        try {
            let secondaryPassLoans = await axiosClient.get("/loan/byPassId/" + secondaryPass[0].passId, config);
            let conflictLoans = secondaryPassLoans.data.data.filter(
                (loan) =>
                    loan.startDate == primaryPassStartDate && loan.defunct == false
            );

            console.log(conflictLoans);
            return conflictLoans.length > 0;



        } catch (error) {
            return this.handleError(error);
        }
    }

    async getLoansPerMonth(userId) {
        const bearer_token = `Bearer ${localStorage.getItem("token")}`;
        const config = {
            headers: { Authorization: bearer_token },
        };
        let current_month = new Date().toISOString().replace(/T.*$/, "").substring(0, 7);
        try {
            let data = await axiosClient.get("/loan/getNumLoansGroupedeByMonthByUserId/" + userId, config);
            console.log(data.data.data);
            return data.data.data[current_month] == null ? 0 : data.data.data[current_month];

        } catch (error) {
            return this.handleError(error);
        }
    }

    async cancelLoan(loanId) {
        const bearer_token = `Bearer ${localStorage.getItem("token")}`;
        const config = {
            headers: { Authorization: bearer_token },
        };
        try {
            let response = await axiosClient.delete("/loan/" + loanId, config);
            return response;

        } catch (error) {
            return this.handleError(error);
        }
    }

    async updateLoanStatus(data) {
        const bearer_token = `Bearer ${localStorage.getItem("token")}`;
        const config = {
            headers: { Authorization: bearer_token },
        };
        try {
            let response = await axiosClient.put("/loan/updateCompleted", data, config);
            console.log(response.data.data);
            return response;

        } catch (error) {
            return this.handleError(error);
        }
    }

}

export default new LoanService();