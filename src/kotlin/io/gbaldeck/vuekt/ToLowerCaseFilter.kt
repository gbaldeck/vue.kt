package io.gbaldeck.vuekt

import io.gbaldeck.vuekt.wrapper.createVueFilter

val initToLowerCaseFilter = {
  createVueFilter("to-lowercase") {
    value: dynamic ->
    value.toLowerCase()
  }
}
