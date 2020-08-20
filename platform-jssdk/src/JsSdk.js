import OpenApi from "./OpenApi";
import OAuth from "./OAuth";
import { config, Scope } from "./config";
import WebSocket from "./WebSocket";

export default class JsSdk {
  constructor() {
    this.oauth = new OAuth();
    this.openApi = new OpenApi();
  }

  /**
   * 出发事件
   */
  call(scope, options) {
    if (!config.scopes.includes(scope)) {
      options.error({ code: "200016", message: "无权访问" });
      return;
    }

    /**
     * 开放接口
     */
    if (this.openApi.__proto__.hasOwnProperty(scope)) {
      this.openApi[scope](options);
      return;
    }

    /**
     * 消息
     */
    if (scope === Scope.MESSAGE_NOTICE || scope === Scope.MESSAGE_CHAT) {
      // if (!this.ws) this.ws = new WebSocket(options);
      // this.ws[scope](scope, options);
      // return;
    }

    options.error({ code: "900002", message: "此功能正在开发中，敬请期待" });
  }

  destroy() {
    if (this.ws) {
      this.ws.disconnect();
    }
  }
}
