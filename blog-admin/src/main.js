import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import Resource from 'vue-resource';
import VueRouter from 'vue-router';
import Vuex from 'vuex';
import NProgress from 'nprogress'; //页面顶部进度条
import 'nprogress/nprogress.css';

import util from './common/util';
import store from './vuex/store';

import App from './App';
import Home from './components/Home.vue';


Vue.use(ElementUI);
Vue.use(VueRouter);
Vue.use(Vuex);
Vue.use(Resource);

NProgress.start();

util.get("/menu/list", null, result => {
    var routes = [{
        path: '/',
        redirect: '/main',
        component: Home,
        name: '控制台',
        hidden: true,
        children: [{
            path: '/main',
            name: '控制台',
            component: require('./components/Main.vue')
        }]
    }];

    if (result.errCode === 0) {
        var routeArr = loadMenu(0, result.data);
        console.log(routeArr);
        routeArr.forEach(route => {
            routes.push(route);
        });
    } else {
        this.$message.error(result.msg);
    }

    const router = new VueRouter({
        routes
    });

    router.beforeEach((to, from, next) => {
        NProgress.start();
        next();
    });

    router.afterEach(transition => {
        NProgress.done();
    });

    const app = new Vue({
        el: '#app',
        template: '<App/>',
        router,
        store,
        components: {
            App
        }
    }).$mount('#app')
});

//加载菜单
function loadMenu(pid, datas) {
    let menus = [];
    datas.forEach(data => {
        if (data.pid === pid) {
            var menu = {};
            if (data.pid === 0) {
                if (data.path) {
                    menus.push(leafMenu(data));
                    return;
                }
                menu.path = '/';
                menu.component = Home;
            } else {
                menu.path = data.path;
                menu.component = require('./components' + data.page);
            }
            menu.name = data.name;
            menu.iconCls = data.iconCls;
            menu.hidden = data.hidden;
            menu.children = loadMenu(data.id, datas);
            menus.push(menu);
        }
    });
    return menus;
}

// 处理只有一级的菜单
function leafMenu(data) {
    var menu = {};
    menu.path = '/';
    menu.component = Home;
    menu.hidden = data.hidden;
    menu.name = data.name;
    menu.leaf = true;
    menu.children = [{
        path: data.path,
        name: data.name,
        component: require('./components' + data.page),
        iconCls: data.iconCls,
        hidden: data.hidden,
        children: []
    }];
    return menu;
}