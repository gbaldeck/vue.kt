package io.gbaldeck.vuekt

import io.gbaldeck.vuekt.external.VueComponent
import io.gbaldeck.vuekt.external.createVueComponent
import io.gbaldeck.vuekt.external.initProps
import io.gbaldeck.vuekt.external.require

interface SubProps {
  val name: String
}

interface SubComponent: VueComponent<Unit, Unit, Unit, Unit, Unit, SubProps>

val initSubComponent = {
  require("KotlinSrc/SubComponent.scss")
  createVueComponent<SubComponent>("sub-component", require("KotlinSrc/SubComponent.html")) {
    initProps(SubProps::name)
  }
}
