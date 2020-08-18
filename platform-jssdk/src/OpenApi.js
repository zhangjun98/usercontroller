import {config, Scope} from "./config";
import Ajax from './ajax';

const headers = Symbol('headers');

export default class OpenApi {

    constructor() {
        const ajaxOptions = Object.assign({}, {
            baseURL: config.open.baseURL,
            crossDomain: true,
        });
        this.ajax = new Ajax(ajaxOptions);
    }

    [Scope.USER_INFO](options){
        const authHeaders = this[headers](options)
        if (authHeaders) {
            this.ajax.axios({
                url: config.open.userInfoUrl,
                method: 'GET',
                headers: authHeaders
            }).then(res=>{
                options.success(res.data);
            }).catch(err=>{
                options.error(err);
            })
        }
    }

    [Scope.MOBILE](options){
        const authHeaders = this[headers](options)
        if (authHeaders) {
            this.ajax.axios({
                url: config.open.userMobile,
                method: 'GET',
                headers: authHeaders
            }).then(res=>{
                options.success(res.data);
            }).catch(err=>{
                options.error(err);
            })
        }
        // options.error({code: '900002', message: '此功能正在开发中，敬请期待'});
    }

    [headers](options){
        const token = config.token.accessToken
        if (!token){
            // 为空
            options.error({code:'200101', message:'未授权'});
            return undefined;

        }
        return {
            'Authorization': 'Bearer ' + token,
        };
    }

}