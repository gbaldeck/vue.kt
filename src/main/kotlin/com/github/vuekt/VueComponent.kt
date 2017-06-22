package com.github.vuekt

import com.github.vuekt.external.clearInterval
import com.github.vuekt.external.setInterval

/**
 * Created by gbaldeck on 6/22/2017.
 */
abstract class VueComponent: VueComponentOptions() {
  @JsName("intervalId")
  private val intervalId: dynamic
  init {
    intervalId = setInterval({
      val self = this@VueComponent
      val selfDynamic:dynamic = self
      val intervalId = self.intervalId
      if(templateImport !== undefined && templateImport !== null) {
        templateImport(self)
        clearInterval(intervalId)
        console.log(self)
        console.log(self.data())
      }
    }, 10)

  }

  abstract fun vueInit()
}