@file:Suppress("UNCHECKED_CAST")
package io.vuekt.framework.vue.core

import io.vuekt.framework.vue.common.*
import io.vuekt.framework.vue.external.Object
import kotlin.reflect.KProperty

/**
 * Created by gbaldeck on 6/22/2017.
 */
typealias VueData = VueCollection<String, dynamic>

abstract class VueComponent {
  abstract val templateImport: dynamic
  abstract val el: String
  private val data = VueData()
  private val computed = VueCollection<String, ComputedContainer<*>>()
  private val watch = VueCollection<String, Function<Unit>>()
  private val methods = VueCollection<String, Function<*>>()
  protected open var dataFunction: Function<VueData>? = null

  var vueThis: dynamic = undefined
    private set

  open fun created() {}

  protected inner class Data<T>(initialValue: T? = null, singleAssign: Boolean = false):
      VueOption<T>(initialValue, singleAssign, data, null)

  protected inner class ComputedContainer<T>(val singleAssign: Boolean){
    private val backingObject = newObject()

    lateinit var getter: () -> (() -> T)
    var setter: (() -> ((T) -> Unit))? = null

    fun finalize(propertyName: String): dynamic {
      backingObject["get"] = getter()

      setter?.let {
        if(singleAssign) {
          var assigned = false

          backingObject["set"] = {
            value: T ->
            if(assigned)
              throwVueKtException("The Vue computed property '$propertyName' in '${this@VueComponent::class.simpleName}' has already been assigned.")

            assigned = true
            it()(value)
          }
        } else {
          backingObject["set"] = it()
        }
      } ?: kotlin.run {
        backingObject["set"] = { throwVueKtException("The Vue computed property '$propertyName' in '${this@VueComponent::class.simpleName}' was not assigned a setter.") }
      }
      return backingObject
    }
  }

  protected inner class Computed<T> {
    private val dynVueComp:dynamic = this@VueComponent
    val computedContainer: ComputedContainer<T>
    val singleAssign: Boolean
    val getter: KProperty<() -> T>
    val setter: (KProperty<(T) -> Unit>)?

    constructor(getter: KProperty<() -> T>) {
      this.singleAssign = false
      this.getter = getter
      this.setter = null
      this.computedContainer = ComputedContainer(singleAssign)
      initGetterAndSetter()
    }

    constructor(getter: KProperty<() -> T>, setter: KProperty<(T) -> Unit>) {
      this.singleAssign = false
      this.setter = setter
      this.getter = getter
      this.computedContainer = ComputedContainer(singleAssign)
      initGetterAndSetter()
    }

    constructor(getter: KProperty<() -> T>, setter: KProperty<(T) -> Unit>, singleAssign: Boolean) {
      this.singleAssign = singleAssign
      this.getter = getter
      this.setter = setter
      this.computedContainer = ComputedContainer(singleAssign)
      initGetterAndSetter()
    }

    private fun initGetterAndSetter() {
      computedContainer.getter = { dynVueComp[getter.name] }

      setter?.let {
        computedContainer.setter = {
          dynVueComp[it.name]
        }
      }
    }

    operator fun getValue(thisRef: Any, propertyLocal: KProperty<*>): T {
      if(isNullOrUndefined(dynVueComp[getter.name]))
        throwVueKtException("The getter property '${getter.name}' for Vue computed property '${propertyLocal.name}' in '${thisRef::class.simpleName}' has not been assigned before this call to get.")

      return dynVueComp[getter.name]()
    }

    operator fun setValue(thisRef: Any, propertyLocal: KProperty<*>, value: T) {
      setter?.let {
        if(isNullOrUndefined(dynVueComp[setter.name]))
          throwVueKtException("The setter property '${it.name}' for Vue computed property '${propertyLocal.name}' in '${thisRef::class.simpleName}' has not been assigned before this call to set.")

        dynVueComp[setter.name](value)

      } ?: throwVueKtException("The Vue computed property '${propertyLocal.name}' in '${thisRef::class.simpleName}' was not assigned a setter.")
    }
  }

  protected inner class Watch<T: (V, V) -> Unit, V>(property: KProperty<V>, singleAssign: Boolean = false):
      VueOption<T>(null, singleAssign, watch as VueCollection<String, T>, property)

  protected inner class Method<T: Function<*>>(initialValue: T? = null, singleAssign: Boolean = false):
      VueOption<T>(initialValue, singleAssign, methods as VueCollection<String, T>, null)



  protected open inner class VueOption<V>(val initialValue: V?,
                                          val singleAssign: Boolean,
                                          val storageObj: VueCollection<String, V>,
                                          val property: KProperty<*>?) {
    var assigned = false
      private set

    open operator fun getValue(thisRef: Any, propertyLocal: KProperty<*>): V {
      if(!assigned) {
        if(isNotNullOrUndefined(initialValue))
          return initialValue!!
        else
          throwVueKtException("The Vue ${this::class.simpleName?.toLowerCase() ?: ""} property '${propertyLocal.name}' in '${thisRef::class.simpleName}' was never assigned a value.")
      }

      val propName = property?.name ?: propertyLocal.name
      if(vueThis !== undefined)
        return vueThis[propName]
      else
        return storageObj[propName]
    }

    open operator fun setValue(thisRef: Any, propertyLocal: KProperty<*>, value: V) {
      if((assigned || isNotNullOrUndefined(initialValue)) && singleAssign)
        throwVueKtException("The Vue ${this::class.simpleName?.toLowerCase() ?: ""} property '${propertyLocal.name}' in '${thisRef::class.simpleName}' has already been assigned.")

      val propName = property?.name ?: propertyLocal.name
      if(vueThis !== undefined)
        vueThis[propName] = value
      else
        storageObj[propName] = value

      assigned = true
    }
  }

  private fun setDelegateInitialValues() {
    val thisDynamic: dynamic = this

    (Object.getOwnPropertyNames(thisDynamic.__proto__) as Array<String>).forEach {
      val delegate = thisDynamic["$it\$delegate"]

      if(isNotNullOrUndefined(delegate)) {
        if(isNotNullOrUndefined(delegate.initialValue) && !delegate.assigned)
          thisDynamic[it] = delegate.initialValue
        else if(isNotNullOrUndefined(delegate.computedContainer))
          computed[it] = (delegate.computedContainer as ComputedContainer<*>).finalize(it)
      }
    }
  }

  open internal fun getActual(): dynamic{
    setDelegateInitialValues()

    val actual = newObject()
    actual.created = {
      vueThis = js("this")
      created()
    }

    dataFunction?.let {
      val dynamicIt: dynamic = it
      actual.data = { (dynamicIt() as VueData).backingObject }
    } ?: run {
      actual.data = { data.backingObject }
    }

    actual.computed = computed.backingObject
    actual.watch = watch.backingObject
    actual.methods = methods.backingObject
    templateImport(actual)

    return actual
  }
}