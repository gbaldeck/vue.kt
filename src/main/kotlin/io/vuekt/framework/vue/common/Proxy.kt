package io.vuekt.framework.vue.common

/**
 * Created by gbaldeck on 6/28/2017.
 */
typealias ProxyGet = (target: dynamic, property: String, receiver: dynamic) -> dynamic
typealias ProxySet = (target: dynamic, property: String, value: dynamic, receiver: dynamic) -> Boolean
typealias ProxyConstruct = (target: dynamic, argumentsList: Array<dynamic>, newTarget: dynamic) -> Any
typealias ProxyApply = (target: dynamic, thisArg: dynamic, argumentsList: Array<dynamic>) -> dynamic

fun newProxy(target: dynamic, handler: ProxyHandler): dynamic {
  return js("new Proxy(target, handler.backingObject)")
}

class ProxyHandler{
  val backingObject = newObject()

  var get: ProxyGet?
    get() = backingObject["get"]
    set(block){
      backingObject["get"] = block
    }

  var set: ProxySet?
    get() = backingObject["set"]
    set(block){
      backingObject["set"] = block
    }

  var construct: ProxyConstruct?
    get() = backingObject["construct"]
    set(block){
      backingObject["construct"] = block
    }

  var apply: ProxyApply?
    get() = backingObject["apply"]
    set(block){
      backingObject["apply"] = block
    }
}