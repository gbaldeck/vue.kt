package com.github.vuekt.common

/**
 * Created by gbaldeck on 6/25/2017.
 */
internal class VueKtException(message: String) : Exception("Vue.kt Exception: " + message)

internal fun throwVueKtException(message: String): Nothing = throw VueKtException(message)

internal fun displayVueKtWarning(message: String) {
  console.log("Vue.kt Warning: " + message)
}