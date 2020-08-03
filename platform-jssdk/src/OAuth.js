


export default class OAuth {

    constructor(options) {

    }

    /**
     * 跳转授权页面
     */
    generateOAuthUrl(redirectUrl, scope, state) {
        let url = this.oAuthUrl;
        const tempObj = {
            appid: this.wechatConfig.appId,
        };
        const oauthState = state || this.wechatConfig.oAuthState || 'userAuth';
        const tempOAuthParams = Object.assign(tempObj, oAuthDefaultParams, {
            redirect_uri: redirectUrl,
            state: oauthState,
        });
        tempOAuthParams.scope = scope
            ? scope
            : /* istanbul ignore next  */ oauthScope.USER_INFO;

        const keys = Object.keys(tempOAuthParams);
        //sort the keys for correct order on url query
        keys.sort();
        const oauthParams = {};
        keys.forEach(key => (oauthParams[key] = tempOAuthParams[key]));

        url += qs.stringify(oauthParams);
        url += REDIRECT_HASH;
        return url;
    }

}