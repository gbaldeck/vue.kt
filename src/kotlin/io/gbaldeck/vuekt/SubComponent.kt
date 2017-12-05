package io.gbaldeck.vuekt

import io.gbaldeck.vuekt.external.*

interface SubMethods {
  var resetName: () -> Unit
}

interface SubProps {
  var name: String
}

interface SubComponent: VueComponent<Unit, SubMethods, Unit, Unit, Unit, SubProps>

val initSubComponent = {
  require("KotlinSrc/SubComponent.scss")
  createVueComponent<SubComponent>("sub-component", require("KotlinSrc/SubComponent.html")) {
    initProps(SubProps::name)
    initMethods {
      resetName = {
        vProps.name = "Graham"
        vEmit("nameWasReset", vProps.name)
      }
    }
  }
}
