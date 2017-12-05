package io.gbaldeck.vuekt.external

import io.gbaldeck.vuekt.isNullOrUndefined
import kotlin.reflect.KProperty

@JsModule("vue")
@JsNonModule
private external val _Vue: dynamic
val Vue: VueInstance = _Vue.default

external interface VueInstance{
  fun <D, M, C, W, R, P> component(tagName: String, component: VueComponent<D, M, C, W, R, P>)
}

interface VueComponent<D, M, C, W, R, P>{
  var render: dynamic
  var staticRenderFns: dynamic

  var data: (() -> D)?
  var methods: M?
  var computed: C?
  var watch: W?
  var props: Array<String>

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
  var errorCaptured: ((dynamic, VueComponent<*,*,*,*,*, *>, String) -> Boolean?)?
}

inline fun <D, M, C, W, R, P> VueComponent<D, M, C, W, R, P>.initData(crossinline config: D.() -> Unit){
  data = {
    val tempData = createJsObject<D>()
    tempData.config()
    tempData
  }
}

inline fun <D, M, C, W, R, P> VueComponent<D, M, C, W, R, P>.initMethods(config: M.() -> Unit){
  if(isNullOrUndefined(methods))
    methods = createJsObject()

  methods!!.config()
}

inline fun <D, M, C, W, R, P> VueComponent<D, M, C, W, R, P>.initComputed(config: C.() -> Unit){
  if(isNullOrUndefined(computed))
    computed = createJsObject()

  computed!!.config()
}

inline fun <D, M, C, W, R, P> VueComponent<D, M, C, W, R, P>.initWatch(config: W.() -> Unit){
  if(isNullOrUndefined(watch))
    watch = createJsObject()

  watch!!.config()
}

fun <D, M, C, W, R, P> VueComponent<D, M, C, W, R, P>.initProps(vararg propsList: KProperty<*>){
  props = arrayOf()
  propsList.forEachIndexed {
    index, prop ->
    props[index] = prop.name
  }
}

inline val <D, M, C, W, R, P> VueComponent<D, M, C, W, R, P>.vData: D
  get() {
    return js("this")
  }

inline val <D, M, C, W, R, P> VueComponent<D, M, C, W, R, P>.vMethods: M
  get() {
    return js("this")
  }

inline val <D, M, C, W, R, P> VueComponent<D, M, C, W, R, P>.vComputed: C
  get() {
    return js("this")
  }

inline val <D, M, C, W, R, P> VueComponent<D, M, C, W, R, P>.vWatch: W
  get() {
    return js("this")
  }

inline val <D, M, C, W, R, P> VueComponent<D, M, C, W, R, P>.vRefs: R
  get() {
    return js("this.\$refs")
  }

inline val <D, M, C, W, R, P> VueComponent<D, M, C, W, R, P>.vProps: P
  get() {
    return js("this")
  }

inline fun <D, M, C, W, R, P, ED> VueComponent<D, M, C, W, R, P>.vEmit(eventName: String, emitData: ED){
  js("this.\$emit(eventName, emitData)")
}

fun <T: VueComponent<*, *, *, *, *, *>> createVueComponent(tagName: String, template: dynamic, config: T.() -> Unit): T{
  val component = createJsObject<T>()
  component.config()
  component.render = template.render
  component.staticRenderFns = template.staticRenderFns
  Communicator.setComponentDefinition(tagName, component)

  return component
}

fun <T> createJsObject(): T = js("{}")
