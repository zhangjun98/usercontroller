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

    oauth: {

        baseURL: 'http://localhost:8080',

        authorizeUrl: 'http://localhost:4000/sso/oauth/authorize',

        accessTokenUrl: '/sso/oauth/token',

        userInfoUrl: '/api/user/info'

    }

};