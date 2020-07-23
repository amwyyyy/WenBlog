import Vue from 'vue';
import App from './LoginApp';
import 'element-ui/lib/theme-chalk/index.css';
import VueRouter from 'vue-router';
import Resource from 'vue-resource';

import Login from './Login.vue';
import {Button, Checkbox, Form, FormItem, Input} from 'element-ui'

Vue.use(VueRouter);
Vue.use(Resource);

Vue.component(Form.name, Form)
Vue.component(Button.name, Button)
Vue.component(FormItem.name, FormItem)
Vue.component(Input.name, Input)
Vue.component(Checkbox.name, Checkbox)


var routes = [{
    path: '/',
    component: Login,
}];

const router = new VueRouter({
    routes
});

new Vue({
    el: '#loginApp',
    template: '<App/>',
    router,
    components: {
        App
    }
}).$mount('#loginApp');