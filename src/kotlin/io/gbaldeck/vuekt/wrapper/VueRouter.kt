package io.gbaldeck.vuekt.wrapper

typealias RouteGuardLambda = (to: dynamic, from: dynamic, next: dynamic) -> Unit

external interface VueRouteComponent<PARAMS, QUERY> {
  var beforeRouteEnter: RouteGuardLambda?
  var beforeRouteUpdate: RouteGuardLambda?
  var beforeRouteLeave: RouteGuardLambda?
}

external interface VueRouter {
  fun push(target: String)
  fun push(target: VueRouterTarget)
}

external interface VueRouterTarget {
  var path: String
}

inline val VueRouteComponent<*,*>.vRouter: VueRouter
  get() {
    return js("this.\$router")
  }

external interface VueRoute<out PARAMS, out QUERY> {
  val params: PARAMS
  val query: QUERY
}

inline val <PARAMS, QUERY> VueRouteComponent<PARAMS, QUERY>.vRoute: VueRoute<PARAMS, QUERY>
  get() {
    return js("this.\$route")
  }
