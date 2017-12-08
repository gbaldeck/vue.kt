package io.gbaldeck.vuekt.external

import kotlin.js.Promise

interface VueHttp{
  fun post(url: String, data: dynamic): dynamic
  fun get(url: String): dynamic
}


interface VueResourceCustomOptions {
  var method: String
  var url: String
}

inline val VueComponent<*,*,*,*,*,*>.vHttp: VueHttp
  get() {
    return js("this.\$http")
  }

inline val VueDirective.vHttp: VueHttp
  get() {
    return js("this.\$http")
  }

inline fun VueComponent<*,*,*,*,*,*>.vResource(url: String,
                                               urlParameters: dynamic = undefined,
                                               options: dynamic = undefined): dynamic{
    return js("this.\$resource(url, urlParameters, dynamic)")
  }

inline val VueDirective.vResource: dynamic
  get() {
    return js("this.\$resource")
  }
