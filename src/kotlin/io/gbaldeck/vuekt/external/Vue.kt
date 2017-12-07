package io.gbaldeck.vuekt.external

//Can probably delete this file since all of this is being handle in the shell.

@JsModule("vue")
@JsNonModule
private external val _Vue: dynamic
val Vue: VueInstance = _Vue.default

external interface VueInstance{
  fun <D, M, C, W, R, P> component(tagName: String, component: VueComponent<D, M, C, W, R, P>)
  val http: VueInstanceHttp
}

typealias ResponseFunction = (response: dynamic) -> Unit
typealias NextFunction = (responseFunc: ResponseFunction) -> Unit

external interface VueInstanceHttp{
  val options: VueInstanceHttpOptions
  val interceptors: Array<(request: dynamic, next: NextFunction) -> Unit>
}

external interface VueInstanceHttpOptions{
  var root: String //Root URL for $http
}
