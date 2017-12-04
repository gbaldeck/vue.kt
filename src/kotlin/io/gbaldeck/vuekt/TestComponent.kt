package io.gbaldeck.vuekt

import io.gbaldeck.vuekt.external.*
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent

interface Person{

}

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
}

interface TestMethods{
  var sayHello: () -> String
  var increase: (Int, Event) -> Unit
  var updateCoordinates: (e: MouseEvent) -> Unit
  var alertMe: () -> Unit
  var result: () -> String
}

interface TestComputed {
  var compOne: () -> String
  var output: () -> String
  var cssClasses: () -> dynamic
}

interface TestWatch {
  var counter2: (Int) -> Unit
}

interface TestComponent: VueComponent<TestData, TestMethods, TestComputed, TestWatch>

val initTestComponent = {
  createVueComponent<TestComponent>("test-component", io.gbaldeck.vuekt.external.require("KotlinSrc/TestComponent.html")) {
    initData {
      hello = "Hello!"
      link = "http://www.google.com"
      counter = 0
      x = 0
      y = 0
      color = "green"
      counter2 = 0
      attachRed = false
      show = true
      ingredients = arrayOf("meat", "vegetables", "salt")
    }

    initMethods {
      sayHello = {
        vData.hello = "Hello again!"
        vData.hello
      }
      increase = { num, e ->
        vData.counter = vData.counter + num
      }
      updateCoordinates = { event ->
        vData.x = event.clientX
        vData.y = event.clientY
      }
      alertMe = {
        alert("Alert!")
      }
      result = {
        console.log("Method")
        if (vData.counter2 > 5)
          "Greater than 5"
        else
          "Smaller than 5"
      }
    }

    initComputed {
      compOne = {
        "1" + vData.hello
      }
      output = {
        console.log("Computed")
        if (vData.counter2 > 5)
          "Greater than 5"
        else
          "Smaller than 5"
      }
      cssClasses = {
        val cssObj = createJsObject<dynamic>()
        cssObj.red = vData.attachRed
        cssObj.blue = !vData.attachRed
        cssObj
      }
    }

    initWatch {
      counter2 = { num ->
        console.log("This was run!")
      }
    }
  }
}
