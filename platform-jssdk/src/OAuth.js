
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
            options.token = token;
            this.userInfo(options);
            return;
        }
        const refreshToken = Cookies.get('ztr');
        if (refreshToken){
            options.refreshToken = refreshToken;
            this.refreshToken(options);
            return;
        }
        const queryStr = location.search.substring(1);
        console.log(queryStr)
        const params = Qs.parse(queryStr);
        console.log(params)
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
            this._setCookies(res, options);
        }).catch(err=>{
            options.error(err);
        })
        return this;
    }

    userInfo(options) {
        const token = (options.token) ? options.token : config.token.accessToken;
        if (!token){
            options.error({code:'200101', message:'未授权'});
            return;
        }
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

    refreshToken(options){
        const refreshToken = (options.refreshToken) ? options.refreshToken : config.token.refreshToken;
        const str =  this.options.appId + ":" + this.options.appSecret;
        const basic = Base64.encode(str);
        const headers = {
            'Authorization': 'Basic ' + basic,
            'Content-Type': 'application/x-www-form-urlencoded'
        };
        this.ajax.axios({
            url: config.oauth.accessTokenUrl,
            method: 'POST',
            data: {
                grant_type: 'refresh_token',
                refresh_token: refreshToken
            },
            headers: headers,
            transformRequest: [(data) => Qs.stringify(data)]
        }).then(res => {
            this._setCookies(res, options);
        }).catch(err => {
            options.error(err);
        })
    }

    _setCookies(res, options){
        if (!res.data){
            options.error({code:'200106', message:'授权失败'});
            return;
        }
        config.token = res.data;
        Cookies.set('ztt', res.data.accessToken, { expires: res.data.accessExpire, path: '' });
        Cookies.set('ztr', res.data.refreshToken, { expires: res.data.refreshExpire, path: '' });
        this.userInfo(options)
    }

}