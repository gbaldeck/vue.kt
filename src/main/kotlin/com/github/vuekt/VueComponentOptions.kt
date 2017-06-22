package com.github.vuekt

/**
 * Created by gbaldeck on 6/22/2017.
 */
abstract class VueComponentOptions {
  @JsName("templateImport")
  abstract val templateImport: dynamic
  @JsName("el")
  abstract val el: String
  @JsName("methods")
  open val methods: Methods = Methods()
  @JsName("data")
  open val data: () -> Data = { Data() }

  class Methods {
    operator fun get(key: String): () -> dynamic = this._get(key)
    operator fun set(key: String, value: () -> dynamic) {
      this._set(key, value)
    }
  }

  fun methodsOf(vararg methods: Pair<String, () -> dynamic>): Methods {
    return createObj(*methods)
  }

  class Data {
    operator fun get(key: String): dynamic = this._get(key)
    operator fun set(key: String, value: dynamic) {
      this._set(key, value)
    }
  }

  fun dataOf(vararg data: Pair<String, dynamic>): Data {
    return createObj(*data)
  }

  fun dataFunOf(vararg data: Pair<String, dynamic>): () -> Data {
    return { dataOf(*data) }
  }

  internal inline fun <reified T: Any> createObj(vararg pairs: Pair<dynamic, dynamic>): T {
    val obj = T::class.createInstance()
    pairs.forEach{
      (key, value) ->
      obj._set(key, value)
    }
    return obj
  }
}