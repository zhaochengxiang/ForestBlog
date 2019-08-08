import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from './utils/http.js'
import './utils/filter_utils.js'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

Vue.prototype.$http = axios 
Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.use(mavonEditor)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
