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
  private val methods = VueCollection<String, Function<*>>()
  private val data = VueData()
  protected open var dataFunction: Function<VueData>? = null

  var vueThis: dynamic = undefined
    private set

  private var actualRun = false

  open fun created() {}

  protected inner class Data(singleAssign: Boolean = false):
      VueOption<dynamic>(singleAssign, data)


  protected inner class Method<T: Function<*>>(singleAssign: Boolean = false):
      VueOption<T>(singleAssign, methods as VueCollection<String, T>)


  protected open inner class VueOption<V>(val singleAssign: Boolean, val storageObj: VueCollection<String, V>) {
    private var assigned = false

    operator fun getValue(thisRef: Any, property: KProperty<*>): V {
      if(!assigned)
        throwVueKtException("The Vue ${this::class.simpleName?.toLowerCase() ?: ""} property '${property.name}' in '${thisRef::class.simpleName}' was never assigned a value.")

      if(actualRun && vueThis !== undefined)
        return vueThis[property.name]
      else
        return storageObj[property.name]
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: V) {
      if(assigned && singleAssign)
        throwVueKtException("The Vue ${this::class.simpleName?.toLowerCase() ?: ""} property '${property.name}' in '${thisRef::class.simpleName}' has already been assigned.")

      if(actualRun && vueThis !== undefined)
        vueThis[property.name] = value
      else
        storageObj[property.name] = value

      assigned = true
    }
  }

  open internal fun getActual(): dynamic{
    actualRun = true

    val actual = newObject()
    actual.created = {
      vueThis = js("this")
      created()
    }

    dataFunction?.let {
      val dynamicIt: dynamic = it
      actual.data = { (dynamicIt() as VueData).backingObject }
    } ?: run {
      actual.data = { data.backingObject }
    }

    actual.methods = methods.backingObject
    templateImport(actual)

    return actual
  }
}