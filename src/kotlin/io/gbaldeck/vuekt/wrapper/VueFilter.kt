package io.gbaldeck.vuekt.wrapper

import kotlin.reflect.KCallable

abstract class VueFilter: VueCommon{
  open val name: String = _name()

  abstract val filter: KCallable<*>
}
