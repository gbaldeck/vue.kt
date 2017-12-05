package io.gbaldeck.vuekt.external

import io.gbaldeck.vuekt.isNullOrUndefined

@JsModule("vue")
@JsNonModule
private external val _Vue: dynamic
val Vue: VueInstance = _Vue.default

external interface VueInstance{
  fun <D, M, C, W, R> component(tagName: String, component: VueComponent<D, M, C, W, R>)
}

interface VueComponent<D, M, C, W, R>{
  var render: dynamic
  var staticRenderFns: dynamic

  var data: (() -> D)?
  var methods: M?
  var computed: C?
  var watch: W?

  var beforeCreate: Function<Unit>?
  var created: Function<Unit>?
  var beforeMount: Function<Unit>?
  var mounted: Function<Unit>?
  var beforeUpdate: Function<Unit>?
  var updated: Function<Unit>?
  var activated: Function<Unit>?
  var deactivated: Function<Unit>?
  var beforeDestroy: Function<Unit>?
  var destroyed: Function<Unit>?
  var errorCaptured: ((dynamic, VueComponent<*,*,*,*,*>, String) -> Boolean?)?
}

inline fun <D, M, C, W, R> VueComponent<D, M, C, W, R>.initData(crossinline config: D.() -> Unit){
  data = {
    val tempData = createJsObject<D>()
    tempData.config()
    tempData
  }
}

inline fun <D, M, C, W, R> VueComponent<D, M, C, W, R>.initMethods(config: M.() -> Unit){
  if(isNullOrUndefined(methods))
    methods = createJsObject()

  methods!!.config()
}

inline fun <D, M, C, W, R> VueComponent<D, M, C, W, R>.initComputed(config: C.() -> Unit){
  if(isNullOrUndefined(computed))
    computed = createJsObject()

  computed!!.config()
}

inline fun <D, M, C, W, R> VueComponent<D, M, C, W, R>.initWatch(config: W.() -> Unit){
  if(isNullOrUndefined(watch))
    watch = createJsObject()

  watch!!.config()
}

inline val <D, M, C, W, R> VueComponent<D, M, C, W, R>.vData: D
  get() {
    return js("this")
  }

inline val <D, M, C, W, R> VueComponent<D, M, C, W, R>.vMethods: M
  get() {
    return js("this")
  }

inline val <D, M, C, W, R> VueComponent<D, M, C, W, R>.vComputed: C
  get() {
    return js("this")
  }

inline val <D, M, C, W, R> VueComponent<D, M, C, W, R>.vWatch: W
  get() {
    return js("this")
  }

inline val <D, M, C, W, R> VueComponent<D, M, C, W, R>.vRefs: R
  get() {
    return js("this.\$refs")
  }

fun <T: VueComponent<*, *, *, *, *>> createVueComponent(tagName: String, template: dynamic, config: T.() -> Unit): T{
  val component = createJsObject<T>()
  component.config()
  component.render = template.render
  component.staticRenderFns = template.staticRenderFns

  if(isNullOrUndefined(Communicator.components[tagName])) {
    setInterval({
      if (!isNullOrUndefined(Communicator.components[tagName])) {
        Communicator.resolveComponent(tagName, component)
        clearInterval()
      }
    }, 5)
  } else {
    Communicator.resolveComponent(tagName, component)
  }

  return component
}

fun <T> createJsObject(): T = js("{}")
