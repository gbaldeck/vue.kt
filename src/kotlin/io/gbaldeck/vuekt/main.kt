package io.gbaldeck.vuekt

fun main(args: Array<String>) {
  //just in case, always init directives first
  //this is so that their definition will always be available before the component templates are loaded
  initHighlightDirective()

  initSubComponent()
  initTestComponent()

}
