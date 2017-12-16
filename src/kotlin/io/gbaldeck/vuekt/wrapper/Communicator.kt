package io.gbaldeck.vuekt.wrapper

external interface CommunicatorInstance {
  fun setComponentDefinition(tagName: String, component: VueComponent<*, *, *, *, *, *>)
  fun setDirectiveDefinition(name: String, directive: VueDirective)
  fun setFilterFunction(name: String, filterFun: Function<dynamic>)
  val components: dynamic
  val directives: dynamic
  val filters: dynamic
}

@JsModule("communicator")
@JsNonModule
private external val _communicator: dynamic

val Communicator: CommunicatorInstance = _communicator.default
