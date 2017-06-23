package com.github.vuekt

/**
 * Created by gbaldeck on 6/23/2017.
 */
fun initVue(vue: Vue): dynamic{
  return js("new _.com.github.vuekt.external.VueObj(vue.getActual())")
}