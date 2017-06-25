package com.github.vuekt

/**
 * Created by gbaldeck on 6/22/2017.
 */
abstract class Vue: VueComponent() {
  open val components: Components = Components()

  class Components : VueCollection<String, VueComponent>() {
    fun add(component: VueComponent) {
      super.set(component.el, component)
    }

    override operator fun get(key: String): VueComponent = super.get(key)
    override operator fun set(key: String, value: VueComponent) {
      super.set(key, value)
    }
  }

  fun componentsOf(vararg components: Pair<String, VueComponent>): Components {
    return VueCollection.create(*components)
  }

  fun componentsOf(vararg components: VueComponent): Components{
    val obj = Components()
    components.forEach {
      obj.add(it)
    }
    return obj
  }

  override internal fun getActual(): dynamic{
    val actual = super.getActual()
    actual.el = el

    val componentsObj: dynamic = newObject()
    components.forEach {
      (key, value) ->
      componentsObj[key] = value.getActual()
    }

    actual.components = componentsObj

    return actual
  }
}

