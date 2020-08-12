## 智慧管理 JSSDK 

### npm 安装包
```
npm install platform-jssdk --registry http://58.220.249.165:4873
```

### 使用JSSDK授权

#### JsSdk.scope 授权范围

- (`USER_INFO`) 获取用户信息

- (`MOBILE`) 获取用户用户手机号

#### 配置JsSdk

- appId: 授权应用编号
- appSecret: 授权应用密钥
- scopes: 授权范围（自定授权可以获取用户信息等）
```
const jsSdk = JsSdk.config({
    appId:{appId},
    appSecret: {appSecret},
    scopes: [JsSdk.scope.USER_INFO, JsSdk.scope.MOBILE],
});
```
#### 授权码授权并且获取token
```
jssdk.oauth.authorize({
    success:function (data) {
        console.log(data)
    },
    error:function (err) {
        console.log(err)
    }
});
```
#### 获取授权范围的数据
- scope 指定授权范围
```
jsSdk.call({scope}, {
    success:function (data) {
        console.log(data)
    },
    error:function (err) {
        console.log(err)
    }
})
```