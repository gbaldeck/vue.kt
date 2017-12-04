package io.gbaldeck.vuekt.external

import io.gbaldeck.vuekt.isNullOrUndefined

@JsModule("vue")
@JsNonModule
private external val _Vue: dynamic
val Vue: VueInstance = _Vue.default

external interface VueInstance{
  fun <D, M, C, W> component(tagName: String, component: VueComponent<D, M, C, W>)
}

interface VueComponent<D, M, C, W>{
  var render: dynamic
  var staticRenderFns: dynamic

  var data: (() -> D)?
  var methods: M?
  var computed: C?
  var watch: W?


  val self
    get() = this
}

inline fun <D, M, C, W> VueComponent<D, M, C, W>.initData(config: D.() -> Unit){
  val tempData = createJsObject<D>()
  tempData.config()
  data = { tempData }
}

inline fun <D, M, C, W> VueComponent<D, M, C, W>.initMethods(config: M.() -> Unit){
  if(isNullOrUndefined(methods))
    methods = createJsObject()

  methods!!.config()
}

inline fun <D, M, C, W> VueComponent<D, M, C, W>.initComputed(config: C.() -> Unit){
  if(isNullOrUndefined(computed))
    computed = createJsObject()

  computed!!.config()
}

inline fun <D, M, C, W> VueComponent<D, M, C, W>.initWatch(config: W.() -> Unit){
  if(isNullOrUndefined(watch))
    watch = createJsObject()

  watch!!.config()
}

inline val <D, M, C, W> VueComponent<D, M, C, W>.vData: D
  get() {
    return js("this")
  }

inline val <D, M, C, W> VueComponent<D, M, C, W>.vMethods: M
  get() {
    return js("this")
  }

inline val <D, M, C, W> VueComponent<D, M, C, W>.vComputed: C
  get() {
    return js("this")
  }

inline val <D, M, C, W> VueComponent<D, M, C, W>.vWatch: W
  get() {
    return js("this")
  }

fun <T: VueComponent<*, *, *, *>> createVueComponent(tagName: String, template: dynamic, config: T.() -> Unit): T{
  val component = createJsObject<T>()
  component.config()
  component.render = template.render
  component.staticRenderFns = template.staticRenderFns
//  Vue.component(tagName, component)
  Communicator.resolveComponent(tagName, component)
  return component
}

fun <T> createJsObject(): T = js("{}")
