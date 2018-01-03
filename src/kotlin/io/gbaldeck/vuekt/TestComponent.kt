package io.gbaldeck.vuekt

import io.gbaldeck.vuekt.wrapper.*
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent

class Person(var name: String, var age: String)

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

  @JsName("sayHello")
  fun sayHello(): String {
    hello = "Hello again"
    return hello
  }

  @JsName("increase")
  fun increase(num: Int, e: Event) {
    counter += num
    updateCoordinates(e as MouseEvent)
  }

  @JsName("updateCoordinates")
  fun updateCoordinates(event: MouseEvent){
    x = event.clientX
    y = event.clientY
  }

  @JsName("alertMe")
  fun alertMe(){
    alert("Alert!")
  }

  @JsName("result")
  fun result(): String{
    console.log("Method")
    if (counter2 > 5)
      return "Greater than 5"
    else
      return "Smaller than 5"
  }

  @JsName("showAndChangeText")
  fun showAndChangeText(){
    showParagraph = !showParagraph
    myButton.innerText = "Test"
  }

  @JsName("destroy")
  fun destroy(){
    js("this.\$destroy()")
  }

  @JsName("changeName")
  fun changeName(){
    name = "Sepheroth"
  }

  @JsName("compOneComputed")
  fun compOneComputed():String {
    return "1" + hello
  }

  @JsName("outputComputed")
  fun outputComputed():String {
    console.log("Computed")
    if (counter2 > 5)
      return "Greater than 5"
    else
      return "Smaller than 5"
  }

  @JsName("cssClassesComputed")
  fun cssClassesComputed(): dynamic {
    val cssObj = createJsObject<dynamic>()
    cssObj.red = attachRed
    cssObj.blue = !attachRed
    return cssObj
  }

  @JsName("counter2Watch")
  fun counter2Watch() {
    console.log("This was run!")
  }

  override fun beforeCreate() {
    println("beforeCreate()")
  }

  override fun created() {
    println("beforeCreate()")
  }

  override fun beforeMount() {
    println("beforeMount()")
  }

  override fun mounted() {
    println("mounted()")
  }

  override fun beforeUpdate() {
    println("beforeUpdate()")
  }

  override fun updated() {
    println("updated()")
  }

  override fun beforeDestroy() {
    println("beforeDestroy()")
  }

  override fun destroyed() {
    println("destroyed()")
  }

}
