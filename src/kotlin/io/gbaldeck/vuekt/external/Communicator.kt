package io.gbaldeck.vuekt.external

external interface CommunicatorInstance {
  fun setComponentDefinition(tagName: String, component: VueComponent<*, *, *, *, *, *>)
  fun setDirectiveDefinition(name: String, directive: VueDirective)
  val components: dynamic
  val directives: dynamic
}

@JsModule("communicator")
@JsNonModule
private external val _communicator: dynamic

val Communicator: CommunicatorInstance = _communicator.default
