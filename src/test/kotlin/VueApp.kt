import com.github.vuekt.Vue
import com.github.vuekt.external.require

/**
 * Created by gbaldeck on 6/22/2017.
 */
class VueApp: Vue(){
  override fun vueInit() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override val templateImport: dynamic = require("./VueApp.html")
  override val el: String = "#app"
  override val components: Components = componentsOf(MyComponent())
}