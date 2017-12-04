# Vue.kt Starter

This is a starter for using Vue and Kotlin in a PWA. It's based on the Vue pwa starter that you can generate with the Vue CLI. 

##Structure
There is a shell that is loaded first (App.vue) then the rest of the app (src/kotlin) is pre-loaded and cached after that. The Kotlin DCE is being used to shrink the bundle size and eliminate dead code.

Communicator.js is used to communicate between the shell and the rest of the app in order to lazily load the Vue components bundled in the app.

In the package io.gbaldeck.vuekt.external you will find the wrapper for Vue. It allows you to take advantage of everything Kotlin has to offer while using Vue.

Take a look at TestComponent.kt to see it in action. Note that the Vue wrapper is a work in progress and is not completed. I'll be updating the documentation more and more as I get closer to finishing it.

I included Framework 7 so you can hit the ground running to make a mobile app.

After updating Framework 7 or doing the initial npm install make sure to do a `npm run build`. This copies several files needed by Framework 7 into the project's static folder.


## Build Setup

``` bash
# install dependencies
npm install

# build Kotlin source with grade (uses gradle 4.2)
gradle build

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report
```

For detailed explanation on how things work, checkout the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).
