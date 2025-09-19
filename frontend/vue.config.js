const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false, // 添加這行來禁用 ESLint
  devServer: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
        logLevel: 'debug' // 啟用詳細的代理日誌
      }
    },
    client: {
      logging: 'verbose', // 啟用詳細的客戶端日誌
      overlay: {
        errors: true,
        warnings: true
      }
    }
  },
  configureWebpack: {
    devtool: 'source-map' // 啟用源映射以獲得更詳細的錯誤信息
  }
})