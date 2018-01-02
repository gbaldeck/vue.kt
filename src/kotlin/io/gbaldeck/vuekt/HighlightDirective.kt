package io.gbaldeck.vuekt

import io.gbaldeck.vuekt.wrapper.VueDirective
import io.gbaldeck.vuekt.wrapper.setTimeout

class HighlightDirective: VueDirective(){

  override val elementName: String = "highlight"

  override fun bind(el: dynamic, binding: dynamic, vnode: dynamic) {
//    el.style.backgroundColor = "green"
//    el.style.backgroundColor = binding.value
    var delay = 0
    if(binding.modifiers["delayed"])
      delay = 3000

    setTimeout({
      if(binding.arg == "background") //checks for the :background argument
        el.style.backgroundColor = binding.value
      else
        el.style.color = binding.value
    }, delay)
  }
}
