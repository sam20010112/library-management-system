<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="24" class="title-section">
        <h1>歡迎來到程式書籍圖書館</h1>
        <p>安安, {{ user?.userName || 'User' }}!</p>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="stats-row">
      <el-col :span="7">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon><Reading /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">{{ stats?.activeBorrowings || 0 }}</div>
              <div class="stat-label">借閱中書籍</div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="7">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon><List /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">{{ stats?.totalBorrowings || 0 }}</div>
              <div class="stat-label">總借閱次數</div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="7">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon><Check /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">{{ stats?.returnedBorrowings || 0 }}</div>
              <div class="stat-label">已歸還書籍</div>
            </div>
          </div>
        </el-card>
      </el-col>

    </el-row>

    <!-- Quick Actions Section -->
    <el-row :gutter="20" class="quick-actions-row">
      <el-col :span="24">
        <el-card>
          <template #header>
            <div class="card-header">
              <el-icon><Operation /></el-icon>
              <span>快速操作</span>
            </div>
          </template>
          <div class="quick-actions">
            <el-button type="primary" @click="$router.push('/books')" class="action-button">
              <el-icon><Reading /></el-icon>
              瀏覽圖書
            </el-button>
            <el-button type="info" @click="$router.push('/profile')" class="action-button">
              <el-icon><User /></el-icon>
              我的個人資料
            </el-button>
            <el-button type="warning" @click="logout" class="action-button">
              <el-icon><SwitchButton /></el-icon>
              登出
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <el-icon><Reading /></el-icon>
              <span>可借書籍</span>
            </div>
          </template>
          <div class="book-list">
            <div v-if="loading" class="loading">
              <el-icon class="is-loading"><Loading /></el-icon>
              載入中...
            </div>
            <div v-else-if="availableBooks.length === 0" class="empty">
              暫無可借書籍
            </div>
            <div v-else>
              <div v-for="book in availableBooks.slice(0, 5)" :key="book.inventoryId" class="book-item">
                <div class="book-info">
                  <div class="book-title">{{ book.name }}</div>
                  <div class="book-author">作者：{{ book.author }}</div>
                </div>
                <el-button type="primary" size="small" @click="borrowBook(book.inventoryId)">
                  借閱
                </el-button>
              </div>
              <div v-if="availableBooks.length > 5" class="more-books">
                <el-button type="text" @click="$router.push('/books')">
                  查看全部 {{ availableBooks.length }} 本書籍
                </el-button>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <el-icon><List /></el-icon>
              <span>借閱中的書籍</span>
            </div>
          </template>
          <div class="borrowing-list">
            <div v-if="loading" class="loading">
              <el-icon class="is-loading"><Loading /></el-icon>
              載入中...
            </div>
            <div v-else-if="activeBorrowings.length === 0" class="empty">
              暫無借閱中的書籍
            </div>
            <div v-else>
              <div v-for="borrowing in activeBorrowings.slice(0, 5)" :key="borrowing.inventoryId" class="borrowing-item">
                <div class="borrowing-info">
                  <div class="book-title">{{ borrowing.bookName }}</div>
                  <div class="book-author">作者：{{ borrowing.author }}</div>
                  <div class="borrowing-date">
                    借閱時間：{{ formatDate(borrowing.borrowingTime) }}
                  </div>
                </div>
                <el-button type="warning" size="small" @click="returnBook(borrowing.inventoryId)">
                  歸還
                </el-button>
              </div>
              <div v-if="activeBorrowings.length > 5" class="more-borrowings">
                <el-button type="text" @click="$router.push('/borrowing')">
                  查看全部 {{ activeBorrowings.length }} 本借閱中的書籍
                </el-button>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'

export default {
  name: 'Dashboard',
  setup() {
    const router = useRouter()
    const store = useStore()

    const user = computed(() => store.getters['auth/user'])
    const stats = computed(() => store.getters['borrowing/stats'])
    const availableBooks = computed(() => store.getters['books/availableBooks'])
    const activeBorrowings = computed(() => store.getters['borrowing/activeBorrowings'])
    const loading = computed(() => store.getters['books/loading'] || store.getters['borrowing/loading'])


    const formatDate = (dateString) => {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString()
    }

    const borrowBook = async (inventoryId) => {
      try {
        const result = await store.dispatch('borrowing/borrowBook', inventoryId)
        if (result.success) {
          ElMessage.success(result.message)
        } else {
          ElMessage.error(result.message)
        }
      } catch (error) {
        ElMessage.error('借閱書籍失敗')
      }
    }

    const returnBook = async (inventoryId) => {
      try {
        const result = await store.dispatch('borrowing/returnBook', inventoryId)
        if (result.success) {
          ElMessage.success(result.message)
        } else {
          ElMessage.error(result.message)
        }
      } catch (error) {
        ElMessage.error('歸還書籍失敗')
      }
    }

    const logout = () => {
      store.dispatch('auth/logout')
      ElMessage.success('Logged out successfully')
      router.push('/login')
    }

    onMounted(async () => {
      await Promise.all([
        store.dispatch('books/fetchAvailableBooks'),
        store.dispatch('borrowing/fetchActiveBorrowings'),
        store.dispatch('borrowing/fetchStats')
      ])
    })

    return {
      user,
      stats,
      availableBooks,
      activeBorrowings,
      loading,
      formatDate,
      borrowBook,
      returnBook,
      logout
    }
  }
}
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.title-section {
  text-align: center;
}

.stats-row {
  margin: 20px 0;
  display: flex;
  justify-content: center;
}

.quick-actions-row {
  margin: 20px 0;
}

.quick-actions {
  display: flex;
  gap: 16px;
  flex-wrap: wrap;
  justify-content: center;
}

.action-button {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 24px;
  font-size: 14px;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.action-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.stat-card {
  margin-bottom: 20px;
}

.stat-content {
  display: flex;
  align-items: center;
}

.stat-icon {
  font-size: 40px;
  color: #409EFF;
  margin-right: 16px;
}

.stat-info {
  flex: 1;
}

.stat-number {
  font-size: 32px;
  font-weight: bold;
  color: #303133;
}

.stat-label {
  font-size: 14px;
  color: #909399;
  margin-top: 4px;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  color: #303133;
}

.card-header .el-icon {
  margin-right: 8px;
  color: #409EFF;
}

.book-list, .borrowing-list {
  max-height: 400px;
  overflow-y: auto;
}

.loading, .empty {
  text-align: center;
  padding: 20px;
  color: #909399;
}

.book-item, .borrowing-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.book-item:last-child, .borrowing-item:last-child {
  border-bottom: none;
}

.book-info, .borrowing-info {
  flex: 1;
}

.book-title {
  font-weight: bold;
  color: #303133;
  margin-bottom: 4px;
}

.book-author {
  font-size: 14px;
  color: #909399;
  margin-bottom: 4px;
}

.borrowing-date {
  font-size: 12px;
  color: #909399;
}

.more-books, .more-borrowings {
  text-align: center;
  padding: 10px 0;
  border-top: 1px solid #f0f0f0;
}
</style>
