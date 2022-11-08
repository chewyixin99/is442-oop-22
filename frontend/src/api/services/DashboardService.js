import { axiosClient } from "../axiosClient";
import BaseApiService from "../BaseApiService";

class DashboardService extends BaseApiService {
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

    async getPassBreakDown() {
        const bearer_token = `Bearer ${localStorage.getItem("token")}`;
        const config = {
            headers: { Authorization: bearer_token },
        };
        try {
            let loans = await axiosClient.get("/analytics/getPassBreakdown", config);
            return loans.data.data

        } catch (error) {
            return this.handleError(error);
        }
    }    

    async getPoiBreakDown() {
        const bearer_token = `Bearer ${localStorage.getItem("token")}`;
        const config = {
            headers: { Authorization: bearer_token },
        };
        try {
            let poi = await axiosClient.get("/analytics/getPoiBreakdown", config);
            return poi.data.data

        } catch (error) {
            return this.handleError(error);
        }
    } 
}

export default new DashboardService();