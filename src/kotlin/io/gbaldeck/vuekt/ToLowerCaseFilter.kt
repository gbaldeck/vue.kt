package io.gbaldeck.vuekt

import io.gbaldeck.vuekt.external.createVueFilter

val initToLowerCaseFilter = {
  createVueFilter("to-lowercase") {
    value: dynamic ->
    value.toLowerCase()
  }
}
