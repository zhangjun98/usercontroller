import { config, Scope } from "./config";
import Cookies from "js-cookie";
import JsSdk from "./JsSdk";

export default {
  /**
   * 配置
   */
  config: options => {
    config.appId = options.appId;
    config.appSecret = options.appSecret;
    config.logoutRedirect = options.logoutRedirect || config.logoutRedirect;
    Object.assign(config.token, {
      accessToken: Cookies.get("ztt"),
      refreshToken: Cookies.get("ztr")
    });
    config.scopes = options.scopes;
    return new JsSdk();
  },

  /**
   * 权限范围
   */
  scope: Scope
};
