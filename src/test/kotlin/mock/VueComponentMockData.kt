package mock

import io.vuekt.framework.vue.core.VueComponent

/**
 * Created by gbaldeck on 7/1/2017.
 */
//vueComponentInstanceInitialValue()
//componentInitialValue()
class VueComponentMockData1 : VueComponent(){
  override val templateImport: dynamic = io.vuekt.framework.vue.external.require("mock/VueComponentMockData.html")
  override val el: String = "vue-data-mock1"

  var test by Data("test")
}

//componentReassignInitialValue()
class VueComponentMockData2 : VueComponent(){
  override val templateImport: dynamic = io.vuekt.framework.vue.external.require("mock/VueComponentMockData.html")
  override val el: String = "vue-data-mock1"

  var test by Data("test")

  init {
    test = "test2"
  }
}