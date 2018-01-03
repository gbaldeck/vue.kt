package io.gbaldeck.vuekt.wrapper

abstract class VueDirective: VueCommon {
  open val name: String = _name()

  @JsName("bind")
  open fun bind(el: dynamic, binding:dynamic, vnode:dynamic){}
  @JsName("inserted")
  open fun inserted(el: dynamic, binding:dynamic, vnode:dynamic){}
  @JsName("update")
  open fun update(el: dynamic, binding:dynamic, vnode:dynamic, oldVnode: dynamic){}
  @JsName("componentUpdated")
  open fun componentUpdated(el: dynamic, binding:dynamic, vnode:dynamic, oldVnode: dynamic){}
  @JsName("unbind")
  open fun unbind(el: dynamic, binding:dynamic, vnode:dynamic){}
}
