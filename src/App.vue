<style src="./styles/global.scss" lang="scss"></style>

<template>
  <div id="app">
    <f7-statusbar></f7-statusbar>
    <!-- Views -->
    <f7-views>
      <!-- Your main view, should have "main" prop -->
      <f7-view main>
        <!-- Pages container, because we use fixed navbar and toolbar, it has additional appropriate props -->
        <f7-pages navbar-fixed toolbar-fixed>
          <!-- Initial Page -->
          <f7-page>
            <!-- Top Navbar-->
            <f7-navbar title="Awesome App"></f7-navbar>
            <test-component></test-component>
            <!-- Page Content -->
            <p>Page content goes here</p>
            <f7-link href="/about/">About App</f7-link>
            <!-- Toolbar-->
            <f7-toolbar bottom tabbar labels>
              <f7-link text="Tab1" tab-link="#tab1" icon=""></f7-link>
              <f7-link text="Tab2" tab-link="#tab2" icon=""></f7-link>
              <f7-link text="Tab3" tab-link="#tab3" icon=""></f7-link>
              <f7-link text="Tab4" tab-link="#tab4" icon=""></f7-link>
            </f7-toolbar>
          </f7-page>
        </f7-pages>
      </f7-view>
    </f7-views>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>
