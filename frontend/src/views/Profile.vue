<template>
  <div class="profile">
    <el-row :gutter="20">
      <el-col :span="24">
        <div class="page-header">
          <div class="header-content">
            <h1>個人資料</h1>
            <p>查看和管理您的帳戶資訊。</p>
          </div>
          <div class="header-actions">
            <el-button type="success" @click="goToDashboard">
              <el-icon><House /></el-icon>
              回到儀表板
            </el-button>
            <el-button type="warning" @click="logout">
              <el-icon><SwitchButton /></el-icon>
              登出
            </el-button>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <el-icon><User /></el-icon>
              <span>帳戶資訊</span>
            </div>
          </template>

          <div v-if="loading" class="loading">
            <el-icon class="is-loading"><Loading /></el-icon>
            載入個人資料中...
          </div>

          <div v-else-if="user" class="profile-content">
            <el-descriptions :column="1" border>
              <el-descriptions-item label="用戶 ID">
                {{ user.userId }}
              </el-descriptions-item>
              <el-descriptions-item label="手機號碼">
                {{ user.phoneNumber }}
              </el-descriptions-item>
              <el-descriptions-item label="用戶名稱">
                {{ user.userName }}
              </el-descriptions-item>
              <el-descriptions-item label="註冊時間">
                {{ formatDate(user.registrationTime) }}
              </el-descriptions-item>
              <el-descriptions-item label="最後登入時間">
                {{ formatDate(user.lastLoginTime) }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <el-icon><DataAnalysis /></el-icon>
              <span>快速統計</span>
            </div>
          </template>

          <div v-if="stats" class="quick-stats">
            <div class="stat-item">
              <div class="stat-icon">
                <el-icon><Clock /></el-icon>
              </div>
              <div class="stat-info">
                <div class="stat-number">{{ stats.activeBorrowings }}</div>
                <div class="stat-label">借閱中書籍</div>
              </div>
            </div>

            <div class="stat-item">
              <div class="stat-icon">
                <el-icon><List /></el-icon>
              </div>
              <div class="stat-info">
                <div class="stat-number">{{ stats.totalBorrowings }}</div>
                <div class="stat-label">總借閱次數</div>
              </div>
            </div>

            <div class="stat-item">
              <div class="stat-icon">
                <el-icon><Check /></el-icon>
              </div>
              <div class="stat-info">
                <div class="stat-number">{{ stats.returnedBorrowings }}</div>
                <div class="stat-label">已歸還書籍</div>
              </div>
            </div>
          </div>

          <div v-else class="loading">
            <el-icon class="is-loading"><Loading /></el-icon>
            載入統計資料中...
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'

export default {
  name: 'Profile',
  setup() {
    const router = useRouter()
    const store = useStore()

    const user = computed(() => store.getters['auth/user'])
    const stats = computed(() => store.getters['borrowing/stats'])
    const loading = computed(() => store.getters['auth/loading'] || store.getters['borrowing/loading'])

    const formatDate = (dateString) => {
      if (!dateString) return '從未'
      const date = new Date(dateString)
      return date.toLocaleDateString() + ' ' + date.toLocaleTimeString()
    }


    const goToDashboard = () => {
      router.push('/dashboard')
    }

    const logout = () => {
      store.dispatch('auth/logout')
      ElMessage.success('Logged out successfully')
      router.push('/login')
    }

    onMounted(async () => {
      await Promise.all([
        store.dispatch('auth/getUserInfo'),
        store.dispatch('borrowing/fetchStats')
      ])
    })

    return {
      user,
      stats,
      loading,
      formatDate,
      goToDashboard,
      logout
    }
  }
}
</script>

<style scoped>
.profile {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 20px;
}

.header-content h1 {
  margin: 0 0 8px 0;
}

.header-content p {
  margin: 0;
  color: #909399;
}

.header-actions {
  display: flex;
  gap: 12px;
  flex-shrink: 0;
}

.card-header {
  display: flex;
  align-items: center;
  font-weight: bold;
  color: #303133;
}

.card-header .el-icon {
  margin-right: 8px;
  color: #409EFF;
}

.loading {
  text-align: center;
  padding: 40px;
  color: #909399;
}

.profile-content {
  padding: 20px 0;
}

.quick-stats {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.stat-item {
  display: flex;
  align-items: center;
  padding: 12px;
  background-color: #f8f9fa;
  border-radius: 8px;
}

.stat-icon {
  font-size: 24px;
  color: #409EFF;
  margin-right: 12px;
}

.stat-info {
  flex: 1;
}

.stat-number {
  font-size: 20px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 4px;
}

.stat-label {
  font-size: 12px;
  color: #909399;
}

</style>
