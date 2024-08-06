import axios from 'axios'
import {useAppStore} from "@/stores/app";

const request = axios.create({
  // baseURL: process.env.VUE_APP_baseUrl, // api的base_url 配置后，请求会拼接对应uri
  timeout: 120000 // request timeout
});

// 响应拦截器
request.interceptors.response.use(response => {
  const responseCode = response.data.code;
  if (responseCode !== 200) {
    // 交给catch处理
    return Promise.reject(response.data);
  }
  response.data.originResponse = response;
  return response.data
}, error => {
  useAppStore()
  return Promise.reject(error)
});

export default request
