package com.github.vuekt.external

import com.github.vuekt.common.newObject

/**
 * Created by gbaldeck on 6/28/2017.
 */
@JsModule("proxy-polyfill/proxy.min")
@JsNonModule
external val Proxy_ext:dynamic

object Proxy{
  operator fun invoke(target: dynamic, handler: ProxyHandler){
    val proxy = Proxy_ext
    return js("new proxy(target, handler.backingObject)")
  }
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