<template>
  <a-config-provider :locale="zh_CN">
    <div id="app">
     <router-view />
    </div>
  </a-config-provider>
</template>

<script>

export default {
  name: 'app',
  // eslint-disable-next-line @typescript-eslint/explicit-module-boundary-types
  data () {
    return {
    }
  },
  components: {
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  height: 100%;
}
</style>
