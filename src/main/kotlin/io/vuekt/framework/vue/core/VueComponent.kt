@file:Suppress("UNCHECKED_CAST")

package io.vuekt.framework.vue.core

import io.vuekt.framework.vue.common.*
import io.vuekt.framework.vue.external.Object
import kotlin.reflect.KProperty

/**
 * Created by gbaldeck on 6/22/2017.
 */
typealias VueData = VueCollection<String, dynamic>

abstract class VueComponent {
  abstract val templateImport: dynamic
  abstract val el: String
  protected open var dataFunction: Function<VueData>? = null

  var vueThis: dynamic = undefined
    private set

  open fun created() {}

  protected inner class Computed<T>(val getter: KProperty<() -> T>, val setter: (KProperty<(T) -> Unit>)? = null) {
    private val dynVueComp: dynamic = this@VueComponent

    @JsName("getValue")
    operator fun getValue(thisRef: Any, propertyLocal: KProperty<*>): T {
      if (isNullOrUndefined(dynVueComp[getter.name]))
        throwVueKtException("The getter property '${getter.name}' for Vue computed property '${propertyLocal.name}' in '${thisRef::class.simpleName}' has not been assigned before this call to get.")

      return dynVueComp[getter.name]()
    }

    @JsName("setValue")
    operator fun setValue(thisRef: Any, propertyLocal: KProperty<*>, value: T) {
      if (isNullOrUndefined(setter))
        throwVueKtException("The Vue computed property '${propertyLocal.name}' in '${thisRef::class.simpleName}' was not assigned a setter function.")
      else if (isNullOrUndefined(dynVueComp[setter!!.name]))
        throwVueKtException("The setter property '${setter!!.name}' for Vue computed property '${propertyLocal.name}' in '${thisRef::class.simpleName}' has not been assigned before this call to set.")

      dynVueComp[setter.name](value)
    }
  }

  protected inner class Data<T>(initialValue: T? = null) : VueOption<T>(initialValue, null)

  protected inner class Watch<T : (V, V) -> Unit, V>(property: KProperty<V>, initialValue: T? = null) :
      VueOption<T>(initialValue, property)

  protected inner class Method<T : Function<*>>(initialValue: T? = null) :
      VueOption<T>(initialValue, null)


  protected open inner class VueOption<V>(var value: V?, val property: KProperty<*>?) {
    var assigned = false
      private set

    @JsName("getValue")
    open operator fun getValue(thisRef: Any, propertyLocal: KProperty<*>): V {
      if (isNotNullOrUndefined(value))
        return value!!
      else
        throwVueKtException("The Vue ${this::class.simpleName?.toLowerCase() ?: ""} property '${propertyLocal.name}' in '${thisRef::class.simpleName}' was never assigned a value.")
    }

    @JsName("setValue")
    open operator fun setValue(thisRef: Any, propertyLocal: KProperty<*>, value: V) {
      this.value = value
    }
  }

  private fun finalizeDelegates(): dynamic {
    val actual: dynamic = Any()
    val thisDynamic: dynamic = this
    val dataObj: dynamic = Any()
    actual.data = { dataObj }
    actual.computed = Any()
    actual.watch = Any()
    actual.methods = Any()

    (Object.getOwnPropertyNames(thisDynamic.__proto__) as Array<String>).forEach {
      name ->
      val delegate = thisDynamic["$name\$delegate"]

      if (isNotNullOrUndefined(delegate)) {
        val metadata = js("new Kotlin.PropertyMetadata(name)")
        //Check to see that the delegate was assigned a value, it will throw errors if it wasn't
        console.log(delegate)
        delegate.getValue(this, metadata)

        val objConfig = jsObjectOf(
            "get" to { delegate.getValue(this, metadata) },
            "set" to {
              value: dynamic ->
              delegate.setValue(this, metadata, value)
            },
            "enumerable" to true,
            "configurable" to true
        )

        when (delegate) {
          is Computed<*> -> {
            actual.computed[name] = jsObjectOf(
                "get" to { delegate.getValue(this, metadata) },
                "set" to {
                  value: dynamic ->
                  delegate.setValue(this, metadata, value)
                }
            )
          }
          is Data<*> -> {
            Object.defineProperty(dataObj, name, objConfig)
          }
          is Watch<*, *> -> {
            Object.defineProperty(actual.watch, delegate.property!!.name, objConfig)
          }
          is Method<*> -> {
            Object.defineProperty(actual.methods, name, objConfig)
          }
        }
      }
    }
    return actual
  }

  open internal fun getActual(): dynamic {
    val actual = finalizeDelegates()

    actual.created = {
      vueThis = js("this")
      created()
    }

    dataFunction?.let {
      actual.data = it
    }

    templateImport(actual)

    return actual
  }
}