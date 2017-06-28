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

  protected inner class ComputedContainer<T>{
    val backingObject = newObject()

    var getter: () -> T
      get() = backingObject["get"]
      set(value) { backingObject["get"] = value }

    var setter: (T) -> Unit
      get() = backingObject["set"]
      set(value) { backingObject["set"] = value }
  }

  protected inner class Computed<T>(val getter: KProperty<() -> T>,
                                    val setter: (KProperty<(T) -> Unit>)? = null,
                                    val singleAssign: Boolean = false) {

    private val dynVueComp:dynamic = this@VueComponent
    private var getterAssigned: Boolean = false
    private var setterAssigned: Boolean = false

    val computed = ComputedContainer<T>()

    constructor(getter: KProperty<() -> T>, singleAssign: Boolean): this(getter, null, singleAssign)

    init {
      if(isNotNullOrUndefined(dynVueComp[getter.name]))
        computed.getter = dynVueComp[getter.name]

      setter?.let {
        if(isNotNullOrUndefined(dynVueComp[it.name]))
          computed.setter = dynVueComp[it.name]
      }

      val handler = ProxyHandler()
      handler.set = {
        target, property, value, receiver ->
        if(property == getter.name)
          computed.getter = value

        setter?.let {
          if(property == it.name)
            computed.setter = value
        }
        target[property] = value
        true
      }
      newProxy(this@VueComponent, handler)
    }

    operator fun getValue(thisRef: Any, propertyLocal: KProperty<*>): T {
      if(isNullOrUndefined(computed.getter))
        throwVueKtException("The getter property '${getter.name}' for Vue computed property '${propertyLocal.name}' in '${thisRef::class.simpleName}' has not been assigned yet.")

      return computed.getter()
    }

    operator fun setValue(thisRef: Any, propertyLocal: KProperty<*>, value: T) {
      setter?.let {
        it(value)
      } ?: throwVueKtException("The Vue ${this::class.simpleName?.toLowerCase() ?: ""} property '${propertyLocal.name}' in '${thisRef::class.simpleName}' was not assigned a setter.")
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
        else if(isNotNullOrUndefined(delegate.computed))
          computed[it] = delegate.computed.backingObject
      }
    }
  }

  open internal fun getActual(): dynamic{
    setDelegateInitialValues()

    val actual = newObject()
    actual.created = {
      vueThis = js("this")
      console.log("vueThis: ", vueThis)
      created()
    }

    dataFunction?.let {
      val dynamicIt: dynamic = it
      actual.data = { (dynamicIt() as VueData).backingObject }
    } ?: run {
      actual.data = { data.backingObject }
    }

    console.log("computed: ", computed.backingObject)
    actual.computed = computed.backingObject
    actual.watch = watch.backingObject
    actual.methods = methods.backingObject
    templateImport(actual)

    return actual
  }
}