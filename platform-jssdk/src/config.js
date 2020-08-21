import Cookies from "js-cookie";

/**
 * 配置参数
 */
export const config = {
  /**
   * appId
   */
  appId: "",

  /**
   * appSecret
   */
  appSecret: "",

  logoutRedirect: "http://58.220.249.165:21501/sso",

  scopes: [],

  oauth: {
    baseURL: "http://58.220.249.165:21501",

    authorizeUrl: "/sso/oauth/authorize",

    accessTokenUrl: "/sso/oauth/token",

    logoutUrl: "/sso/logout"
  },

  open: {
    baseURL: "http://58.220.249.165:21500",

    userInfoUrl: "/api/user/info",

    userMobile: "/api/user/mobile"
  },

  ws: {
    url: "http://localhost:8080/ws",

    subscribes: {
      chat: "/user/topic/chat",
      notice: "/user/topic/notice"
    }
  },

  /**
   * 授权或的token
   */
  token: {
    get accessToken() {
      return Cookies.get("ztt");
    },
    set accessToken(val) {},
    get refreshToken() {
      return Cookies.get("ztr");
    },
    set refreshToken(val) {}
  }
};

/**
 * 申请权限范围
 */
export const Scope = {
  USER_INFO: "userInfo",

  MOBILE: "mobile",

  MESSAGE_NOTICE: "messageNotice",

  MESSAGE_CHAT: "messageChat"
};
