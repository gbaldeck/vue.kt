package com.github.vuekt.external
/**
 * Created by gbaldeck on 5/9/2017.
 */
external fun require(module: String): dynamic

external val Math: dynamic

external fun setInterval(function: () -> dynamic, time: Number): dynamic

external fun clearInterval(id: dynamic)