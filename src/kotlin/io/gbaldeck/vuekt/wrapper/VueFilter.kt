package io.gbaldeck.vuekt.wrapper

fun createVueFilter(name: String, filterFun: Function<dynamic>){
  Communicator.setFilterFunction(name, filterFun)
}
