package io.gbaldeck.vuekt.external

fun createVueFilter(name: String, filterFun: Function<dynamic>){
  Communicator.setFilterFunction(name, filterFun)
}
