/* eslint-disable */
import nodeResolve from 'rollup-plugin-node-resolve'; // 帮助寻找node_modules里的包,运行加载node_modules中的三方模块。
import babel from 'rollup-plugin-babel';              // rollup的babel插件，ES6转ES5,用于支持箭头函数等新语法特性
import replace from 'rollup-plugin-replace';          // 替换待打包文件里的一些变量，如 process在浏览器端是不存在的，需要被替换
import commonjs from 'rollup-plugin-commonjs';        // 使rollup支持CommonJS规范开发的模块,将CommonJS模块转换成ES6，防止他们在Rollup中失效
import alias from 'rollup-plugin-alias';              // alias插件提供了为模块起别名的功能，用过webpack的小伙伴应该对这个功能非常熟悉
import json from 'rollup-plugin-json';                // 使rollup支持将json文件以模块的方式导入进来使用
import path from 'path';
import { terser } from 'rollup-plugin-terser';

const env = process.env.NODE_ENV;
const pathResolve = (p) => path.resolve(__dirname, p);

const config = {
    input: './src/index.js',
    external: [                       // 告诉rollup，不内联打包指定的依赖, 例如:对应import qs from 'qs'语句from后面的值
        'axios'
    ],
    output: [
        {
            file: './lib/jssdk.cjs.js',
            format: 'cjs',                // amd / es / iife / umd / cjs
            sourcemap: true
        },
        {
            format: 'es',                 // ES6模块规范,是未来的主流规范
            file: './lib/jssdk.es.js',
            sourcemap: true
        },
        {
            format: 'umd',                // 输出umd规范的定义，即同时支持amd，cjs 和 iife规范
            file: './lib/jssdk.umd.js',     // 输出文件
            sourcemap: true,              // 生成.map.js文件，方便chrome调试
            name: 'JsSdk',            // 当format为iife和umd时必须提供，将作为全局变量挂在window(浏览器环境)下：window.ztemap_sso=...
            globals: {                    // 用于umd/iife打包时才需要配置, 这跟external 是配套使用的，指明global.Qs即是外部依赖的qs
                axios: 'axios'
            }
        }
    ],
    plugins: [
        nodeResolve(),
        babel({
            exclude: '**/node_modules/**'
        }),
        replace({
            exclude: '**/node_modules/**',
            'process.env.NODE_ENV': JSON.stringify(env)
        }),
        commonjs(),
        json(),
        alias({
            '@': pathResolve('src')
        }),
        terser({
            sourcemap: true
        })
    ]
};

export default config;