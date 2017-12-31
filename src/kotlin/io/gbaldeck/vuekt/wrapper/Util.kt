package io.gbaldeck.vuekt.wrapper

fun isNullOrUndefined(obj: Any?) = obj === null || obj === undefined

inline fun <T> createJsObject(): T = js("{}")

fun String.camelToDashCase(): String {
  return this.replace(Regex("([a-z])([A-Z])"), {
    result ->
    val (g1: String, g2: String) = result.destructured
    "$g1-$g2"
  })
}
