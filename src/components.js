import Vue from "vue";
import Communicator from "communicator"

export const subComponent = Vue.component('sub-component', function (resolve, reject) {
  Communicator.setComponentResolver("sub-component",resolve)
});

export const testComponent = Vue.component('test-component', function (resolve, reject) {
  Communicator.setComponentResolver("test-component",resolve)
});
