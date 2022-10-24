import { axiosClient } from "../axiosClient";
import BaseApiService from "../BaseApiService";

class PassService extends BaseApiService {
    async getAllPasses() {
        try {
            let passes = await axiosClient.get("/passes");
            // console.log(passes.data);
            return passes.data

        } catch (error) {
            return this.handleError(error);
        }
    }

    // async createPass(passDetails) {
    //     try {
    //         let pass = await axiosClient.post("/passes", { ...passDetails });
    //         console.log(pass);
    //         return pass.data

    //     } catch (error) {
    //         return this.handleError(error);
    //     }
    // }
    // async deletePass(passID) {
    //     try {
    //         let deletePass = await axiosClient.delete("/passes/", { ...passID })
    //         console.log(deletePass)
    //         return deletePass.data.message
    //     } catch (error) {
    //         return this.handleError(error);
    //     }
    // }
}

export default new PassService();