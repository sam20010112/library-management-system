<template>
  <div class="register-container">
    <el-card class="register-card">
      <template #header>
        <div class="card-header">
          <el-icon><User /></el-icon>
          <span>建立帳號</span>
        </div>
      </template>

      <el-form
        ref="registerForm"
        label-width="120px"
        class="register-form"
      >
        <el-form-item label="手機號碼">
          <el-input
            v-model="phoneNumber"
            placeholder="請輸入手機號碼"
            maxlength="10"
          >
            <template #prefix>
              <el-icon><Phone /></el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item label="用戶名稱">
          <el-input
            v-model="userName"
            placeholder="請輸入您的姓名"
            maxlength="50"
          >
            <template #prefix>
              <el-icon><User /></el-icon>
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
            @click="handleRegister"
            class="register-button"
          >
            註冊
          </el-button>
        </el-form-item>

        <el-form-item>
          <div class="login-link">
            已經有帳號了？
            <el-link type="primary" @click="goToLogin">
              立即登入
            </el-link>
          </div>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'

export default {
  name: 'Register',
  setup() {
    const router = useRouter()
    const store = useStore()

    const phoneNumber = ref('')
    const userName = ref('')
    const password = ref('')
    const loading = ref(false)

    // Debug: Watch form values
    watch([phoneNumber, userName, password], ([newPhone, newUser, newPass]) => {
      console.log('Form values changed:', { 
        phoneNumber: newPhone, 
        userName: newUser, 
        password: newPass
      })
    })



    const handleRegister = async () => {
      try {
        loading.value = true
        const registerData = {
          phoneNumber: phoneNumber.value,
          userName: userName.value,
          password: password.value
        }
        const result = await store.dispatch('auth/register', registerData)

        if (result.success) {
          ElMessage.success(result.message)
          router.push('/login')
        } else {
          ElMessage.error(result.message)
        }
      } catch (error) {
        ElMessage.error('註冊失敗，請重試')
      } finally {
        loading.value = false
      }
    }

    const goToLogin = () => {
      router.push('/login')
    }


    return {
      phoneNumber,
      userName,
      password,
      loading,
      handleRegister,
      goToLogin
    }
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.register-card {
  width: 450px;
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

.register-form {
  padding: 20px;
}

.register-button {
  width: 100%;
  height: 40px;
  font-size: 16px;
}

.login-link {
  text-align: center;
  width: 100%;
}
</style>
