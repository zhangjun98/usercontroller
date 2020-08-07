import OpenApi from "./OpenApi";
import OAuth from "./OAuth";
import {config} from './config'

export default class JsSdk{

    constructor() {
        this.oauth = new OAuth();
        this.openApi = new OpenApi();
    }

    /**
     * 出发事件
     */
    call(scope, options) {
        if (config.scopes.includes(scope)){
            this.openApi[scope](options);
            return;
        }
        options.error({code:'200016', message:'无权访问'});
    }

}
