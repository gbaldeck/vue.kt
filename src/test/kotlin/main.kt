import com.github.vuekt.*

/**
 * Created by gbaldeck on 6/17/2017.
 */
fun main(args: Array<String>){
  console.log(vue_ext)
  val obj = require("./main.html")
  console.log(obj)
  initComponent(obj)
  initVue(require("./app.html"))
}