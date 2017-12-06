package io.gbaldeck.vuekt.external

interface VueDirective {
  var bind: ((el: dynamic, binding:dynamic, vnode:dynamic) -> Unit)?
  var inserted: ((el: dynamic, binding:dynamic, vnode:dynamic) -> Unit)?
  var update: ((el: dynamic, binding:dynamic, vnode:dynamic, oldVnode: dynamic) -> Unit)?
  var componentUpdated: ((el: dynamic, binding:dynamic, vnode:dynamic, oldVnode: dynamic) -> Unit)?
  var unbind: ((el: dynamic, binding:dynamic, vnode:dynamic) -> Unit)?
}


fun createVueDirective(name: String, config: VueDirective.() -> Unit): VueDirective{
  val directive = createJsObject<VueDirective>()
  directive.config()
  Communicator.setDirectiveDefinition(name, directive)
  return directive
}
