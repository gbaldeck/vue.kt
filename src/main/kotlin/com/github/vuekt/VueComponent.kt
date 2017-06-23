package com.github.vuekt

/**
 * Created by gbaldeck on 6/22/2017.
 */
abstract class VueComponent {
  @JsName("templateImport")
  abstract val templateImport: dynamic
  @JsName("el")
  abstract val el: String
  @JsName("methods")
  open val methods: Methods = Methods()
  @JsName("data")
  open val data: () -> Data = { Data() }

  class Methods : VueCollection<String, () -> dynamic>() {
    override operator fun get(key: String): () -> dynamic = super.get(key)
    override operator fun set(key: String, value: () -> dynamic) {
      super.set(key, value)
    }
  }

  fun methodsOf(vararg methods: Pair<String, () -> dynamic>): Methods {
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

    actual.methods = methods.backingObject
    actual.data = { data().backingObject }
    templateImport(actual)

    return actual
  }
}