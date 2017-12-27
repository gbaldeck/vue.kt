package io.gbaldeck.vuekt

import io.gbaldeck.vuekt.wrapper.VuexStore
import io.gbaldeck.vuekt.wrapper.createVueStore

external interface StoreState

external interface TestStore: VuexStore<StoreState>

val testStore = createVueStore<TestStore> {  }
