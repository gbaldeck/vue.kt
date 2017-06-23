package com.github.vuekt

import com.github.vuekt.external.Object

/**
 * Created by gbaldeck on 6/23/2017.
 */
open class VueCollection<in K, V>{
  companion object {
    inline fun <reified T: VueCollection<K, V>, K, V> create(vararg pairs: Pair<K, V>): T{
      val obj = T::class.createInstance()
      pairs.forEach{
        (key, value) ->
        obj.set(key, value)
      }
      return obj
    }
  }

  val backingObject: dynamic = js("new Object()")

  open operator fun get(key: K): V{
    return backingObject[key]
  }
  open operator fun set(key: K, value: V){
    backingObject[key] = value
  }
}

fun <K, V> VueCollection<K, V>.forEach(keyVal: (Pair<K, V>) -> Unit){
  val keys = Object.keys(backingObject) as Array<K>
  keys.forEach {
    keyVal(Pair(it, backingObject[it]))
  }
}

fun VueCollection<*, *>.isEmpty(): Boolean{
  val keys = Object.keys(backingObject) as Array<*>
  return keys.isEmpty()
}

fun VueCollection<*, *>.isNotEmpty(): Boolean{
  val keys = Object.keys(backingObject) as Array<*>
  return keys.isNotEmpty()
}