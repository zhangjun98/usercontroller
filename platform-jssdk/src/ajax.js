import axios from "axios";

export default class Ajax {
  constructor(options) {
    this.axios = axios.create(options ? options : {});
    this.initSso();
  }

  initSso() {
    /**
     * 请求拦截器
     */
    this.axios.interceptors.request.use(
      config => {
        return config;
      },
      err => {
        return Promise.reject(err);
      }
    );

    /**
     * 响应拦截器
     */
    this.axios.interceptors.response.use(
      response => {
        const res = response.data;
        if (res.code === "000000") {
          return res;
        } else {
          // 对响应错误做点什么
          return Promise.reject(res);
        }
      },
      err => {
        let response = err.response;
        if (response.status === 401 || response.status === 403) {
          return Promise.reject(err.response);
        }
        console.error(err);
        console.log("err", response);
        // 对响应错误做点什么
        return Promise.reject({ code: "101001", message: "请求失败" });
      }
    );
  }
}
