package com.github.vuekt

/**
 * Created by gbaldeck on 6/22/2017.
 */
fun jsObjectOf(vararg pairs: Pair<String, dynamic>): Any{
  val obj: dynamic = Any()
  pairs.forEach {
    (key, value) ->
    obj[key] = value
  }
  return obj
}

fun jsObjectOf(map: Map<String, dynamic>): Any = jsObjectOf(*map.toList().toTypedArray())

fun Any._get(key: dynamic): dynamic {
  val self: dynamic = this
  return self[key]
}

fun Any._set(key: dynamic, value: dynamic){
  val self: dynamic = this
  self[key] = value
}