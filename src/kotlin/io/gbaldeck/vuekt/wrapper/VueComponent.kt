package io.gbaldeck.vuekt.wrapper

import kotlin.reflect.KProperty

external interface VueComponent<DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>{
  var render: dynamic
  var staticRenderFns: dynamic

  var data: (() -> DATA)?
  var methods: METHODS?
  var computed: COMPUTED?
  var watch: WATCH?
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
  var errorCaptured: ((dynamic, VueComponent<*,*,*,*,*,*>, String) -> Boolean?)?
}

inline fun <DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>
  VueComponent<DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>.initData(noinline config: DATA.() -> Unit){
  data = {
    val configTemp = config //so that the DCE doesn't cut out the parameter since its not being used in the Kotlin code
    val tempData = createJsObject<DATA>()
    js("configTemp.call(this, tempData)") //the function is losing reference to the Vue 'this', so must bind the local 'this' to the function
    tempData
  }
}

inline fun <DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>
  VueComponent<DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>.initMethods(config: METHODS.() -> Unit){
  if(isNullOrUndefined(methods))
    methods = createJsObject()

  methods!!.config()
}

inline fun <DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>
  VueComponent<DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>.initComputed(config: COMPUTED.() -> Unit){
  if(isNullOrUndefined(computed))
    computed = createJsObject()

  computed!!.config()
}

inline fun <DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>
  VueComponent<DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>.initWatch(config: WATCH.() -> Unit){
  if(isNullOrUndefined(watch))
    watch = createJsObject()

  watch!!.config()
}

fun <DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>
  VueComponent<DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>.initProps(vararg propsList: KProperty<*>){
  props = arrayOf()
  propsList.forEachIndexed {
    index, prop ->
    props[index] = prop.name
  }
}

inline val <DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>
  VueComponent<DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>.vData: DATA
  get() {
    return js("this")
  }

inline val <DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>
  VueComponent<DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>.vMethods: METHODS
  get() {
    return js("this")
  }

inline val <DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>
  VueComponent<DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>.vComputed: COMPUTED
  get() {
    return js("this")
  }

inline val <DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>
  VueComponent<DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>.vWatch: WATCH
  get() {
    return js("this")
  }

inline val <DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>
  VueComponent<DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>.vRefs: REFS
  get() {
    return js("this.\$refs")
  }

inline val <DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>
  VueComponent<DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>.vProps: PROPS
  get() {
    return js("this")
  }

inline fun <DATA, METHODS, COMPUTED, WATCH, REFS, PROPS, EMITDATA>
  VueComponent<DATA, METHODS, COMPUTED, WATCH, REFS, PROPS>.vEmit(eventName: String, emitData: EMITDATA){
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
