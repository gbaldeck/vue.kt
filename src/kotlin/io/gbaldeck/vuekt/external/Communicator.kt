package io.gbaldeck.vuekt.external

external interface CommunicatorInstance {
  fun resolveComponent(tagName: String, component: VueComponent<*, *, *, *, *>)
}

@JsModule("communicator")
@JsNonModule
private external val _communicator: dynamic

val Communicator: CommunicatorInstance = _communicator.default
