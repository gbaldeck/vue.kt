package io.gbaldeck.vuekt

import io.gbaldeck.vuekt.wrapper.*

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
