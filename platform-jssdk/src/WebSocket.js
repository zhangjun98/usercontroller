import SockJS from  'sockjs-client';
import  Stomp from 'stompjs';
import {config, Scope} from "./config";

const subscribe = Symbol('subscribe');

const send = Symbol('send');

export default class WebSocket {

    constructor(options) {
        this.connect(options);
    }

    connect(options) {
        // 建立连接对象
        const socket = new SockJS(config.ws.url);
        // 获取STOMP子协议的客户端对象
        this.stompClient = Stomp.over(socket);
        // 定义客户端的认证信息,按需求配置
        const headers = {
            Authorization: 'Bearer ' + config.token.accessToken
        }
        // 向服务器发起websocket连接
        this.stompClient.connect(headers, options.success, options.error);
    }


    [Scope.MESSAGE_NOTICE](options){
        this[subscribe]('/user/topic/notice', options)
    }

    [Scope.MESSAGE_CHAT](options){
        if (options.subscribe){
            this[subscribe]('/user/topic/chat', options)
        } else if(options.send){
            this[send]('/chat', options);
        }
    }

    [subscribe](destination, options) {
        // 订阅服务端提供的某个topic
        this.stompClient.subscribe(destination, options.callback, headers);
    }

    /**
     * 发送信息
     */
    [send](destination, options) {
        this.stompClient.send(destination, headers, options.data);
    }

    /**
     * 连接 后台
     */
    disconnect() {
        if (this.stompClient) {
            this.stompClient.disconnect();
        }
    }

}