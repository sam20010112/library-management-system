import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)

// 全域錯誤處理
app.config.errorHandler = (err, vm, info) => {
  console.group('🚨 Vue Global Error')
  console.error('Error:', err)
  console.error('Component:', vm)
  console.error('Info:', info)
  console.groupEnd()
}

// 全域警告處理
app.config.warnHandler = (msg, vm, trace) => {
  console.group('⚠️ Vue Warning')
  console.warn('Message:', msg)
  console.warn('Component:', vm)
  console.warn('Trace:', trace)
  console.groupEnd()
}

// Register Element Plus icons
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.use(store)
app.use(router)
app.use(ElementPlus)
app.mount('#app')
