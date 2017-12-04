

const Communicator = {
  components: {},
  setComponentResolver: (tagName, resolveFn) => {
    Communicator.components[tagName] = (componentDef) => {
      resolveFn(componentDef)
    }
  },
  resolveComponent: (tagName, componentDef) => {
    Communicator.components[tagName](componentDef)
  }
}

export default Communicator
