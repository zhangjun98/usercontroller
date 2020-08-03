module.exports = {
    'env': {
        'browser': true,
        'es6': true
    },
    'extends': [
        'plugin:prettier/recommended'
    ],
    "plugins": ["prettier"],
    'globals': {
        'Atomics': 'readonly',
        'SharedArrayBuffer': 'readonly'
    },
    'parserOptions': {
        'ecmaVersion': 2018,
        'sourceType': 'module'
    },
    /**
     *   off 或 0 - 关闭规则
     *  warn 或 1 - 开启规则(警告)
     * error 或 2 - 开启规则(错误)
     */
    'rules': {
        "prettier/prettier": "error",
        "no-console": process.env.NODE_ENV === "production" ? "error" : "off",
        "no-debugger": process.env.NODE_ENV === "production" ? "error" : "off",
        "linebreak-style": ["off", "windows"]
    }
}