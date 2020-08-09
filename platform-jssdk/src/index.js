import {config, Scope} from "./config";
import Cookies from 'js-cookie';
import JsSdk from './JsSdk';

export default {

    config: (options)=>{
        config.appId = options.appId
        config.appSecret = options.appSecret
        config.token = {
            accessToken: Cookies.get('ztt'),
            refreshToken: Cookies.get('ztr')
        }
        config.scopes = options.scopes
        return new JsSdk();
    },

    scope: Scope

}
