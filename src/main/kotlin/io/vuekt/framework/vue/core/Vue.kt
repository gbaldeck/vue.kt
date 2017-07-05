package io.vuekt.framework.vue.core

import io.vuekt.framework.vue.common.throwVueKtException
import kotlin.reflect.KProperty

/**
 * Created by gbaldeck on 6/22/2017.
 */
abstract class Vue: VueComponent() {

  //This is checked for in internal global function finalizeDelegates() in VueComponent.kt
  inner class Component<T: VueComponent>(initialValue: T? = null): VueOption<T>(initialValue, null)

  override internal fun getActual(): dynamic{
    val actual = super.getActual()
    actual.el = el

    return actual
  }
}

