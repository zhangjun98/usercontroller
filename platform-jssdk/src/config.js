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

        baseURL: 'http://58.220.249.165:21501',

        authorizeUrl: '/sso/oauth/authorize',

        accessTokenUrl: '/sso/oauth/token',

    },

    open: {

        baseURL: 'http://58.220.249.165:21500',

        userInfoUrl: '/api/user/info',

        userMobile: '/api/user/mobile'
    },

    ws:{
        url: 'http://localhost:8080/ws',

        subscribes: {
            chat: '/user/topic/chat',
            notice: '/user/topic/notice'
        }

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

    MOBILE: 'mobile',

    MESSAGE_NOTICE: 'messageNotice',

    MESSAGE_CHAT: 'messageChat',

};