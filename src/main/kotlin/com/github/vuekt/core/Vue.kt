package com.github.vuekt.core

import com.github.vuekt.common.throwVueKtException
import kotlin.reflect.KProperty

/**
 * Created by gbaldeck on 6/22/2017.
 */
abstract class Vue: VueComponent() {
  private val components = VueCollection<String, VueComponent>()

  protected inner class Component<T: VueComponent>(val singleAssign: Boolean = false) {
    private var assigned = false
    private lateinit var thisComponent: T

    operator fun getValue(thisRef: Any, property: KProperty<*>): T {
      if(!assigned)
        throwVueKtException("The Vue component property '${property.name}' in '${thisRef::class.simpleName}' was never assigned a value.")

      return thisComponent
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
      if(assigned && singleAssign)
        throwVueKtException("The Vue component property '${property.name}' in '${thisRef::class.simpleName}' has already been assigned.")

      thisComponent = value
      components[value.el] = value.getActual()
      assigned = true
    }
  }

  override internal fun getActual(): dynamic{
    val actual = super.getActual()
    actual.el = el

    actual.components = components.backingObject

    return actual
  }
}

