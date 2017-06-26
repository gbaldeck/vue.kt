package com.github.vuekt.core

import com.github.vuekt.common.ObjectHelper
import com.github.vuekt.common.createInstance
import com.github.vuekt.common.isNotNullOrUndefined
import com.github.vuekt.common.newObject
import com.github.vuekt.external.Object

/**
 * Created by gbaldeck on 6/23/2017.
 */
open class VueCollection<in K, V>(backingObj: dynamic = newObject()){
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

  var backingObject: dynamic = backingObj
    private set

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

fun VueCollection<*, *>.finalizeBackingObject(backingObj: dynamic = backingObject) {
  ObjectHelper.forEach<String, dynamic>(backingObj) {
    (key, value) ->
    if(isNotNullOrUndefined(value.backingObject)){
      finalizeBackingObject(value.backingObject)
      backingObj[key] = value.backingObject
    }
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