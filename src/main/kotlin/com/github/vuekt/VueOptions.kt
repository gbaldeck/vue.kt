package com.github.vuekt

/**
 * Created by gbaldeck on 6/22/2017.
 */
abstract class VueOptions: VueComponentOptions() {
  @JsName("components")
  open val components: Components = Components()

  class Components {
    fun add(component: VueComponent) {
      this._set(component.el, component)
    }

    operator fun get(el: String): VueComponent = this._get(el)
    operator fun set(el: String, component: VueComponent) {
      this._set(el, component)
    }
  }

  fun componentsOf(vararg components: Pair<String, VueComponent>): Components {
    return createObj(*components)
  }

  fun componentsOf(vararg components: VueComponent): Components{
    val obj = Components()
    components.forEach {
      obj.add(it)
    }
    return obj
  }
}