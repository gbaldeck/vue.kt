package io.gbaldeck.vuekt.wrapper.classStyle

import io.gbaldeck.vuekt.wrapper.createJsObject
import kotlin.reflect.KCallable
import kotlin.reflect.KProperty

interface VueKtDelegate

abstract class VueComponent {

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

  class Ref<T>: VueKtDelegate {
    inline operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
      val propertyName = property.name
      return js("this[propertyName]")
    }
  }

  class Prop<out T>: VueKtDelegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
      return property.name.asDynamic()
    }
  }
}

object Vue{
  infix fun component(vueComponent: VueComponent){
    val component = vueComponent.asDynamic()
    val ownNames = js("Object").getOwnPropertyNames(component) as Array<String>
    val protoNames = js("Object").getOwnPropertyNames(component.constructor.prototype) as Array<String>
    println(ownNames)
    println(protoNames)
    ownNames.forEach {
      println("$it : ${js("Object").prototype.toString.call(component[it])}")
    }
    println("----------------")
    println("----------------")
    protoNames.forEach {
      println("$it : ${js("Object").prototype.toString.call(component[it])}")
    }
    val vueObject = createJsObject<dynamic>()
    vueObject.data = createJsObject<dynamic>()
    vueObject.methods = createJsObject<dynamic>()
    vueObject.computed = createJsObject<dynamic>()
    vueObject.watches = createJsObject<dynamic>()
    vueObject.props = arrayOf<String>().asDynamic()

    val protoNamesList = protoNames.toMutableList()
    protoNamesList.remove("constructor")

    ownNames.forEach {
      if(component[it] !is VueKtDelegate) {
        vueObject.data[it] = component[it]

      } else {
        val delegateGetterKey = it.substringBefore("_")

        when(component[it]) {
          is VueComponent.Computed<*> -> {
            val (propertyName, methodName) = component[delegateGetterKey] as Pair<String, String>
            vueObject.computed[propertyName] = component[methodName]
            protoNamesList.remove(methodName)
          }
          is VueComponent.Watch<*> -> {
            val (propertyName, propertyValue, methodName) = component[delegateGetterKey] as Triple<String, dynamic, String>
            vueObject.data[propertyName] = propertyValue
            vueObject.watches[propertyName] = component[methodName]
            protoNamesList.remove(methodName)
          }
          is VueComponent.Prop<*> -> {
            val propName = component[delegateGetterKey]
            vueObject.props.push(propName)
          }

        }
        protoNamesList.remove(delegateGetterKey)
      }
    }

    protoNames.forEach {
      vueObject.methods[it] = component[it]
    }
  }
}

fun getType(item: dynamic): String = js("Object").prototype.toString.call(item)
