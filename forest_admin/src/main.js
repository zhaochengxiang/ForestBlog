import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import Cookies from 'js-cookie'
import Element from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from './utils/http.js'
import './utils/filter.js'
// import mavonEditor from 'mavon-editor'
// import 'mavon-editor/dist/css/index.css'

import '@/styles/index.scss' // global css

import './icons' // icon
import './permission' // permission control

Vue.prototype.$http = axios 
Vue.config.productionTip = false;
Vue.use(Element, {
  size: Cookies.get('size') || 'medium' // set element-ui default size
})
// Vue.use(mavonEditor)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
