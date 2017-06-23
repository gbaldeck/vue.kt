package com.github.vuekt

/**
 * Created by gbaldeck on 6/23/2017.
 */
fun initVue(vue: Vue): dynamic{

  js("console.log('VueMain: ', new _.com.github.vuekt.external.VueObj(vue.getActual()))")
  return null
}