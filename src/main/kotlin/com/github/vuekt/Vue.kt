package com.github.vuekt

/**
 * Created by gbaldeck on 6/21/2017.
 */
@JsModule("vue")
@JsNonModule
external val vue_ext: dynamic = definedExternally
val VueObj = vue_ext.default

fun initVue(template: dynamic){
  js("""
  var options = {
    el: '#app'
  };
  Object.assign(options, template({}));
  new _.com.github.vuekt.VueObj(options)
  """)
}

fun initComponent(template: dynamic){
  js("""
  var options = template({
    data: function() {
      return { title: 'Hello World!' }
    }
  });
  _.com.github.vuekt.VueObj.component('my-component', options);
  """)
//  js("""
//  template(_.com.github.vuekt.VueObj.component('my-component',{
//    data: function() {
//      return { title: 'Hello World!' }
//    }
//  }))()
//  """)
}