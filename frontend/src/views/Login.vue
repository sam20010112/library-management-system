<template>
  <div class="login-container">
    <el-card class="login-card">
      <template #header>
        <div class="card-header">
          <el-icon><Reading /></el-icon>
          <span>圖書館管理系統</span>
        </div>
      </template>

      <el-form
        ref="loginForm"
        :model="loginForm"
        label-width="100px"
        class="login-form"
      >
        <el-form-item label="手機號碼">
          <el-input
            v-model="phoneNumber"
            placeholder="請輸入手機號碼"
            maxlength="10"
            clearable
          >
            <template #prefix>
              <el-icon><Phone /></el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item label="密碼">
          <el-input
            v-model="password"
            type="password"
            placeholder="請輸入密碼"
            show-password
          >
            <template #prefix>
              <el-icon><Lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            :loading="loading"
            @click="handleLogin"
            class="login-button"
          >
            登入
          </el-button>
        </el-form-item>

        <el-form-item>
          <div class="register-link">
            還沒有帳號？
            <el-link type="primary" @click="goToRegister">
              立即註冊
            </el-link>
          </div>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { ref, reactive, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'

export default {
  name: 'Login',
  setup() {
    const router = useRouter()
    const store = useStore()

    const phoneNumber = ref('')
    const password = ref('')

    const loading = ref(false)

    // Debug: Watch form values
    watch([phoneNumber, password], ([newPhone, newPassword]) => {
      console.log('Form values changed:', { phoneNumber: newPhone, password: newPassword })
    })

    const rules = {
      phoneNumber: [
        { required: true, message: '請輸入手機號碼', trigger: 'blur' }
      ],
      password: [
        { required: true, message: '請輸入密碼', trigger: 'blur' },
        { min: 6, message: '密碼至少需要6個字元', trigger: 'blur' }
      ]
    }

    const handleLogin = async () => {
      try {
        loading.value = true
        const loginData = {
          phoneNumber: phoneNumber.value,
          password: password.value
        }
        console.log('Starting login process...')
        const result = await store.dispatch('auth/login', loginData)
        console.log('Login result:', result)
        console.log('Store state after login:', store.getters['auth/isLoggedIn'])

        if (result.success) {
          ElMessage.success(result.message)
          console.log('Login successful, attempting to navigate to dashboard...')
          await router.push('/dashboard')
          console.log('Navigation completed')
        } else {
          ElMessage.error(result.message)
        }
      } catch (error) {
        console.error('Login error:', error)
        ElMessage.error('登入失敗，請重試')
      } finally {
        loading.value = false
      }
    }

    const goToRegister = () => {
      router.push('/register')
    }


    return {
      phoneNumber,
      password,
      loading,
      rules,
      handleLogin,
      goToRegister
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
  width: 400px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  font-weight: bold;
  color: #409EFF;
}

.card-header .el-icon {
  margin-right: 8px;
  font-size: 24px;
}

.login-form {
  padding: 20px;
}

.login-button {
  width: 100%;
  height: 40px;
  font-size: 16px;
}

.register-link {
  text-align: center;
  width: 100%;
}
</style>
