package io.gbaldeck.vuekt.wrapper

fun isNullOrUndefined(obj: Any?) = obj === null || obj === undefined

inline fun <T> createJsObject(): T = js("{}")
