package io.gbaldeck.vuekt

import io.gbaldeck.vuekt.wrapper.*

interface SubMethods {
  var resetName: () -> Unit
  var navigateToTestComponent: () -> Unit
}

interface SubProps {
  var name: String
}

interface SubData {
  var dataName: String
}

interface SubWatch {
  var `$route`: (VueRoute<SubProps, *>, VueRoute<SubProps, *>) -> Unit
}

interface SubComponent: VueComponent<SubData, SubMethods, Unit, SubWatch, Unit, SubProps>, VueRouteComponent<SubProps, Unit>

val initSubComponent = {
  require("KotlinSrc/SubComponent.scss")
  createVueComponent<SubComponent>("sub-component", require("KotlinSrc/SubComponent.html")) {
    initProps(SubProps::name)

    initData {
      dataName = vRoute.params.name
    }

    initMethods {
      resetName = {
        vProps.name = "Graham"
        vEmit("nameWasReset", vProps.name)
      }

      navigateToTestComponent = {
        vRouter.push("/")
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
