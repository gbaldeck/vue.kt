import Vue from 'vue'
import VueRouter from 'vue-router'
import VueResource from 'vue-resource'
import Vuex from 'vuex';
import App from './App'
// import router from './router'

import './components'
import './directives'
import './filters'
import routes from './routes'
import Communicator from './communicator'

Vue.config.productionTip = false

Vue.use(VueRouter)
Vue.use(VueResource)
Vue.use(Vuex)

const router = new VueRouter({
  routes: routes
})

new Vue({
  el: '#app',
  router: router,
  store: () => Communicator.store,
  render: h => h(App)
})
