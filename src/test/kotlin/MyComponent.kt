import com.github.vuekt.VueComponent
import com.github.vuekt.external.require

/**
 * Created by gbaldeck on 6/22/2017.
 */
class MyComponent: VueComponent(){
  override val templateImport: dynamic = require("./MyComponent.html")
  override val el: String = "my-component"
  override val data = dataFunOf("title" to "Hello world!")

  override fun vueInit() {

  }
}