package io.gbaldeck.vuekt.wrapper

import kotlin.reflect.KCallable
import kotlin.reflect.KProperty

interface VueKtDelegate

abstract class VueComponent: VueCommon {
  abstract val template: dynamic
  open val elementName: String = _elementName()

  open fun beforeCreate(){}
  open fun created(){}
  open fun beforeMount(){}
  open fun mounted(){}
  open fun beforeUpdate(){}
  open fun updated(){}
  open fun activated(){}
  open fun deactivated(){}
  open fun beforeDestroy(){}
  open fun destroyed(){}
  open fun errorCaptured(err: dynamic, vm: dynamic, info: String): Boolean? = undefined

  class Computed<out T>(private val method: KCallable<T>): VueKtDelegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
      return Pair(property.name, method.name).asDynamic()
    }
  }

  class Watch<T>(private val method: KCallable<T>): VueKtDelegate {

    private var value: dynamic = undefined

    constructor(initialValue: T, method: KCallable<T>): this(method){
      value = initialValue
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): dynamic {
      return Triple(property.name, value, method.name).asDynamic()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
      this.value = value
    }
  }

  inner class Ref<T>: VueKtDelegate {
    operator fun getValue(thisRef: Any, property: KProperty<*>): T {
      return Pair(property.name, {
        val propertyName = property.name
        js("this.\$refs[propertyName]")
      }).asDynamic()
    }
  }

  class Prop<T>: VueKtDelegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
      return property.name.asDynamic()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    }
  }
}

inline fun <EMITDATA> VueComponent.emit(eventName: String, emitData: EMITDATA){
  js("this.\$emit(eventName, emitData)")
}
