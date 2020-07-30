import JsEncrypt from 'jsencrypt'
import jsrsasign from 'jsrsasign'

export function addUrlParam(uri, key, value) {
	var re = new RegExp('([?&])' + key + '=.*?(&|$)', 'i')
	var separator = uri.indexOf('?') !== -1 ? '&' : '?'
	if (uri.match(re)) {
		return uri.replace(re, '$1' + key + '=' + value + '$2')
	} else {
		return uri + separator + key + '=' + value
	}
}

export function getQueryVariable(variable) {
    var query = window.location.search.substring(1)
    var vars = query.split('&')
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split('=')
        if (pair[0] === variable) { return pair[1] }
    }
    return
}

export function urlParse() {
    const url = window.location.search
    const obj = {}
    const reg = /[?&][^?&]+=[^?&]+/g
    const arr = url.match(reg)
    if (arr) {
        arr.forEach((item) => {
            const tempArr = item.substring(1).split('=')
            const key = decodeURIComponent(tempArr[0])
            const val = decodeURIComponent(tempArr[1])
            obj[key] = val
        })
    }
    return obj
}

/**
 * 加密
 * @param password 需加密内容
 */
export function getEncryptCode(publicKey, data) {
    const encrypt = new JsEncrypt()
    encrypt.setPublicKey(publicKey)
    return encrypt.encrypt(data)
}

/**
 * 获取签名
 * @param password
 * @returns {*}
 */
export function getSignCode(privateKey, data) {
    // 私钥加开头和结尾
    privateKey = `-----BEGIN PRIVATE KEY-----${privateKey}-----END PRIVATE KEY-----`
    const rsa = jsrsasign.KEYUTIL.getKey(privateKey)
    // MD5withRSA 签名算法，要与后端一致
    const sig = new jsrsasign.KJUR.crypto.Signature({ 'alg': 'MD5withRSA', 'prov': 'cryptojs/jsrsa' })
    sig.init(rsa)
    sig.updateString(data)
    return jsrsasign.hextob64(sig.sign())
}
