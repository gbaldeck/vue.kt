package io.gbaldeck.vuekt

import io.gbaldeck.vuekt.wrapper.VuexStore
import io.gbaldeck.vuekt.wrapper.createVuexStore
import io.gbaldeck.vuekt.wrapper.initGetters
import io.gbaldeck.vuekt.wrapper.initState

external interface StoreState{
  var counter: Int
}

external interface StoreGetterFuns {
  var doubleCounter: (StoreState) -> Int
}

external interface StoreGetters {
  var doubleCounter: Int
}

external interface TestStore: VuexStore<StoreState, StoreGetters>

val testStore = createVuexStore<TestStore> {
  initState {
    counter = 0
  }

  initGetters<StoreGetterFuns> {
    doubleCounter = {
      state ->
      state.counter * 2
    }
  }
}
