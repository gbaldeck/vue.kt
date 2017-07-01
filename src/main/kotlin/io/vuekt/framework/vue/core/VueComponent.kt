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

  internal inner class ComputedContainer<T>(val hasSetter: Boolean){
    private val backingObject = newObject()

    var getter: () -> (() -> T)
      get() = backingObject["get"]
      set(value) { backingObject["get"] = value }

    var setter: () -> ((T) -> Unit)
      get() = backingObject["set"]
      set(value) { backingObject["set"] = value }

    fun finalize(): dynamic {
      backingObject["get"] = getter()

      if(hasSetter)
        backingObject["set"] = setter()
      else
        backingObject["set"] = setter

      return backingObject
    }
  }

  protected inner class Computed<T> {
    lateinit var propertyName: String //needs to be set when the operator setValue is called and when getActual is called
    private val dynVueComp:dynamic = this@VueComponent
    @JsName("computedContainer")
    internal val computedContainer: ComputedContainer<T>
    private var assigned = false
    val singleAssign: Boolean
    val getter: KProperty<() -> T>
    val setter: (KProperty<(T) -> Unit>)?

    constructor(getter: KProperty<() -> T>) {
      this.singleAssign = false
      this.getter = getter
      this.setter = null
      this.computedContainer = ComputedContainer(false)
      initGetterAndSetter()
    }

    constructor(getter: KProperty<() -> T>, setter: KProperty<(T) -> Unit>) {
      this.singleAssign = false
      this.setter = setter
      this.getter = getter
      this.computedContainer = ComputedContainer(true)
      initGetterAndSetter()
    }

    constructor(getter: KProperty<() -> T>, setter: KProperty<(T) -> Unit>, singleAssign: Boolean) {
      this.singleAssign = singleAssign
      this.getter = getter
      this.setter = setter
      this.computedContainer = ComputedContainer(true)
      initGetterAndSetter()
    }

    private fun initGetterAndSetter() {
      computedContainer.getter = { dynVueComp[getter.name] }

      if(isNotNullOrUndefined(setter)) {
        if(singleAssign) {
          computedContainer.setter = {
            {
              value: T ->
              if (assigned)
                throwVueKtException("The Vue computed property '${setter!!.name}' in '${this@VueComponent::class.simpleName}' has already been assigned.")

              assigned = true
              dynVueComp[setter!!.name](value)
            }
          }
        } else {
          computedContainer.setter = { dynVueComp[setter!!.name] }
        }
      } else {
        computedContainer.setter = { throwVueKtException("The Vue computed property '$propertyName' in '${this@VueComponent::class.simpleName}' was not assigned a setter.") }
      }
    }

    operator fun getValue(thisRef: Any, propertyLocal: KProperty<*>): T {
      if(isNullOrUndefined(dynVueComp[getter.name]))
        throwVueKtException("The getter property '${getter.name}' for Vue computed property '${propertyLocal.name}' in '${thisRef::class.simpleName}' has not been assigned before this call to get.")

      return computedContainer.getter()()
    }

    operator fun setValue(thisRef: Any, propertyLocal: KProperty<*>, value: T) {
      propertyName = propertyLocal.name
      if((singleAssign && isNullOrUndefined(dynVueComp[setter!!.name])) || isNullOrUndefined(computedContainer.setter()))
        throwVueKtException("The setter property '${setter!!.name}' for Vue computed property '${propertyLocal.name}' in '${thisRef::class.simpleName}' has not been assigned before this call to set.")

      computedContainer.setter()(value)
      assigned = true
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
        if(isNotNullOrUndefined(delegate.initialValue) && !delegate.assigned) {
          thisDynamic[it] = delegate.initialValue
        }
        else if(isNotNullOrUndefined(delegate.computedContainer)) {
          delegate.propertyName = it
          computed[it] = (delegate.computedContainer as ComputedContainer<*>).finalize()
        }
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