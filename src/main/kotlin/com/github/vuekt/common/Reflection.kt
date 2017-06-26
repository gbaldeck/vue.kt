package com.github.vuekt.common

import kotlin.reflect.KClass

/**
 * Created by gbaldeck on 4/25/2017.
 */
fun <T : Any> KClass<T>.createInstance(vararg args: dynamic): T {
  val cls = this.js
  val allArgs = arrayOf(null, *args)
  return js("new (Function.prototype.bind.apply(cls, allArgs))")
}

fun KClass<*>.extendsOrImplements(klass: KClass<*>): Boolean {
  val jsClass = this.js
  val otherJsClass = klass.js
  val metadata = js("jsClass.\$metadata\$")

  js( "if(metadata.interfaces){" +
      " var interfaces = metadata.interfaces;" +
      " var i;" +
      " for(i = 0; i < interfaces.length; i++){" +
      "   if(interfaces[i] === otherJsClass){" +
      "     return true;" +
      "   }" +
      " }" +
      "}" )
  return false;
}