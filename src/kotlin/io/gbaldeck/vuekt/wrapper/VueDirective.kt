package io.gbaldeck.vuekt.wrapper

abstract class VueDirective: VueCommon {
  open val elementName: String = _elementName()

  open fun bind(el: dynamic, binding:dynamic, vnode:dynamic){}
  open fun inserted(el: dynamic, binding:dynamic, vnode:dynamic){}
  open fun update(el: dynamic, binding:dynamic, vnode:dynamic, oldVnode: dynamic){}
  open fun componentUpdated(el: dynamic, binding:dynamic, vnode:dynamic, oldVnode: dynamic){}
  open fun unbind(el: dynamic, binding:dynamic, vnode:dynamic){}
}
