import axios from 'axios';

const fallbackURL = "http://localhost:5000"


const axiosClient = axios.create({
    baseURL: process.env.VUE_APP_API_URL || fallbackURL,
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
    },
    credentials: 'include',
    withCredentials: true
});

export {
    axiosClient
}