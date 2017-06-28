package io.vuekt.framework.vue

import io.vuekt.framework.vue.core.Vue
import io.vuekt.framework.vue.external.VueObj

/**
 * Created by gbaldeck on 6/23/2017.
 */
fun initVue(vue: Vue): dynamic {
  val vueObj = VueObj
  return js("new vueObj(vue.getActual())")
}