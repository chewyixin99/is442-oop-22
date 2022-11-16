import { axiosClient } from "../axiosClient";
import BaseApiService from "../BaseApiService";

class PassService extends BaseApiService {
    async getAllPasses() {
        const bearer_token = `Bearer ${localStorage.getItem("token")}`;
        const config = {
            headers: {
                Authorization: bearer_token,
            },
        };
        try {
            let passes = await axiosClient.get("/passes", config);
            return passes.data

        } catch (error) {
            return this.handleError(error);
        }
    }
}

export default new PassService();