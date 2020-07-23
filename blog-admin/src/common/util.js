import Vue from 'vue';
import { MessageBox, Notification } from 'element-ui';
import config from '../../config';

/**
 * 对象转参数形式  name=value&age=aaa
 */
function objectEncode(param, key, encode) {
    if (param == null) {
        return '';
    }
    var paramStr = '';
    var t = typeof(param);
    if (t == 'string' || t == 'number' || t == 'boolean') {
        paramStr += '&' + key + '=' + ((encode == null || encode) ? encodeURIComponent(param) : param);
    } else {
        for (var i in param) {
            var k = key == null ? i : key + (param instanceof Array ? '[' + i + ']' : '.' + i);
            paramStr += objectEncode(param[i], k, encode);
        }
    }
    return paramStr;
}

// 根据是打包还是开发，判断服务器路径
const uri = process.env.NODE_ENV === 'production' ? config.build.baseUri : config.dev.baseUri;

export default {
    post: (url, data, callback) => {
        Vue.http.options.emulateJSON = true;
        Vue.http.post(uri + url, data).then(
            result => {
                if (result.data.errCode === 10 && url.indexOf('/index/login') === -1) {
                    window.location.href = "/login.html";
                } else if (result.data.errCode === 20) {
                    Notification.error({
                        title: '错误',
                        message: '没有权限'
                    });
                } else {
                    callback(result.data);
                }
            },
            error => {
                if (url.indexOf('/menu/list') !== -1) {
                    window.location.href = "./login.html";
                } else {
                    MessageBox.alert('服务器异常！', '消息', {
                        confirmButtonText: '确定'
                    });
                }
            }
        );
    },
    get: (url, data, callback) => {
        if (url.indexOf('?') === -1) {
            url += '?';
        }
        url += objectEncode(data);

        Vue.http.get(uri + url).then(
            result => {
                if (result.data.errCode === 10 && url.indexOf('/index/login') === -1) {
                    window.location.href = "./login.html";
                } else if (result.data.errCode === 20) {
                    Notification.error({
                        title: '错误',
                        message: '没有权限'
                    });
                } else {
                    callback(result.data);
                }
            },
            error => {
                if (url.indexOf('/menu/list') !== -1) {
                    window.location.href = "./login.html";
                } else {
                    MessageBox.alert('服务器异常！', '消息', {
                        confirmButtonText: '确定'
                    });
                }
            }
        );
    },
    http: (settings) => {
        Vue.http({
            method: settings.method,
            url: uri + settings.url,
            data: settings.data,
            emulateJSON: true
        }).then(result => {
            if (result.data.errCode === 10 && settings.url.indexOf('/index/login') === -1) {
                window.location.href = "./login.html";
            } else if (result.data.errCode === 20) {
                Notification.error({
                    title: '错误',
                    message: '没有权限'
                });
            } else {
                settings.callback(result.data);
            }
        }, error => {
            if (settings.url.indexOf('/menu/list') !== -1) {
                window.location.href = "./login.html";
            } else {
                MessageBox.alert('服务器异常！', '消息', {
                    confirmButtonText: '确定'
                });
            }
        });
    },
    objectEncode: (param, key, encode) => {
        return objectEncode(param, key, encode);
    },
    getQueryStringByName: (name) => {
        var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
        var r = window.location.search.substr(1).match(reg);
        var context = "";
        if (r !== null) {
            context = r[2];
        }
        reg = null;
        r = null;
        return context === null || context === "" || context === 'undefined' ? '' : context;
    },
    //复制属性到对象
    copyProperties: (target, obj) => {
        target = target || {};
        if (obj) {
            for (var key in obj) {
                if (!obj.hasOwnProperty(key)) {
                    continue;
                }

                var value = obj[key];
                if (typeof(value) === 'undefined') {
                    continue;
                }

                target[key] = value;
            }
        }
    },
    //清空对象属性
    clearProperties: (target) => {
        target = target || {};
        for (var key in target) {
            if (!target.hasOwnProperty(key)) {
                continue;
            }

            var val = target[key];
            if (typeof(val) === 'undefined') {
                continue;
            }

            target[key] = null;
        }
    }
};