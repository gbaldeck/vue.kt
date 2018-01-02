package io.gbaldeck.vuekt

import io.gbaldeck.vuekt.wrapper.*
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent

class Person(var name: String, var age: String)

interface TestData{
  var hello: String
  var link: String
  var x: Int
  var y: Int
  var counter: Int
  var color: String
  var counter2: Int
  var attachRed: Boolean
  var show: Boolean
  var ingredients: Array<String>
  var persons: Array<Person>
  var showParagraph: Boolean
  var title: String
  var name: String
}

interface TestMethods{
  var sayHello: () -> String

}

interface TestComputedFunctions {
  var compOne: () -> String
  var output: () -> String
  var cssClasses: () -> dynamic
}

interface TestComputed {
  var compOne: String
  var output: String
  var cssClasses: dynamic
}

interface TestWatch {
  var counter2: (Int) -> Unit
}

interface TestRefs {
  val myButton: HTMLButtonElement
}

class TestComponent: VueComponent(){
  override val template: dynamic = require("KotlinSrc/TestComponent.html")

  var hello = "Hello!"
  var link = "http://www.google.com"
  var counter = 0
  var x = 0
  var y = 0
  var color = "green"
  var counter2: Int by Watch(0, TestComponent::counter2Watch)
  var attachRed = false
  var show = true
  var ingredients = arrayOf("meat", "vegetables", "salt")
  var persons = arrayOf(Person("sally","34"), Person("George", "23"))
  var showParagraph = false
  var title = "TO LOWER CASE"
  var name = "Graham"

  val compOne: String by Computed(TestComponent::compOneComputed)
  val output: String by Computed(TestComponent::outputComputed)
  val cssClasses: dynamic by Computed(TestComponent::cssClassesComputed)

  val myButton: HTMLButtonElement by Ref()

  fun sayHello(): String {
    hello = "Hello again"
    return hello
  }

  fun increase(num: Int, e: Event) {
    counter += num
  }

  fun updateCoordinates(event: MouseEvent){
    x = event.clientX
    y = event.clientY
  }

  fun alertMe(){
    alert("Alert!")
  }

  fun result(): String{
    console.log("Method")
    if (counter2 > 5)
      return "Greater than 5"
    else
      return "Smaller than 5"
  }

  fun showAndChangeText(){
    showParagraph = !showParagraph
    myButton.innerText = "Test"
  }

  fun destroy(){
    js("this.\$destroy()")
  }

  fun changeName(){
    name = "Sepheroth"
  }

  fun compOneComputed():String {
    return "1" + hello
  }

  fun outputComputed():String {
    console.log("Computed")
    if (counter2 > 5)
      return "Greater than 5"
    else
      return "Smaller than 5"
  }
  fun cssClassesComputed(): dynamic {
    val cssObj = createJsObject<dynamic>()
    cssObj.red = attachRed
    cssObj.blue = !attachRed
    return cssObj
  }

  fun counter2Watch() {
    console.log("This was run!")
  }
}

    initWatch {

    }

    beforeCreate = {
      println("beforeCreate()")
    }

    created = {
      println("beforeCreate()")
    }

    beforeMount = {
      println("beforeMount()")
    }

    mounted = {
      println("mounted()")
    }

    beforeUpdate = {
      println("beforeUpdate()")
    }

    updated = {
      println("updated()")
    }

    beforeDestroy = {
      println("beforeDestroy()")
    }

    destroyed = {
      println("destroyed()")
    }

  }
}
