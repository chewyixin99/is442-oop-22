import { axiosClient } from "../axiosClient";
import BaseApiService from "../BaseApiService";

class DashboardService extends BaseApiService {
    async getLoansForMonth() {
        try {
            let loans = await axiosClient.get("/analytics/getLoansForMonth/9");
            return loans.data.data

        } catch (error) {
            return this.handleError(error);
        }
    }

}

export default new DashboardService();