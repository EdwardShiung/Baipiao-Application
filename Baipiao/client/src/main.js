import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import { LMap, LTileLayer, LMarker } from 'vue2-leaflet';


import PaperDashboard from "./plugins/paperDashboard";
import http from '../src/plugins/http'
import axios from "axios";
import 'leaflet/dist/leaflet.css';


Vue.component('l-map', LMap);
Vue.component('l-tile-layer', LTileLayer);
Vue.component('l-marker', LMarker);

Vue.use(PaperDashboard);

Vue.prototype.$http = http;
Vue.prototype.$axios = axios;
Vue.config.productionTip = false


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
