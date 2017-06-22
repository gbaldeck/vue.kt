package com.github.vuekt

import com.github.vuekt.external.clearInterval
import com.github.vuekt.external.setInterval

/**
 * Created by gbaldeck on 6/22/2017.
 */

abstract class Vue: VueOptions() {
  @JsName("vue")
  protected var vue: dynamic = undefined
  @JsName("intervalId")
  private val intervalId: dynamic

  abstract fun vueInit()

  init {
    intervalId = setInterval({
      val self = this@Vue
      val intervalId = self.intervalId
      if(templateImport !== undefined && templateImport !== null) {
        templateImport(self)
        vue = js("new _.com.github.vuekt.external.VueObj(self)")
        clearInterval(intervalId)
        console.log(self)
      }
    }, 10)
  }
}

