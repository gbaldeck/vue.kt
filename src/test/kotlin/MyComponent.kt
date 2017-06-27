import com.github.vuekt.core.VueComponent
import com.github.vuekt.external.alert
import com.github.vuekt.external.require

/**
 * Created by gbaldeck on 6/22/2017.
 */
class MyComponent: VueComponent(){
  override val templateImport: dynamic = require("./MyComponent.html")
  override val el: String = "my-component"

  var google: String by Data()
  var hello: String by Data("hello initial")
  var finishedLink: String by Data()
  var counter: Int by Data()
  var x: Int by Data()
  var y: Int by Data()
  var name: String by Data()

  var computedCounter: Int by Data()
  var computedCounter2: Int by Data()
  var methodOutput: () -> String by Method()

  val computedOutputGet = {
    console.log("computedOutput called")
    if(computedCounter > 5)
      "Greater than 5"
    else
      "Less than 5"
  }
  var computedOutput: String by Computed(computedOutputGet)

  var watchCounter: (Int, Int) -> Unit by Watch(this::computedCounter)

  var sayHello: () -> String by Method()
  var increase: (Int, dynamic) -> Unit by Method()
  var updateCoordinates: (dynamic) -> Unit by Method()
  var dummy: (dynamic) -> Unit by Method()
  var alertMe: () -> Unit by Method()
  var functionName: () -> String by Method()

  init {
    console.log(hello)
    counter = 0
    hello = "hello"
    google = "https://www.google.com/"
    finishedLink = """<a href="$google">Google Finished Link</a>"""
    val tempHello = hello
    sayHello = { hello }
    increase = { step, event -> counter += step }
    name = "Graham"

    x = 0
    y = 0
    updateCoordinates = {
      x = it.clientX;
      y = it.clientY;
    }
    dummy = {
      it.stopPropagation();
    }
    alertMe = {
      alert("Alert")
    }
    functionName = {
      name
    }

    //Testing computed
    computedCounter = 0
    computedCounter2 = 0
//    computedOutput = {
//      console.log("computedOutput called")
//      if(computedCounter > 5)
//        "Greater than 5"
//      else
//        "Less than 5"
//    }
    methodOutput = {
      console.log("methodOutput called")
      if(computedCounter > 5)
        "Greater than 5"
      else
        "Less than 5"
    }
    watchCounter = {
      value, _ ->
      console.log("$computedCounter | $value")
    }
  }

  override fun created() {
//    self.title = "Hello World!"
  }
}