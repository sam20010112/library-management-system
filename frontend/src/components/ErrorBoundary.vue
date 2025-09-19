<template>
  <div v-if="hasError" class="error-boundary">
    <div class="error-container">
      <h2>🚨 應用程式錯誤</h2>
      <div class="error-details">
        <h3>錯誤訊息:</h3>
        <p class="error-message">{{ error.message }}</p>
        
        <h3>錯誤堆疊:</h3>
        <pre class="error-stack">{{ error.stack }}</pre>
        
        <h3>錯誤信息:</h3>
        <p class="error-info">{{ errorInfo }}</p>
        
        <div class="error-actions">
          <button @click="reloadPage" class="reload-btn">重新載入頁面</button>
          <button @click="goHome" class="home-btn">返回首頁</button>
          <button @click="reportError" class="report-btn">報告錯誤</button>
        </div>
      </div>
    </div>
  </div>
  <slot v-else />
</template>

<script>
import logger from '../utils/logger'

export default {
  name: 'ErrorBoundary',
  data() {
    return {
      hasError: false,
      error: null,
      errorInfo: null
    }
  },
  errorCaptured(err, vm, info) {
    this.hasError = true
    this.error = err
    this.errorInfo = info
    
    // 記錄錯誤到控制台和日誌
    logger.error('ErrorBoundary caught an error', err, {
      component: vm?.$options.name || 'Unknown',
      info: info
    })
    
    // 阻止錯誤繼續傳播
    return false
  },
  methods: {
    reloadPage() {
      window.location.reload()
    },
    goHome() {
      this.$router.push('/')
      this.resetError()
    },
    reportError() {
      // 這裡可以實現錯誤報告功能
      console.group('🐛 Error Report')
      console.error('Error:', this.error)
      console.error('Info:', this.errorInfo)
      console.error('User Agent:', navigator.userAgent)
      console.error('URL:', window.location.href)
      console.groupEnd()
      
      alert('錯誤已記錄到控制台，請查看詳細信息')
    },
    resetError() {
      this.hasError = false
      this.error = null
      this.errorInfo = null
    }
  }
}
</script>

<style scoped>
.error-boundary {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f5f5f5;
  padding: 20px;
}

.error-container {
  max-width: 800px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 30px;
}

.error-container h2 {
  color: #e74c3c;
  margin-bottom: 20px;
  font-size: 24px;
}

.error-container h3 {
  color: #333;
  margin-top: 20px;
  margin-bottom: 10px;
  font-size: 16px;
}

.error-message {
  background-color: #f8f9fa;
  border-left: 4px solid #e74c3c;
  padding: 15px;
  margin: 10px 0;
  border-radius: 4px;
  font-family: monospace;
}

.error-stack {
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
  padding: 15px;
  margin: 10px 0;
  border-radius: 4px;
  font-size: 12px;
  overflow-x: auto;
  max-height: 300px;
  overflow-y: auto;
}

.error-info {
  background-color: #fff3cd;
  border-left: 4px solid #ffc107;
  padding: 15px;
  margin: 10px 0;
  border-radius: 4px;
}

.error-actions {
  margin-top: 30px;
  display: flex;
  gap: 15px;
  flex-wrap: wrap;
}

.error-actions button {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
  transition: background-color 0.3s;
}

.reload-btn {
  background-color: #007bff;
  color: white;
}

.reload-btn:hover {
  background-color: #0056b3;
}

.home-btn {
  background-color: #28a745;
  color: white;
}

.home-btn:hover {
  background-color: #1e7e34;
}

.report-btn {
  background-color: #6c757d;
  color: white;
}

.report-btn:hover {
  background-color: #545b62;
}
</style>
