import axios from 'axios'
import qs from 'qs'
import conf from '../common/config'

export default {
    submitLogin(data) {
        return axios.post(conf.loginUrl, qs.stringify(data), {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        })
    }
}
