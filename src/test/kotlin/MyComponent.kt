import com.github.vuekt.core.VueComponent
import com.github.vuekt.external.require

/**
 * Created by gbaldeck on 6/22/2017.
 */
class MyComponent: VueComponent(){
  override val templateImport: dynamic = require("./MyComponent.html")
  override val el: String = "my-component"

  var google: String by Data()
  var hello: String by Data()
  var finishedLink: String by Data()

  var sayHello: () -> String by Method()

  init {
    hello = "hello"
    google = "https://www.google.com/"
    finishedLink = """<a href="$google">Google Finished Link</a>"""
    val tempHello = hello
    sayHello = { hello }
  }

  override fun created() {
//    self.title = "Hello World!"
  }
}