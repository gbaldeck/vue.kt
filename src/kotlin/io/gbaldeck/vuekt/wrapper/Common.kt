package io.gbaldeck.vuekt.wrapper


external fun require(module: String): dynamic

external fun alert(message: String)

external fun setInterval(method: Function<Unit>, millis: Int)

external fun clearInterval()

external fun setTimeout(method: Function<Unit>, millis: Int)
