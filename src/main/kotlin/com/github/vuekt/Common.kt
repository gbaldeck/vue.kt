package com.github.vuekt

import com.github.vuekt.external.Object

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

object ObjectHelper {
  fun <K, V> forEach(obj: dynamic, keyVal: (Pair<K, V>) -> Unit) {
    val keys = Object.keys(obj) as Array<K>
    keys.forEach {
      keyVal(Pair(it, obj[it]))
    }
  }
}

fun newObject(): dynamic = js("new Object()")