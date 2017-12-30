package io.gbaldeck.vuekt

import io.gbaldeck.vuekt.wrapper.classStyle.VueComponent
import io.gbaldeck.vuekt.wrapper.classStyle.VueKtDelegate
import kotlin.reflect.KProperty

class ClassStyleComponent: VueComponent() {

  var test = 123
  var tester: String

  var delegate: String by CSDelegate()
  var delegate2: Int by CSDelegate()

  init{
    tester = "set it"
    println(ClassStyleComponent::testmethod.asDynamic()())
  }

  val test1234: String
    get() {
      return "string awesome"
    }

  fun testmethod(){
    console.log(this)
    tester = "its tested"
    println("tested the method")
  }
}

class CSDelegate: VueKtDelegate {

  var item: dynamic = undefined

  operator fun <T> getValue(thisRef: Any?, property: KProperty<*>): T {
    println("Get $item")
    return item
  }

  operator fun <T> setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    println("Set $value")
    item = value
  }
}
