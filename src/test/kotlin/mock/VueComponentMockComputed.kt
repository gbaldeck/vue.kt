package mock

import io.vuekt.framework.vue.core.VueComponent
import io.vuekt.framework.vue.external.require

/**
 * Created by gbaldeck on 7/1/2017.
 */

//vueComponentInstanceShouldThrowNoAssignedSetterError()
class VueComponentMockComputed3 : VueComponent(){
  override val templateImport: dynamic = require("mock/VueComponentMockComputed.html")
  override val el: String = "vue-component-mock3"

  var test by Data("test")
  var testComputed by Computed(this::testComputedGet)

  val testComputedGet = {
    test
  }
}

//componentShouldThrowNoAssignedSetterError()
class VueComponentMockComputed4 : VueComponent(){
  override val templateImport: dynamic = require("mock/VueComponentMockComputed.html")
  override val el: String = "vue-component-mock4"

  var test by Data("test")
  var testComputed by Computed(this::testComputedGet)

  val testComputedGet = {
    test
  }

  init {
    testComputed = "test1"
  }
}

//componentShouldThrowSetterNotAssignedAtThisPointError()
class VueComponentMockComputed5 : VueComponent(){
  override val templateImport: dynamic = require("mock/VueComponentMockComputed.html")
  override val el: String = "vue-component-mock5"

  var test by Data("test")
  var testComputed by Computed(this::testComputedGet, this::testComputedSet)

  val testComputedGet = {
    test
  }

  val testComputedSet: (String) -> Unit

  init {
    testComputed = "testComputed"
    testComputedSet = {
      test = it
    }
  }
}

//componentShouldThrowGetterNotAssignedAtThisPointError()
class VueComponentMockComputed6 : VueComponent(){
  override val templateImport: dynamic = require("mock/VueComponentMockComputed.html")
  override val el: String = "vue-component-mock6"

  var test by Data("test")
  var testComputed by Computed(this::testComputedGet)

  val testComputedGet: () -> String

  init {
    console.log(testComputed)
    testComputedGet = {
      test
    }
  }
}

class VueComponentMockComputed7 : VueComponent(){
  override val templateImport: dynamic = require("mock/VueComponentMockComputed.html")
  override val el: String = "vue-component-mock7"

  var test by Data("test")
  var testComputed by Computed(this::testComputedGet)

  val testComputedGet: () -> String

  init {
    testComputedGet = {
      test
    }
  }
}

class VueComponentMockComputed8 : VueComponent(){
  override val templateImport: dynamic = require("mock/VueComponentMockComputed.html")
  override val el: String = "vue-component-mock8"

  var test by Data("test")
  var testComputed by Computed(this::testComputedGet)

  val testComputedGet: () -> String

  init {
    testComputedGet = {
      test
    }
  }
}