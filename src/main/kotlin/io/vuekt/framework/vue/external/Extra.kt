package io.vuekt.framework.vue.external
/**
 * Created by gbaldeck on 5/9/2017.
 */
external fun require(module: String): dynamic

external val Math: dynamic

external val Object: dynamic

external fun setInterval(function: () -> dynamic, time: Number): dynamic

external fun clearInterval(id: dynamic)

external val Kotlin:dynamic

external fun alert(message: dynamic)

external fun setTimeout(function: Function<*>, time: Number)