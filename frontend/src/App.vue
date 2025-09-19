<template>
  <div id="app">
    <el-container>
      <el-header v-if="isLoggedIn">
        <Header />
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { computed } from 'vue'
import { useStore } from 'vuex'
import Header from './components/Header.vue'

export default {
  name: 'App',
  components: {
    Header
  },
  setup() {
    const store = useStore()
    const isLoggedIn = computed(() => store.getters.isLoggedIn)

    return {
      isLoggedIn
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

.el-header {
  background-color: #409EFF;
  color: white;
  text-align: center;
  line-height: 60px;
}

.el-main {
  padding: 20px;
  min-height: calc(100vh - 60px);
}
</style>
