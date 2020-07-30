<template>
    <div id="app">
        <LoginVue />
    </div>
</template>

<script>
import LoginVue from './components/Login.vue'
LoginVue

export default {
    name: 'App',
    components: {
        LoginVue
    }
}
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height: 100%;
  width: 100%;
}
</style>
