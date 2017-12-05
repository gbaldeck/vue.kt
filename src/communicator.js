

const Communicator = {
  components: {},
  setComponentResolver: (tagName, resolveFn) => {
    if(Communicator.components[tagName])
      resolveFn(Communicator.components[tagName])
    else
      Communicator.components[tagName] = resolveFn
  },
  setComponentDefinition: (tagName, componentDef) => {
    if(Communicator.components[tagName])
      Communicator.components[tagName](componentDef)
    else
      Communicator.components[tagName] = componentDef
  }
}

export default Communicator
