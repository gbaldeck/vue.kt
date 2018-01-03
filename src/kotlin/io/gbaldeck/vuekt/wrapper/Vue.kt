package io.gbaldeck.vuekt.wrapper

interface VueCommon
fun VueCommon._name() = this::class.simpleName!!.camelToDashCase().toLowerCase()

object Vue{
  infix fun component(vueComponent: VueComponent){
    val component = vueComponent.asDynamic()
    val ownNames = js("Object").getOwnPropertyNames(component) as Array<String>
    val protoNames = js("Object").getOwnPropertyNames(component.constructor.prototype) as Array<String>
    println(ownNames)
    println(protoNames)

    val definition = createJsObject<dynamic>()
    val data = createJsObject<dynamic>()
    definition.methods = createJsObject<dynamic>()
    definition.computed = createJsObject<dynamic>()
    definition.watches = createJsObject<dynamic>()
    definition.props = arrayOf<String>().asDynamic()

    val lifeCycleHooks = arrayOf("beforeCreate","created","beforeMount",
      "mounted","beforeUpdate","updated","activated","deactivated","beforeDestroy","destroyed","errorCaptured")

    val protoNamesList = protoNames.toMutableList()

    protoNamesList.removeAll(arrayOf("constructor", "template", "elementName"))
    protoNamesList.removeAll(lifeCycleHooks)

    ownNames.forEach {
      if(component[it] !is VueKtDelegate) {
        val dataKey = stripGeneratedPostfix(it)

        if(dataKey != "template" && dataKey != "elementName")
          data[dataKey] = component[it]

      } else {
        val delegatePropertyKey = stripGeneratedPostfix(it)

        when(component[it]) {
          is VueComponent.Computed<*> -> {
            val (propertyName, methodName) = component[delegatePropertyKey] as Pair<String, String>
            definition.computed[propertyName] = component[methodName]
            protoNamesList.remove(methodName)
          }
          is VueComponent.Watch<*> -> {
            val (propertyName, propertyValue, methodName) = component[delegatePropertyKey] as Triple<String, dynamic, String>

            data[propertyName] = propertyValue
            definition.watches[propertyName] = component[methodName]
            protoNamesList.remove(methodName)
          }
          is VueComponent.Ref<*> -> {
            console.log(delegatePropertyKey)
            console.log(component[delegatePropertyKey])
            val (propertyName, refComputedFun) = component[delegatePropertyKey] as Pair<String, dynamic>
            definition.computed[propertyName] = refComputedFun
          }
          is VueComponent.Prop<*> -> {
            val propName = component[delegatePropertyKey]
            definition.props.push(propName)
          }

        }
        protoNamesList.remove(delegatePropertyKey)
      }
    }

    protoNamesList.forEach {
      definition.methods[it] = component[it]
    }

    definition.data = {
      js("Object").assign(createJsObject(), data)
    }

    lifeCycleHooks.forEach {
      definition[it] = component[it]
    }

    definition.render = component.template.render
    definition.staticRenderFns = component.template.staticRenderFns
    Communicator.setComponentDefinition(vueComponent.elementName, definition)
  }

  infix fun directive(vueDirective: VueDirective){
    val directive = vueDirective.asDynamic()
    val protoNames = js("Object").getOwnPropertyNames(directive.constructor.prototype) as Array<String>

    val lifeCycleHooks = arrayOf("bind", "inserted", "update", "componentUpdated", "unbind")

    val definition = createJsObject<dynamic>()
    lifeCycleHooks.forEach {
      definition[it] = directive[it]
    }

    Communicator.setDirectiveDefinition(vueDirective.name, definition)
  }

  infix fun filter(vueFilter: VueFilter){
    val filter = vueFilter.asDynamic()
    val filterFun = filter[vueFilter.filter.name]

    Communicator.setFilterFunction(vueFilter.name, filterFun)
  }
}

fun stripGeneratedPostfix(name: String): String{
  if(name.matches("^[.\\S]+$postfixRegex\$")){
    val subIt = name.substringBeforeLast("$")
    return subIt.substringBeforeLast("_")
  }

  return name
}

private val postfixRegex = "_[.\\S]+[\$](?:_\\d)?"
//
//fun findSpecificNamesWithPostfix(searchArr: Array<String>, names: Array<String>): Array<String>{
//  val arr = mutableListOf<String>()
//
//  names.forEach {
//    name ->
//    val item = searchArr.find { it.matches("^$name(?:$postfixRegex)?\$") }
//
//    item?.let{ arr.add(it) }
//  }
//
//  return arr.toTypedArray()
//}
//

