/**
 * 前端日誌工具類
 * 提供統一的錯誤日誌記錄和顯示功能
 */

class Logger {
  constructor() {
    this.isDevelopment = process.env.NODE_ENV === 'development'
  }

  /**
   * 記錄錯誤
   */
  error(message, error = null, context = {}) {
    console.group('🚨 Error Log')
    console.error('Message:', message)
    if (error) {
      console.error('Error Object:', error)
      console.error('Error Stack:', error.stack)
    }
    console.error('Context:', context)
    console.error('Timestamp:', new Date().toISOString())
    console.groupEnd()
  }

  /**
   * 記錄警告
   */
  warn(message, context = {}) {
    console.group('⚠️ Warning Log')
    console.warn('Message:', message)
    console.warn('Context:', context)
    console.warn('Timestamp:', new Date().toISOString())
    console.groupEnd()
  }

  /**
   * 記錄信息
   */
  info(message, context = {}) {
    if (this.isDevelopment) {
      console.group('ℹ️ Info Log')
      console.info('Message:', message)
      console.info('Context:', context)
      console.info('Timestamp:', new Date().toISOString())
      console.groupEnd()
    }
  }

  /**
   * 記錄調試信息
   */
  debug(message, context = {}) {
    if (this.isDevelopment) {
      console.group('🐛 Debug Log')
      console.debug('Message:', message)
      console.debug('Context:', context)
      console.debug('Timestamp:', new Date().toISOString())
      console.groupEnd()
    }
  }

  /**
   * 記錄 API 請求
   */
  apiRequest(method, url, data = null) {
    if (this.isDevelopment) {
      console.group(`🌐 API Request [${method.toUpperCase()}]`)
      console.log('URL:', url)
      console.log('Data:', data)
      console.log('Timestamp:', new Date().toISOString())
      console.groupEnd()
    }
  }

  /**
   * 記錄 API 響應
   */
  apiResponse(method, url, response, duration = null) {
    if (this.isDevelopment) {
      console.group(`📡 API Response [${method.toUpperCase()}]`)
      console.log('URL:', url)
      console.log('Status:', response.status)
      console.log('Data:', response.data)
      if (duration) {
        console.log('Duration:', `${duration}ms`)
      }
      console.log('Timestamp:', new Date().toISOString())
      console.groupEnd()
    }
  }

  /**
   * 記錄 API 錯誤
   */
  apiError(method, url, error, duration = null) {
    console.group(`❌ API Error [${method.toUpperCase()}]`)
    console.error('URL:', url)
    console.error('Status:', error.response?.status)
    console.error('Error Message:', error.message)
    console.error('Response Data:', error.response?.data)
    if (duration) {
      console.error('Duration:', `${duration}ms`)
    }
    console.error('Timestamp:', new Date().toISOString())
    console.groupEnd()
  }

  /**
   * 記錄用戶操作
   */
  userAction(action, details = {}) {
    if (this.isDevelopment) {
      console.group('👤 User Action')
      console.log('Action:', action)
      console.log('Details:', details)
      console.log('Timestamp:', new Date().toISOString())
      console.groupEnd()
    }
  }
}

// 創建單例實例
const logger = new Logger()

export default logger
