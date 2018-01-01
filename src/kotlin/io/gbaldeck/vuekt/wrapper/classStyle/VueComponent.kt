package io.gbaldeck.vuekt.wrapper.classStyle

import io.gbaldeck.vuekt.wrapper.Communicator
import io.gbaldeck.vuekt.wrapper.camelToDashCase
import io.gbaldeck.vuekt.wrapper.createJsObject
import kotlin.reflect.KCallable
import kotlin.reflect.KProperty

interface VueKtDelegate

abstract class VueComponent {
  abstract val template: dynamic
  open val elementName: String = _elementName()

  open fun beforeCreate(){}
  open fun created(){}
  open fun beforeMount(){}

  class Computed<out T>(private val method: KCallable<T>): VueKtDelegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
      return Pair(property.name, method.name).asDynamic()
    }
  }

  class Watch<T>(private val method: KCallable<T>): VueKtDelegate {
    private var value: dynamic = undefined

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
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

    val protoNamesList = protoNames.toMutableList()
    protoNamesList.removeAll(arrayOf("constructor","beforeCreate","created","beforeMount"))

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

    vueObject.beforeCreate = component["beforeCreate"]

    vueObject.render = component.template.render
    vueObject.staticRenderFns = component.template.staticRenderFns
    Communicator.setComponentDefinition(vueComponent.elementName, vueObject)
  }
}

fun getType(item: dynamic): String = js("Object").prototype.toString.call(item)
