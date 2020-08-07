
import {config} from './config'
import Cookies from 'js-cookie';
import {Base64} from 'js-base64';
import Qs from 'qs';
import Ajax from './ajax';

const setCookies = Symbol('setCookies');

export default class OAuth {

    constructor() {
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
        if (config.token.accessToken){
            options.token = config.token.accessToken;
            options.success(
                {
                    code:'000000',
                    data: config.token
                }
            )
            return;
        }
        if (config.token.refreshToken){
            options.refreshToken = config.token.refreshToken;
            this.refreshToken(options);
            return;
        }
        const queryStr = location.search.substring(1);
        const params = Qs.parse(queryStr);
        if (params.code){
            this.generateToken(params.code, options);
            return;
        }
        this.generateAuthorize(config.scopes);
    }

    /**
     * 跳转授权页面
     */
    generateAuthorize(scopes) {
        const tempObj = {
            client_id: config.appId,
            response_type: 'code',
            'redirect_uri': location.href
        };

        if (scopes){
            tempObj.scope = scopes.join('+')
        }

        const params = Qs.stringify(tempObj);
        location.href = config.oauth.baseURL + config.oauth.authorizeUrl + "?" + params;
    }

    /**
     * 获取token
     */
    generateToken(code, options){
        const url = location.href;
        const uri = url.substring(0, (url.lastIndexOf("code") - 1))
        const basic = Base64.encode(config.appId + ":" + config.appSecret);
        const headers = {
            'Authorization': 'Basic ' + basic,
            'Content-Type': 'application/x-www-form-urlencoded'
        };

        this.ajax.axios({
            url: config.oauth.accessTokenUrl,
            method: 'POST',
            data: {
                code: code,
                'redirect_uri': uri,
                grant_type: 'authorization_code',
            },
            headers: headers,
            transformRequest: [(data) => Qs.stringify(data)]
        }).then(res=>{
            this[setCookies](res, options);
        }).catch(err=>{
            options.error(err);
        })
    }

    /**
     * 刷新token
     * @param options
     */
    refreshToken(options){
        const refreshToken = (options.refreshToken) ? options.refreshToken : config.token.refreshToken;
        const str =  config.appId + ":" + config.appSecret;
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
            this[setCookies](res, options);
        }).catch(err => {
            options.error(err);
        })
    }

    /**
     * 判断是否授权
     */
    isAuthorize(){
        return (config.token.accessToken !== undefined)
    }

    [setCookies](res, options){
        if (res.data === undefined){
            options.error({code:'200106', message:'授权失败'});
            return;
        }
        config.token = res.data;
        Cookies.set('ztt', res.data.accessToken, { expires: res.data.accessExpire, path: '' });
        Cookies.set('ztr', res.data.refreshToken, { expires: res.data.refreshExpire, path: '' });
        options.success(config.token);
    }

}
