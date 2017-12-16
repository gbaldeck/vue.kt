<style src="./styles/global.scss" lang="scss"></style>

<template>
  <div id="app">
    <!--<test-component></test-component>-->
    <ul>
      <router-link to="/" tag="li" active-class="red" exact><a>testComponent</a></router-link>
      <router-link to="/sub/SubComponentOne?locale=noten&q=200" tag="li" active-class="red"><a>subComponentOne</a></router-link>
      <router-link :to="{ name: 'subRoute', params: { name: 'SubComponentTwo' }, query:{ locale: 'en', q: 100}}" tag="li" active-class="red"><a>subComponentTwo</a></router-link>
    </ul>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>
