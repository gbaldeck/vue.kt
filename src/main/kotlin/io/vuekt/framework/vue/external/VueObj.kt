package io.vuekt.framework.vue.external

/**
 * Created by gbaldeck on 6/21/2017.
 */
@JsModule("vue")
@JsNonModule
external val vue_ext: dynamic = definedExternally

@JsName("VueObj")
val VueObj = vue_ext.default