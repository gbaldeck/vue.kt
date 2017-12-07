package io.gbaldeck.vuekt.external

import kotlin.js.Promise

interface VueHttp{
  fun post(url: String, data: dynamic): dynamic
  fun get(url: String): dynamic
}

inline val VueComponent<*,*,*,*,*,*>.vHttp: VueHttp
  get() {
    return js("this.\$http")
  }

inline val VueDirective.vHttp: VueHttp
  get() {
    return js("this.\$http")
  }
