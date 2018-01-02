package io.gbaldeck.vuekt.wrapper

import kotlin.reflect.KCallable
import kotlin.reflect.KProperty

interface VueKtDelegate

abstract class VueComponent {
  abstract val template: dynamic
  open val elementName: String = _elementName()

  open fun beforeCreate(){}
  open fun created(){}
  open fun beforeMount(){}
  open fun mounted(){}
  open fun beforeUpdate(){}
  open fun updated(){}
  open fun activated(){}
  open fun deactivated(){}
  open fun beforeDestroy(){}
  open fun destroyed(){}
  open fun errorCaptured(err: dynamic, vm: dynamic, info: String): Boolean? = undefined

  class Computed<out T>(private val method: KCallable<T>): VueKtDelegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
      return Pair(property.name, method.name).asDynamic()
    }
  }

  class Watch<T>(private val method: KCallable<T>): VueKtDelegate {

    private var value: dynamic = undefined

    constructor(initialValue: T, method: KCallable<T>): this(method){
      value = initialValue
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): dynamic {
      return Triple(property.name, value, method.name).asDynamic()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
      this.value = value
    }
  }

  inner class Ref<T>: VueKtDelegate {
    operator fun getValue(thisRef: Any, property: KProperty<*>): T {
      return Pair(property.name, {
        val propertyName = property.name
        js("this.\$refs[propertyName]")
      }).asDynamic()
    }
  }

  class Prop<T>: VueKtDelegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
      return property.name.asDynamic()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    }
  }
}

inline fun <EMITDATA> VueComponent.emit(eventName: String, emitData: EMITDATA){
  js("this.\$emit(eventName, emitData)")
}

private fun VueComponent._elementName() = this::class.simpleName!!.camelToDashCase().toLowerCase()

object Vue{
  infix fun component(vueComponent: VueComponent){
    val component = vueComponent.asDynamic()
    val ownNames = js("Object").getOwnPropertyNames(component) as Array<String>
    val protoNames = js("Object").getOwnPropertyNames(component.constructor.prototype) as Array<String>
    println(ownNames)
    println(protoNames)

    val vueObject = createJsObject<dynamic>()
    val data = createJsObject<dynamic>()
    vueObject.methods = createJsObject<dynamic>()
    vueObject.computed = createJsObject<dynamic>()
    vueObject.watches = createJsObject<dynamic>()
    vueObject.props = arrayOf<String>().asDynamic()

    val lifeCycleHooks = arrayOf("beforeCreate","created","beforeMount",
      "mounted","beforeUpdate","updated","activated","deactivated","beforeDestroy","destroyed","errorCaptured")

    val protoNamesList = protoNames.toMutableList()
    protoNamesList.remove("constructor")
    protoNamesList.removeAll(lifeCycleHooks)

    ownNames.forEach {
      if(component[it] !is VueKtDelegate) {
        data[it] = component[it]

      } else {
        val subIt = it.substringBeforeLast("$")
        val delegatePropertyKey = subIt.substringBeforeLast("_")

        when(component[it]) {
          is VueComponent.Computed<*> -> {
            val (propertyName, methodName) = component[delegatePropertyKey] as Pair<String, String>
            vueObject.computed[propertyName] = component[methodName]
            protoNamesList.remove(methodName)
          }
          is VueComponent.Watch<*> -> {
            val (propertyName, propertyValue, methodName) = component[delegatePropertyKey] as Triple<String, dynamic, String>

            data[propertyName] = propertyValue
            vueObject.watches[propertyName] = component[methodName]
            protoNamesList.remove(methodName)
          }
          is VueComponent.Ref<*> -> {
            val (propertyName, refComputedFun) = component[delegatePropertyKey] as Pair<String, dynamic>
            vueObject.computed[propertyName] = refComputedFun
          }
          is VueComponent.Prop<*> -> {
            val propName = component[delegatePropertyKey]
            vueObject.props.push(propName)
          }

        }
        protoNamesList.remove(delegatePropertyKey)
      }
    }

    protoNamesList.forEach {
      vueObject.methods[it] = component[it]
    }

    vueObject.data = {
      js("Object").assign(createJsObject(), data)
    }

    lifeCycleHooks.forEach {
      vueObject[it] = component[it]
    }

    vueObject.render = component.template.render
    vueObject.staticRenderFns = component.template.staticRenderFns
    Communicator.setComponentDefinition(vueComponent.elementName, vueObject)
  }
}

fun getType(item: dynamic): String = js("Object").prototype.toString.call(item)
