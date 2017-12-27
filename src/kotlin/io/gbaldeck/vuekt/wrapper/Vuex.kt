package io.gbaldeck.vuekt.wrapper

@JsModule("vuex")
@JsNonModule
private external val _vuex: dynamic

external interface VuexStore<STATE>{
  val state: STATE?
}

fun <T: VuexStore<*>> createVueStore(config: T.() -> Unit): T{
  val storedef = createJsObject<T>()
  storedef.config()

  val vuexref = _vuex.default
  val store = js("new vuexref.store(storedef)")

  return store
}

fun <STATE> VuexStore<STATE>.initState(config: STATE.() -> Unit){
  if(isNullOrUndefined(state)){
    val tempstate = createJsObject<STATE>()
    tempstate.config()

    val _this = this
    js("_this.state = tempstate")
  } else {
    state!!.config()
  }
}



