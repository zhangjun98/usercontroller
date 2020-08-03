import axios from 'axios';

export default class Ajax {

    constructor(options) {
        this.axios = axios.create(options ? options : {});
        this.initSso();
    }

    initSso() {
        /**
         * 请求拦截器
         */
        this.axios.interceptors.request.use((config) => {
                return config;
            },(err) => {
                return Promise.reject(err);
            }
        );

        /**
         * 响应拦截器
         */
        this.axios.interceptors.response.use((response) => {
                return response;
            },(err) => {
                // 对响应错误做点什么
                return Promise.reject(err);
            }
        );
    }

}

