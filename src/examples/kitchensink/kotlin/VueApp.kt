import io.vuekt.framework.vue.core.Vue
import io.vuekt.framework.vue.external.require

/**
 * Created by gbaldeck on 6/22/2017.
 */
class VueApp: Vue(){
  override val templateImport: dynamic = require("kitchensink/kotlin/VueApp.html")
  override val el: String = "#app"

  var myComponent: MyComponent by Component()

  init {
    myComponent= MyComponent()
  }

  override fun created() {

  }
}