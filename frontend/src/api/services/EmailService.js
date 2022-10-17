import { axiosClient } from "../axiosClient";
import BaseApiService from "../BaseApiService";

class EmailService extends BaseApiService {
    async getAllEmailTemplates() {
        try {
            let emailTemplates = await axiosClient.get("/templates");
            // console.log(emailTemplates.data);
            return emailTemplates.data

        } catch (error) {
            return this.handleError(error);
        }
    }
}

export default new EmailService();