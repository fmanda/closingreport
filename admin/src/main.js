import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import VueRouter from 'vue-router'
import routes from './routes'
import App from './App.vue'
import 'font-awesome/css/font-awesome.min.css'
import locale from 'element-ui/lib/locale/lang/en'

import 'devextreme/dist/css/dx.common.css';
import 'devextreme/dist/css/dx.light.compact.css';

var CONFIG = require('../config.json');

Vue.use(ElementUI,  { locale })
Vue.use(VueRouter)

Vue.prototype.$rest_url = CONFIG.rest_url;

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
	if (to.path == '/login') {
		sessionStorage.removeItem('user');
	}
	let user = JSON.parse(sessionStorage.getItem('user'));
	if (!user && to.path != '/login' && to.path != '/404')  {
		next({ path: '/login' })
	} else {
		next()
	}
})


new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
