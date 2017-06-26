@file:Suppress("UNCHECKED_CAST")
package com.github.vuekt.core

import com.github.vuekt.common.newObject
import com.github.vuekt.common.throwVueKtException
import kotlin.reflect.KProperty

/**
 * Created by gbaldeck on 6/22/2017.
 */
typealias VueData = VueCollection<String, dynamic>

abstract class VueComponent {
  abstract val templateImport: dynamic
  abstract val el: String
  private val data = VueData()
  private val computed = VueCollection<String, ComputedContainer<*>>()
  private val watch = VueCollection<String, Function<Unit>>()
  private val methods = VueCollection<String, Function<*>>()
  protected open var dataFunction: Function<VueData>? = null

  var vueThis: dynamic = undefined
    private set

  open fun created() {}

  protected inner class Data(singleAssign: Boolean = false):
      VueOption<dynamic>(singleAssign, data)

  protected inner class ComputedContainer<T>{
    val backingObject = newObject()

    var getter: Function<T>
      get() = backingObject["get"]
      set(value) { backingObject["get"] = value }

    var setter: (T) -> Unit
      get() = backingObject["set"]
      set(value) { backingObject["set"] = value }
  }

  protected inner class Computed<T>(getter: Function<T>, setter: ((T) -> Unit)? = null, singleAssign: Boolean = false){

  }

  protected inner class Watch<T: Function<Unit>>(property: KProperty<*>, singleAssign: Boolean = false):
      VueOption<T>(singleAssign, watch as VueCollection<String, T>, property)

  protected inner class Method<T: Function<*>>(singleAssign: Boolean = false):
      VueOption<T>(singleAssign, methods as VueCollection<String, T>)




  protected open inner class VueOption<V>(val singleAssign: Boolean,
                                          val storageObj: VueCollection<String, V>,
                                          val property: KProperty<*>? = null) {
    private var assigned = false

    open operator fun getValue(thisRef: Any, propertyLocal: KProperty<*>): V {
      console.log("get is run")
      if(!assigned)
        throwVueKtException("The Vue ${this::class.simpleName?.toLowerCase() ?: ""} property '${propertyLocal.name}' in '${thisRef::class.simpleName}' was never assigned a value.")

      val propName = property?.name ?: propertyLocal.name
      if(vueThis !== undefined)
        return vueThis[propName]
      else
        return storageObj[propName]
    }

    open operator fun setValue(thisRef: Any, propertyLocal: KProperty<*>, value: V) {
      console.log("set is run")
      if(assigned && singleAssign)
        throwVueKtException("The Vue ${this::class.simpleName?.toLowerCase() ?: ""} property '${propertyLocal.name}' in '${thisRef::class.simpleName}' has already been assigned.")

      val propName = property?.name ?: propertyLocal.name
      if(vueThis !== undefined)
        vueThis[propName] = value
      else
        storageObj[propName] = value

      assigned = true
    }
  }

  open internal fun getActual(): dynamic{
    val actual = newObject()
    actual.created = {
      vueThis = js("this")
      console.log("vueThis: ", vueThis)
      created()
    }

    dataFunction?.let {
      val dynamicIt: dynamic = it
      actual.data = { (dynamicIt() as VueData).backingObject }
    } ?: run {
      actual.data = { data.backingObject }
    }

    actual.computed = computed.backingObject
    actual.watch = watch.backingObject
    actual.methods = methods.backingObject
    templateImport(actual)

    return actual
  }
}