package com.github.vuekt

/**
 * Created by gbaldeck on 6/22/2017.
 */
abstract class VueComponent {
  abstract val templateImport: dynamic
  abstract val el: String
  open val methods: Methods = Methods()
  open val data: () -> Data = { Data() }
  protected var self: dynamic = undefined

  open fun created() {}

  class Methods : VueCollection<String, Function<*>>() {
    override operator fun get(key: String): Function<*> = super.get(key)
    override operator fun set(key: String, value: Function<*>) {
      super.set(key, value)
    }
  }

  fun methodsOf(vararg methods: Pair<String, Function<*>>): Methods {
    return VueCollection.create(*methods)
  }

  class Data : VueCollection<String, dynamic>() {
    override operator fun get(key: String): dynamic = super.get(key)
    override operator fun set(key: String, value: dynamic) {
      super.set(key, value)
    }
  }

  fun dataOf(vararg data: Pair<String, dynamic>): Data {
    return VueCollection.create(*data)
  }

  fun dataFunOf(vararg data: Pair<String, dynamic>): () -> Data {
    return { dataOf(*data) }
  }

  open internal fun getActual(): dynamic{
    val actual = js("new Object()")
    actual.created = {
      self = js("this")
      created()
    }

    actual.methods = methods.backingObject
    actual.data = { data().backingObject }
    templateImport(actual)

    return actual
  }
}