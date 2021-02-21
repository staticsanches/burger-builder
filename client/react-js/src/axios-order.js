import axios from 'axios';

const instance = axios.create({
	baseURL: process.env.REACT_APP_REMOTE_URL
});

export default instance;
