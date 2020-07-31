<template>
    <div id="login">
        <div class="containers inner">
            <div class="wordCloudBoxNew">
                <a class="dataToMoney">大数据资源</a>
                <div id="cloudBox">
                    <a>社交与征信</a>
                    <a>户籍、生育</a>
                    <a>文娱、音乐</a>
                    <a>健康医疗</a>
                    <a>住房、民生</a>
                    <a>影视</a>
                    <a>安全监督</a>
                    <a>人才、就业</a>
                    <a>采购与物流</a>
                    <a>经典音乐</a>
                    <a>智慧生活</a>
                    <a>知识产权</a>
                    <a>贸易进出口</a>
                    <a>科研应用</a>
                    <a>政府数据</a>
                    <a>金融商贸</a>
                </div>
            </div>
            <div class="loginContent">
                <div class="contentBox">
                    <div class="plat-title">
                        <img src="../assets/logo.png" />智慧广陵统一登录平台
                    </div>
                    <div class="content">
                        <el-form ref="myForm" :model="model" :rules="rules">
                            <el-form-item prop="username">
                                <el-input v-model="model.username" placeholder="请输入用户名" prefix-icon="el-icon-user" />
                            </el-form-item>
                            <el-form-item prop="password">
                                <el-input
                                    v-model="model.password"
                                    type="password"
                                    placeholder="请输入密码"
                                    prefix-icon="el-icon-lock"
                                />
                            </el-form-item>

                            <el-row :gutter="20">
                                <el-col :span="12">
                                    <el-form-item prop="remember">
                                        <el-checkbox v-model="model.remember">七天之内自动登录</el-checkbox>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12" class="forget-pwd">
                                    <el-button type="text">忘记密码</el-button>
                                </el-col>
                            </el-row>
                            <el-form-item>
                                <el-button class="login-btn" type="primary" @click="submitLogin">立即登录</el-button>
                                <span class="err-msg">{{ errorMessage }}</span>
                            </el-form-item>
                        </el-form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { getEncryptCode } from '../utils/login'
import api from '../api/login'

const publicKey = 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEbxUXjnRh2m1k0M1XHfNEj3WaNnhHTxit1VTW0fbGbX3MfuNPwRr86Slo7tE4RLwM5tuMVXG9QFQVzTYj6xG2hZbx3rSniGF5IXAfp/VoPH5W//XFepXdU2HVxX1tyyE1BsABEg2J15x5uAuNv4jqX11PINdStRwmV2AHMo3CewIDAQAB'

export default {
    data() {
        return {
            kdown: null,
            errorMessage: null,
            model: {
                username: null,
                password: null,
                remember: false
            },
            rules: {
                username: [
                    { required: true, message: '请填写用户名', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请填写密码', trigger: 'blur' }
                ]
            }
        }
    },
    watch: {
        model: {
            handler() {
                this.errorMessage = null
            },
            deep: true
        }
    },
    created() {
        this.animate()
        this.kdown = document.onkeydown
        const thiz = this
        document.onkeydown = function(e) {
            const key = window.event.keyCode
            if (key === 13) {
                thiz.submitLogin()
            }
        }
    },
    beforeDestroy() {
        document.onkeydown = this.kdown
    },
    methods: {
        submitLogin() {
            this.$refs.myForm.validate((valid) => {
                if (valid) {
                    const decrypt = getEncryptCode(publicKey, this.model.password)
                    console.log(decrypt)
                    api.submitLogin({
                        username: this.model.username,
                        password: this.model.password
                    }).then(res => {
                        if (res.status !== 200) {
                            this.errorMessage = '登录失败！用户名或密码错误'
                        }
                    }).catch(err => {
                        console.trace(err)
                        this.errorMessage = '服务异常！请稍后重试'
                    })
                } else {
                    return false
                }
            })
        },
        // 动画
        animate() {
            let sa, ca, sb, cb, sc, cc
            let per

            const radius = 220
            const dtr = Math.PI / 180
            const d = 300

            const mcList = []
            let active = false
            let lasta = 1
            let lastb = 1
            const distr = true
            const tspeed = 10
            const size = 250

            let mouseX = 0
            let mouseY = 0

            const howElliptical = 1

            let aA = null
            let oDiv = null

            function depthSort() {
                let i = 0
                const aTmp = []

                for (i = 0; i < aA.length; i++) {
                    aTmp.push(aA[i])
                }

                aTmp.sort(function(vItem1, vItem2) {
                    if (vItem1.cz > vItem2.cz) {
                        return -1
                    } else if (vItem1.cz < vItem2.cz) {
                        return 1
                    }
                    return 0
                })

                for (i = 0; i < aTmp.length; i++) {
                    aTmp[i].style.zIndex = i
                }
            }

            function positionAll() {
                let phi = 0
                let theta = 0
                const max = mcList.length
                let i = 0

                const aTmp = []
                const oFragment = document.createDocumentFragment()

                for (i = 0; i < aA.length; i++) {
                    aTmp.push(aA[i])
                }

                aTmp.sort(function() {
                    return Math.random() < 0.5 ? 1 : -1
                })

                for (i = 0; i < aTmp.length; i++) {
                    oFragment.appendChild(aTmp[i])
                }

                oDiv.appendChild(oFragment)

                for (let i = 1; i < max + 1; i++) {
                    if (distr) {
                        phi = Math.acos(-1 + (2 * i - 1) / max)
                        theta = Math.sqrt(max * Math.PI) * phi
                    } else {
                        phi = Math.random() * Math.PI
                        theta = Math.random() * (2 * Math.PI)
                    }
                    mcList[i - 1].cx = radius * Math.cos(theta) * Math.sin(phi)
                    mcList[i - 1].cy = radius * Math.sin(theta) * Math.sin(phi)
                    mcList[i - 1].cz = radius * Math.cos(phi)

                    aA[i - 1].style.left =
                        mcList[i - 1].cx +
                        oDiv.offsetWidth / 2 -
                        mcList[i - 1].offsetWidth / 2 +
                        'px'
                    aA[i - 1].style.top =
                        mcList[i - 1].cy +
                        oDiv.offsetHeight / 2 -
                        mcList[i - 1].offsetHeight / 2 +
                        'px'
                }
            }

            function doPosition() {
                const l = oDiv.offsetWidth / 2
                const t = oDiv.offsetHeight / 2
                for (let i = 0; i < mcList.length; i++) {
                    aA[i].style.left =
                        mcList[i].cx + l - mcList[i].offsetWidth / 2 + 'px'
                    aA[i].style.top =
                        mcList[i].cy + t - mcList[i].offsetHeight / 2 + 'px'

                    aA[i].style.fontSize =
                        Math.ceil((12 * mcList[i].scale) / 2) + 8 + 'px'

                    aA[i].style.filter = 'alpha(opacity=' + 100 * mcList[i].alpha + ')'
                    aA[i].style.opacity = mcList[i].alpha
                }
            }

            function sineCosine(a, b, c) {
                sa = Math.sin(a * dtr)
                ca = Math.cos(a * dtr)
                sb = Math.sin(b * dtr)
                cb = Math.cos(b * dtr)
                sc = Math.sin(c * dtr)
                cc = Math.cos(c * dtr)
            }

            function update() {
                let a
                let b

                if (active) {
                    a = (-Math.min(Math.max(-mouseY, -size), size) / radius) * tspeed
                    b = (Math.min(Math.max(-mouseX, -size), size) / radius) * tspeed
                } else {
                    a = lasta * 0.98
                    b = lastb * 0.98
                }

                lasta = a
                lastb = b

                if (Math.abs(a) <= 0.01 && Math.abs(b) <= 0.01) {
                    return
                }

                const c = 0
                sineCosine(a, b, c)
                for (let j = 0; j < mcList.length; j++) {
                    const rx1 = mcList[j].cx
                    const ry1 = mcList[j].cy * ca + mcList[j].cz * -sa
                    const rz1 = mcList[j].cy * sa + mcList[j].cz * ca

                    const rx2 = rx1 * cb + rz1 * sb
                    const ry2 = ry1
                    const rz2 = rx1 * -sb + rz1 * cb

                    const rx3 = rx2 * cc + ry2 * -sc
                    const ry3 = rx2 * sc + ry2 * cc
                    const rz3 = rz2

                    mcList[j].cx = rx3
                    mcList[j].cy = ry3
                    mcList[j].cz = rz3

                    per = d / (d + rz3)

                    mcList[j].x = howElliptical * rx3 * per - howElliptical * 2
                    mcList[j].y = ry3 * per
                    mcList[j].scale = per
                    mcList[j].alpha = per

                    mcList[j].alpha = (mcList[j].alpha - 0.6) * (10 / 6)
                }

                doPosition()
                depthSort()
            }

            window.onload = function() {
                let i = 0
                let oTag = null

                oDiv = document.getElementById('cloudBox')

                aA = oDiv.getElementsByTagName('a')

                for (i = 0; i < aA.length; i++) {
                    oTag = {}

                    oTag.offsetWidth = aA[i].offsetWidth
                    oTag.offsetHeight = aA[i].offsetHeight

                    mcList.push(oTag)
                }

                sineCosine(0, 0, 0)

                positionAll()

                oDiv.onmouseover = function() {
                    active = true
                }

                oDiv.onmouseout = function() {
                    active = false
                }

                oDiv.onmousemove = function(ev) {
                    const oEvent = window.event || ev
                    mouseX = oEvent.clientX - (oDiv.offsetLeft + oDiv.offsetWidth / 2)
                    mouseY = oEvent.clientY - (oDiv.offsetTop + oDiv.offsetHeight / 2)
                    mouseX /= 5
                    mouseY /= 5
                }
                setInterval(update, 70)
                setInterval(positionAll, 5000)
            }
        }
    }
}
</script>

<style scoped lang='scss'>
#login {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: url(../assets/bgLogin.png) 0 0 no-repeat;

  .inner {
    display: flex;
    height: 100%;
    justify-content: space-evenly;
    align-items: center;

    .wordCloudBoxNew {
      width: 591px;
      height: 591px;
      background: url(../assets/registerPic.png) no-repeat left center;
      position: relative;
      a.dataToMoney {
        display: block;
        width: 132px;
        height: 46px;
        line-height: 46px;
        text-align: center;
        font-size: 24px;
        position: absolute;
        top: 273px;
        left: 217px;
        //background-color: #fff;
        color: #ffffff;
      }
      #cloudBox {
        position: relative;
        width: 566px;
        height: 566px;
        a {
          position: absolute;
          top: 0px;
          left: 0px;
          color: #fff;
          text-decoration: none;
          padding: 3px 20px;
          font-size: 12px;
        }
      }
    }

    .loginContent {
      width: 457px;
      background: rgba(255, 255, 255, 0.4);
      border-radius: 8px;
      margin-left: 100px;
      padding: 10px;

      .contentBox {
        // width: 100%;
        // height: 100%;
        background-color: #f8f8f8;
        border-radius: 8px;
        padding: 20px;

        .plat-title {
          text-align: center;
          margin-bottom: 10px;
          font-size: 16pt;
          color: #0f8fdb;
          font-family: -apple-system, BlinkMacSystemFont, Segoe UI, Roboto,
            Helvetica Neue, Arial, sans-serif;
          line-height: 60px;
          img {
            width: 51px;
            height: 36px;
            margin-right: 20px;
          }
        }

        .content {
          // height: 340px;
          // width: 100%;
          background-color: #fff;
          padding: 40px;
          position: relative;
          border-radius: 8px;
          text-align: left;
          .err-msg{
            display: inline-block;
            color:#dc3545;
            position: absolute;
            left: 0;
            top: 40px;
          }
          .forget-pwd {
            text-align: right;
          }
          .login-btn {
            width: 100%;
          }
          input.userName {
            padding-left: 34px;
            background: url(../assets/login_user_03.png) 7px 8px no-repeat;
          }

          input.pwd {
            padding-left: 34px;
            background: url(../assets/0_1_Login_password_03.jpg) 8px 7px
              no-repeat;
            & + div {
              height: 20px;
              line-height: 35px;
            }
          }

          .other {
            height: 30px;
            margin-top: 15px;
            span {
              line-height: 30px;
              margin-left: 7px;
              vertical-align: middle;
            }
            i.checkBox {
              width: 16px;
              height: 16px;
              border: 1px solid #999999;
              border-radius: 2px;
              display: inline-block;
              margin-top: 7px;
              vertical-align: top;
              cursor: pointer;
              &.active {
                border: 1px solid #409eff;
                background: url(../assets/0_1_Login_yes_03.jpg) no-repeat center
                  4px;
              }
            }

            a.wjmm {
              color: #409eff;
              cursor: pointer;
              float: right;
              line-height: 30px;
            }
          }
          .flex {
            display: flex;
            justify-content: space-between;
          }
        }
      }
    }
  }
}
</style>
