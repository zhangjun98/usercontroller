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

        baseURL: 'http://localhost:4000/oauth',

        authorizeUrl: 'http://localhost:4000/oauth/authorize',

        accessTokenUrl: '/token',

    }

};
