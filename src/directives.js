import Vue from "vue";
import Communicator from "communicator"


const highlightDirective = {}
Communicator.setDirectiveReceiver('highlight', highlightDirective)
Vue.directive('highlight',highlightDirective);
