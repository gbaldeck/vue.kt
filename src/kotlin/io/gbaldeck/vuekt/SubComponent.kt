package io.gbaldeck.vuekt

import io.gbaldeck.vuekt.wrapper.*
import org.w3c.dom.HTMLButtonElement

class SubComponentClassStyle: VueComponent(){
  override val template: dynamic = require("KotlinSrc/SubComponent.html")
  override val elementName = "sub-component"

  var data_name: String = ""
  var otherCounter = 0

  var name_one: String by Prop()
  val resetNameButton: HTMLButtonElement by Ref()

  val storeCounter: Int by Computed(SubComponentClassStyle::storeCounterFun)
  val doubleStoreCounter: Int by Computed(SubComponentClassStyle::doubleStoreCounterFun)
  val `$route`: dynamic by Watch(SubComponentClassStyle::routeWatchFun)

  override fun created() {
    data_name = js("this.\$route.params.name_one")
  }

  fun resetName(){
    val rnb = resetNameButton
    console.log(rnb)
    console.log(js("this.\$refs.resetNameButton"))
    name_one = "Graham"
    val _name = name_one
    js("this.\$emit('nameWasReset', _name)")
  }

  fun navigateToTestComponent() {
    js("this.\$router.push('/')")
  }

  fun incrementStoreCounter() {
    testStore.state!!.counter++

  }

  fun decrementStoreCounter() {
    testStore.state!!.counter--
  }

  fun incrementOtherCounter() {
    this.otherCounter++
  }

  fun decrementOtherCounter() {
    this.otherCounter--
  }

  fun storeCounterFun(): Int {
    console.log("Store counter called")
    return testStore.state!!.counter
  }

  fun doubleStoreCounterFun(): Int {
    console.log("Double Store counter called")
    return testStore.getters!!.doubleCounter
  }

  fun routeWatchFun(routeA: dynamic, routeB: dynamic){
    this.data_name = routeA.params.name
  }
}
