import Vue from 'vue'
import Framework7 from 'framework7'
import Framework7Vue from 'framework7-vue'
import App from './App'
// import router from './router'

const Communicator = require("communicator").default

const isAndroid = window.Framework7.prototype.device.android
const link1 = document.createElement("link");
const link2 = document.createElement("link");

if(isAndroid){
  link1.href = '/static/css/framework7.material.min.css';
  link1.rel = "stylesheet";

  link2.href = '/static/css/framework7.material.colors.min.css';
  link2.rel = "stylesheet";
} else {
  link1.href = '/static/css/framework7.ios.min.css';
  link1.rel = "stylesheet";

  link2.href = '/static/css/framework7.ios.colors.min.css';
  link2.rel = "stylesheet";
}
const head = document.getElementsByTagName("head")[0]
head.appendChild(link1);
head.appendChild(link2);

Vue.config.productionTip = false

Vue.use(Framework7Vue)

const highlightDirective = {}
Communicator.setDirectiveReceiver('highlight', highlightDirective)
Vue.directive('highlight',highlightDirective);

Vue.component('sub-component', function (resolve, reject) {
  Communicator.setComponentResolver("sub-component",resolve)
});

Vue.component('test-component', function (resolve, reject) {
  Communicator.setComponentResolver("test-component",resolve)
});



/* eslint-disable no-new */
new Vue({
  el: '#app',
  framework7: {
    root: '#app',
    material: isAndroid,
    materialRipple: isAndroid
  },
  render: h => h(App)
})
