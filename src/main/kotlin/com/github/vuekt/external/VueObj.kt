package com.github.vuekt.external

/**
 * Created by gbaldeck on 6/21/2017.
 */
@JsModule("vue")
@JsNonModule
external val vue_ext: dynamic = definedExternally
val VueObj = vue_ext.default

fun initVue(template: dynamic){
  js("""
  var options = template({});
  options.el = '#app';
  new _.com.github.vuekt.external.VueObj(options)
  """)
}

fun initComponent(template: dynamic){
  js("""
  var options = template({
    data: function() {
      return { title: 'Hello World!' }
    }
  });
  _.com.github.vuekt.external.VueObj.component('my-component', options);
  """)
//  js("""
//  template(_.com.github.vuekt.external.getVueObj.component('my-component',{
//    data: function() {
//      return { title: 'Hello World!' }
//    }
//  }))()
//  """)
}