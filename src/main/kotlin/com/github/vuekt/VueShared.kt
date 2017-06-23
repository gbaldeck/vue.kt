package com.github.vuekt

import com.github.vuekt.external.VueObj

/**
 * Created by gbaldeck on 6/23/2017.
 */
fun initVue(vue: Vue): dynamic {
  val vueObj = VueObj
  return js("new vueObj(vue.getActual())")
}