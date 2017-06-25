@file:Suppress("UNCHECKED_CAST")
package com.github.vuekt

import kotlin.reflect.KProperty

/**
 * Created by gbaldeck on 6/22/2017.
 */
abstract class VueComponent {
  abstract val templateImport: dynamic
  abstract val el: String
  private val methods = VueCollection<String, Function<*>>()
  private val data = VueCollection<String, dynamic>()
  var self: dynamic = undefined
    private set

  open fun created() {}

  protected inner class Data<T> {
    operator fun getValue(thisRef: Any, property: KProperty<*>): T = data[property.name]

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
      data[property.name] = value
    }
  }

  protected inner class Method<T: Function<*>> {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
      return methods[property.name] as T
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
      methods[property.name] = value
    }
  }

//  class Methods(backingObj: dynamic = newObject()) : VueCollection<String, Function<*>>(backingObj) {
//    override operator fun get(key: String): Function<*> = super.get(key)
//    override operator fun set(key: String, value: Function<*>) {
//      super.set(key, value)
//    }
//  }
//
//  fun methodsOf(vararg methods: Pair<String, Function<*>>): Methods {
//    return VueCollection.create(*methods)
//  }
//
//
//  fun <T: Any> methodsOf(methods: T.() -> Unit): Methods{
//    val tempObj = newObject()
//    val methodsDynamic: dynamic = methods
//
//    methodsDynamic(tempObj)
//    val methodsObj = Methods(tempObj)
//
//    return methodsObj
//  }
//
//  class Data : VueCollection<String, dynamic>() {
//    override operator fun get(key: String): dynamic = super.get(key)
//    override operator fun set(key: String, value: dynamic) {
//      super.set(key, value)
//    }
//  }
//
//  fun dataOf(vararg data: Pair<String, dynamic>): Data {
//    return VueCollection.create(*data)
//  }
//
//  fun dataFunOf(vararg data: Pair<String, dynamic>): () -> Data {
//    return { dataOf(*data) }
//  }

  open internal fun getActual(): dynamic{
    val actual = newObject()
    actual.created = {
      self = js("this")
      created()
    }

//    methods.forEach {
//      (key, value) ->
//      val dynamicVal: dynamic = value
//      console.log({ dynamicVal(js("this")) })
//    }

//    val dynamicData: dynamic = data
    actual.data = { data.backingObject }

    actual.methods = methods.backingObject
    templateImport(actual)

    return actual
  }
}