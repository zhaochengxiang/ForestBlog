import axios from 'axios'
import { getToken } from './auth.js'

// create an axios instance
const service = axios.create({
  method: 'post',
  baseURL: process.env.baseURL, // api 的 base_url
  timeout: 5000, // request timeout
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded'
  }
})

// request interceptor
service.interceptors.request.use(
  config => {
    if (getToken()) {
      config.headers['Access-Token'] = getToken()
    } 

    let ret = ''
    for (let it in config.data) {
      ret += encodeURIComponent(it) + '=' + encodeURIComponent(config.data[it]) + '&'
    }
    config.data = ret

    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
    response => {
      return response;
    },
    error => {
      console.log('err' + error); // for debug
      return Promise.reject(error);
    },
  );

export default service
