package io.gbaldeck.vuekt.wrapper

external interface VueComponentRoute<PARAMS, QUERY>

external interface VueRouter {
  fun push(target: String)
  fun push(target: VueRouterTarget)
}

external interface VueRouterTarget {
  var path: String
}

inline val VueComponentRoute<*,*>.vRouter: VueRouter
  get() {
    return js("this.\$router")
  }

external interface VueRoute<out PARAMS, out QUERY> {
  val params: PARAMS
  val query: QUERY
}

inline val <PARAMS, QUERY> VueComponentRoute<PARAMS, QUERY>.vRoute: VueRoute<PARAMS, QUERY>
  get() {
    return js("this.\$route")
  }
