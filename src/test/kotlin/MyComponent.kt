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
  var counter: Int by Data()
  var x: Int by Data()
  var y: Int by Data()

  var sayHello: () -> String by Method()
  var increase: (Int, dynamic) -> Unit by Method()
  var updateCoordinates: (dynamic) -> Unit by Method()
  var dummy: (dynamic) -> Unit by Method()
  var alertMe: () -> Unit by Method()

  init {
    counter = 0
    hello = "hello"
    google = "https://www.google.com/"
    finishedLink = """<a href="$google">Google Finished Link</a>"""
    val tempHello = hello
    sayHello = { hello }
    increase = { step, event -> counter += step }

    x = 0
    y = 0
    updateCoordinates = {
      x = it.clientX;
      y = it.clientY;
    }
    dummy = {
      it.stopPropagation();
    }
  }

  override fun created() {
//    self.title = "Hello World!"
  }
}