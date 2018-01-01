package io.gbaldeck.vuekt

import io.gbaldeck.vuekt.wrapper.*
import org.w3c.dom.HTMLButtonElement

external interface SubMethods {
  var resetName: () -> Unit
  var navigateToTestComponent: () -> Unit
  var incrementStoreCounter: () -> Unit
  var decrementStoreCounter: () -> Unit
  var incrementOtherCounter: () -> Unit
  var decrementOtherCounter: () -> Unit
}

external interface SubProps {
  var name: String
}

external interface SubData {
  var dataName: String
  var otherCounter: Int
}

external interface SubWatch {
  var `$route`: (VueRoute<SubProps, *>, VueRoute<SubProps, *>) -> Unit
}

external interface SubComputedFunctions {
  var storeCounter: () -> Int
  var doubleStoreCounter: () -> Int
}

external interface SubComputed {
  var storeCounter: Int
  var doubleStoreCounter: Int
}

class SubComponentClassStyle: io.gbaldeck.vuekt.wrapper.classStyle.VueComponent(){
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



interface SubComponent: VueComponent<SubData, SubMethods, SubComputed, SubWatch, Unit, SubProps>, VueRouteComponent<SubProps, Unit>

val initSubComponent = {
  require("KotlinSrc/SubComponent.scss")
  createVueComponent<SubComponent>("sub-component", require("KotlinSrc/SubComponent.html")) {
    initProps(SubProps::name)

    initData {
      dataName = vRoute.params.name
      otherCounter = 0
    }

    initMethods {
      resetName = {
        vProps.name = "Graham"
        vEmit("nameWasReset", vProps.name)
      }

      navigateToTestComponent = {
        vRouter.push("/")
      }

      incrementStoreCounter = {
        testStore.state!!.counter++

      }

      decrementStoreCounter = {
        testStore.state!!.counter--
      }

      incrementOtherCounter = {
        vData.otherCounter++
      }

      decrementOtherCounter = {
        vData.otherCounter--
      }
    }

    initComputed<SubComputedFunctions> {
      storeCounter = {
        console.log("Store counter called")
        testStore.state!!.counter
      }

      doubleStoreCounter = {
        console.log("Double Store counter called")
        testStore.getters!!.doubleCounter
      }
    }

    initWatch {
      `$route` = {
        routeA, routeB ->
        vData.dataName = routeA.params.name
      }
    }
  }
}
