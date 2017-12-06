

const Communicator = {
  components: {},
  directives: {},
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
  },
  setDirectiveReceiver: (name, receiver) => {
    if(Communicator.directives[name])
      Object.assign(receiver, Communicator.directives[name])
    else
      Communicator.directives[name] = receiver
  },
  setDirectiveDefinition: (name, directiveDef) => {
    if(Communicator.directives[name])
      Object.assign(Communicator.directives[name], directiveDef)
    else
      Communicator.directives[name] = directiveDef
  }
}

export default Communicator
