package io.vuekt.framework.vue.common

import kotlin.reflect.KProperty

/**
 * Created by gbaldeck on 6/28/2017.
 */
class SingleAssign<T>(private var value: T? = null){
  operator fun getValue(thisRef: Any, property: KProperty<*>): T =
      value ?: throwVueKtException("Property '${property.name}' in '${thisRef::class.simpleName}' has not been set.")

  operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
      if(this.value == null)
        this.value = value
      else
        throwVueKtException("Property '${property.name}' in '${thisRef::class.simpleName}' has already been set.")
}