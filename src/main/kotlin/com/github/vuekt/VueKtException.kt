package com.github.vuekt

/**
 * Created by gbaldeck on 6/25/2017.
 */
class VueKtException(message: String?): Exception("Vue.kt Exception: "+message)
fun throwVueKtException(message: String? = null): Nothing = throw VueKtException(message)