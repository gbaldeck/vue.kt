package io.gbaldeck.vuekt.wrapper

external interface VueHttpResource

external interface VueHttp{
  fun post(url: String, data: dynamic): dynamic
  fun get(url: String): dynamic
}


external interface VueResourceCustomOptions {
  var method: String
  var url: String
}

inline val VueHttpResource.vHttp: VueHttp
  get() {
    return js("this.\$http")
  }

inline fun VueHttpResource.vResource(url: String,
                                     urlParameters: dynamic = undefined,
                                     options: dynamic = undefined): dynamic{
    return js("this.\$resource(url, urlParameters, dynamic)")
  }

inline val VueHttpResource.vResource: dynamic
  get() {
    return js("this.\$resource")
  }
