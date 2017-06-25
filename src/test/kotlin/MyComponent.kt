import com.github.vuekt.VueComponent
import com.github.vuekt.external.require

/**
 * Created by gbaldeck on 6/22/2017.
 */
class MyComponent: VueComponent(){
  override val templateImport: dynamic = require("./MyComponent.html")
  override val el: String = "my-component"

  var title: String by Data()
  var method: () -> String by Method()

  init {
    title = "test"
    method = { "test method" }
  }

//  override val data =
//      dataFunOf(
//          "title" to "First render",
//          "test" to "https://www.google.com/",
//          "finishedLink" to """<a href="http://google.com">Google</a>""")
//
//  interface methodNames { var sayHello: () -> Unit; var increase: () -> Boolean }
//  override val methods: Methods =
//      methodsOf<methodNames>{
//        sayHello = {
//            self.title = "Hello!"
//            self.title
//        }
//
//        increase = {
//          true
//        }
//      }

//  override val methods: Methods =
//      methodsOf(
//          "sayHello" to {
//            console.log(this)
//            self.title = "Hello!"
//            self.title
//          },
//          "increase" to {
//
//          }
//      )

  override fun created() {
//    self.title = "Hello World!"
  }
}