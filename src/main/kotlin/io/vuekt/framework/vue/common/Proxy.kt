package io.vuekt.framework.vue.common

/**
 * Created by gbaldeck on 6/28/2017.
 */
fun newProxy(target: dynamic, handler: ProxyHandler){
  console.log("handler backing obj: ", handler.backingObject)
  console.log("target: ", target)
  return js("new Proxy(target, handler.backingObject)")
}

class ProxyHandler{
  val backingObject = newObject()

  var get: ((target: dynamic, property: String, receiver: dynamic) -> dynamic)?
    get() = backingObject["get"]
    set(block){
      backingObject["get"] = block
    }

  var set: ((target: dynamic, property: String, value: dynamic, receiver: dynamic) -> Boolean)?
    get() = backingObject["set"]
    set(block){
      backingObject["set"] = block
    }

  var construct: ((target: dynamic, argumentsList: Array<dynamic>, newTarget: dynamic) -> Any)?
    get() = backingObject["construct"]
    set(block){
      backingObject["construct"] = block
    }

  var apply: ((target: dynamic, thisArg: dynamic, argumentsList: Array<dynamic>) -> dynamic)?
    get() = backingObject["apply"]
    set(block){
      backingObject["apply"] = block
    }
}