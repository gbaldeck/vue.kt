package io.gbaldeck.vuekt

import io.gbaldeck.vuekt.external.VueComponent
import io.gbaldeck.vuekt.external.createVueComponent
import io.gbaldeck.vuekt.external.require

interface SubComponent: VueComponent<Unit, Unit, Unit, Unit, Unit>

val initSubComponent = {
  createVueComponent<SubComponent>("sub-component", require("KotlinSrc/SubComponent.html")) {

  }
}
