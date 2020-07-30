import Vue from 'vue'
import App from './App.vue'
import conf from './common/config'

Vue.config.productionTip = false

import { Button, Input, Form, FormItem, Checkbox, Row, Col } from 'element-ui'
Vue.use(Button)
Vue.use(Input)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Checkbox)
Vue.use(Row)
Vue.use(Col)

Vue.prototype.$conf = conf

new Vue({
  render: h => h(App)
}).$mount('#app')
