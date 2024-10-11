import axios from 'axios';

const http = axios.create({
    // API Setting
    baseURL: 'http://localhost:8080/'
})

export default http;