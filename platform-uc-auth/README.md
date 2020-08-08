# 统一认证

## 使用OAuth2.0认证

### 获取授权码

#### 第一步请求授权码

```
http://**.**.**/oauth/authorize?response_type=code&client_id={client_id}&redirect_uri={redirect_uri}/&scope={scope}
```
##### 提交方式
[GET]

##### 参数
- response_type 相应类型
- client_id     应用编号
- redirect_uri  跳转地址
- scope         授权范围

#### 第二步登录授权回返授权码
##### 获取

#### 第三步通过授权码获取令牌
```
http://**.**.**/oauth/token
```
##### 提交方式
[POST]
##### 参数
- client_secret 应用密钥
- client_id     应用编号
- grant_type    授权类型（authorization_code）
- code          授权码
- redirect_uri  回调地址


### 获取用户信息
```
http://**.**.**/api/user/info
```
##### 提交方式
[GET]
##### Header
| header|value|
|--|--|
|Authorization| Bearer {{accessToken}}|


http://localhost:8080/sso/oauth/authorize?response_type=code&client_id=123456&redirect_uri=http://localhost:63342/platform-uc/platform-jssdk/examples/demo.html
