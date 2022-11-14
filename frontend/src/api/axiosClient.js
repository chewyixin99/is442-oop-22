import axios from 'axios';
import { ENDPOINT } from '@/constants';

const fallbackURL = ENDPOINT


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