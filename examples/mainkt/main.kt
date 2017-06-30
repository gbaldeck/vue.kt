import io.vuekt.framework.vue.initVue

/**
 * Created by gbaldeck on 6/17/2017.
 */
//Main entry point for webpack testing the examples
fun main(args: Array<String>){
  initVue(VueApp()) //init kitchensink
}