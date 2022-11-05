import { axiosClient } from "../axiosClient";
import BaseApiService from "../BaseApiService";

class LoginService extends BaseApiService {
    async registerUser(userDetails) {
        try {
            let user = await axiosClient.post("/register", {...userDetails});
            return user.data

        } catch (error) {
            return this.handleError(error);
        }
    }

    async resetUserPassword(userEmail) {
        try {
            let user = await axiosClient.post("/resetPassword", {"email": userEmail});
            return user.data

        } catch (error) {
            return this.handleError(error);
        }
    }

    async savePassword(token, userDetails) {
        try {
            let user = await axiosClient.post("/savePassword", {...userDetails}, {params: {"token": token}});
            return user.data

        } catch (error) {
            return this.handleError(error);
        }
    }
}

export default new LoginService();