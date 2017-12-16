import Vue from "vue";
import Communicator from "communicator"

Vue.filter("to-lowercase", function(value){
  return Communicator.filters['to-lowercase'](value)
});
