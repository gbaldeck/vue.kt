package mock

import io.vuekt.framework.vue.core.VueComponent
import io.vuekt.framework.vue.external.require

/**
 * Created by gbaldeck on 7/1/2017.
 */
//vueComponentInstanceInitialValue()
//componentInitialValue()
class VueComponentMockData1 : VueComponent(){
  override val templateImport: dynamic = require("mock/VueComponentMockData.html")
  override val el: String = "vue-data-mock1"

  var test by Data("test")
}

//vueComponentReassignInitialValue()
//componentReassignInitialValue()
class VueComponentMockData2 : VueComponent(){
  override val templateImport: dynamic = require("mock/VueComponentMockData.html")
  override val el: String = "vue-data-mock2"

  var test by Data("test")

  init {
    test = "test2"
  }
}

class VueComponentMockData3 : VueComponent(){
  override val templateImport: dynamic = require("mock/VueComponentMockData.html")
  override val el: String = "vue-data-mock3"

  val test: String by Data("test")

  init {
    val test2 = test
  }
}