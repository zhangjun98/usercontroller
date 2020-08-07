/**
 * 配置参数
 */
export const config = {

    /**
     * appId
     */
    appId: '',

    /**
     * appSecret
     */
    appSecret: '',

    scopes: [],

    oauth: {

        baseURL: 'http://localhost:8080',

        authorizeUrl: '/sso/oauth/authorize',

        accessTokenUrl: '/sso/oauth/token',

        userInfoUrl: '/api/user/info'

    },

    /**
     * 授权或的token
     */
    token: {}

};


/**
 * 申请权限范围
 */
export const Scope = {

    USER_INFO: 'userInfo',

    MOBILE: 'mobile'

};