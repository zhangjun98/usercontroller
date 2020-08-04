
import {config} from './config'
import Cookies from 'js-cookie';
import {Base64} from 'js-base64';
import Qs from 'qs';
import Ajax from './ajax';


export default class OAuth {

    constructor(options) {
        this.options = options;
        const ajaxOptions = Object.assign({}, {
            baseURL: config.oauth.baseURL,
            crossDomain: true,
        });
        this.ajax = new Ajax(ajaxOptions);
    }

    /**
     * 授权
     */
    authorize(options){
        // 获取token
        const token = Cookies.get('ztt');
        if (token){
            return;
        }
        const queryStr = location.search.substring(1);
        const params = Qs.parse(queryStr);
        if (params.code){
            this.generateToken(params.code, options);
            return;
        }
        this.generateAuthorize(options.scope);
    }

    /**
     * 跳转授权页面
     */
    generateAuthorize(scope) {
        const tempObj = {
            client_id: this.options.appId,
            response_type: 'code',
            redirect_uri: location.href,
            scope: scope
        };
        const params = Qs.stringify(tempObj);
        location.href = config.oauth.authorizeUrl + "?" + params;
    }

    /**
     * 获取token
     */
    generateToken(code, options){
        const str =  this.options.appId + ":" + this.options.appSecret;
        const basic = Base64.encode(str);
        const headers = {
            'Authorization': 'Basic ' + basic,
            'Content-Type': 'application/x-www-form-urlencoded'
        };

        const url = location.href;
        const uri = url.substring(0, url.lastIndexOf('&'))
        this.ajax.axios({
            url: config.oauth.accessTokenUrl,
            method: 'POST',
            data: {
                code: code,
                redirect_uri: uri,
                grant_type: 'authorization_code',
            },
            headers: headers,
            transformRequest: [(data) => Qs.stringify(data)]
        }).then(res=>{
            this.userInfo(res.data.accessToken, options);
        }).catch(err=>{
            options.error(err);
        })
    }

    userInfo(token, options) {
        const headers = {
            'Authorization': 'Bearer ' + token,
        };
        this.ajax.axios({
            url: config.oauth.userInfoUrl,
            method: 'GET',
            headers: headers
        }).then(res=>{
            options.success(res.data);
        }).catch(err=>{
            options.error(err);
        })
    }

}