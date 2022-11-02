import { axiosClient } from "../axiosClient";
import BaseApiService from "../BaseApiService";

class EmailService extends BaseApiService {
    async getAllEmailTemplates() {
        const bearer_token = `Bearer ${localStorage.getItem("token")}`;
        const config = {
            headers: {
                Authorization: bearer_token,
            },
        };
        try {
            let emailTemplates = await axiosClient.get("/templates", config);
            // console.log(emailTemplates.data);
            return emailTemplates.data

        } catch (error) {
            return this.handleError(error);
        }
    }
}

export default new EmailService();