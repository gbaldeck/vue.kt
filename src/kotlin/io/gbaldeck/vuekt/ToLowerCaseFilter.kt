package io.gbaldeck.vuekt

import io.gbaldeck.vuekt.wrapper.VueFilter
import kotlin.reflect.KCallable

class ToLowerCaseFilter: VueFilter(){
  override val name: String = "to-lowercase"

  override val filter: KCallable<*> = ToLowerCaseFilter::toLowerCase

  @JsName("toLowerCase")
  fun toLowerCase(value: dynamic){
    return value.toLowerCase()
  }
}
