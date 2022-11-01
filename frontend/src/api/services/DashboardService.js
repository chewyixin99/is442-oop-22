import { axiosClient } from "../axiosClient";
import BaseApiService from "../BaseApiService";

class DashboardService extends BaseApiService {
    async getAllLoans() {
        try {
            let loans = await axiosClient.get("/loan");
            return loans.data.data

        } catch (error) {
            return this.handleError(error);
        }
    }

    async getPassBreakDown() {
        try {
            let loans = await axiosClient.get("/analytics/getPassBreakdown");
            return loans.data.data

        } catch (error) {
            return this.handleError(error);
        }
    }    

    async getPoiBreakDown() {
        try {
            let poi = await axiosClient.get("/analytics/getPoiBreakdown");
            return poi.data.data

        } catch (error) {
            return this.handleError(error);
        }
    } 
}

export default new DashboardService();