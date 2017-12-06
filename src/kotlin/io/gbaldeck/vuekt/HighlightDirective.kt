package io.gbaldeck.vuekt

import io.gbaldeck.vuekt.external.createVueDirective
import io.gbaldeck.vuekt.external.setTimeout

val initHighlightDirective = {
  createVueDirective("highlight") {
    bind = {
      el, binding, _ ->
//      el.style.backgroundColor = "green"
//      el.style.backgroundColor = binding.value
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
}
