import axios from "axios";
import { ENDPOINT } from "./constants";

export default axios.create({
    baseURL: ENDPOINT,
    headers: {
        "Content-type": "application/json"
    }
});