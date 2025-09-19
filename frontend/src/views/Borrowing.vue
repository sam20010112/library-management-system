<template>
  <div class="borrowing">
    <el-row :gutter="20">
      <el-col :span="24">
        <h1>My Borrowing</h1>
        <p>Manage your book borrowings and view borrowing history.</p>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="24">
        <el-tabs v-model="activeTab" @tab-click="handleTabClick">
          <el-tab-pane label="Active Borrowings" name="active">
            <el-card>
              <template #header>
                <div class="card-header">
                  <el-icon><Clock /></el-icon>
                  <span>Currently Borrowed Books</span>
                  <el-badge :value="activeBorrowings.length" class="badge" />
                </div>
              </template>

              <div v-if="loading" class="loading">
                <el-icon class="is-loading"><Loading /></el-icon>
                Loading active borrowings...
              </div>

              <div v-else-if="activeBorrowings.length === 0" class="empty">
                <el-icon><Check /></el-icon>
                <p>No active borrowings</p>
              </div>

              <div v-else class="borrowing-list">
                <el-card
                  v-for="borrowing in activeBorrowings"
                  :key="borrowing.inventoryId"
                  class="borrowing-card"
                  shadow="hover"
                >
                  <div class="borrowing-content">
                    <div class="book-info">
                      <h3 class="book-title">{{ borrowing.bookName }}</h3>
                      <p class="book-author">by {{ borrowing.author }}</p>
                      <p class="book-isbn">ISBN: {{ borrowing.isbn }}</p>
                      <div class="borrowing-details">
                        <el-tag type="warning" size="small">
                          <el-icon><Clock /></el-icon>
                          Borrowed on {{ formatDate(borrowing.borrowingTime) }}
                        </el-tag>
                      </div>
                    </div>

                    <div class="borrowing-actions">
                      <el-button
                        type="warning"
                        @click="returnBook(borrowing.inventoryId)"
                        :loading="returningLoading"
                        class="return-button"
                      >
                        <el-icon><RefreshLeft /></el-icon>
                        Return Book
                      </el-button>
                    </div>
                  </div>
                </el-card>
              </div>
            </el-card>
          </el-tab-pane>

          <el-tab-pane label="Borrowing History" name="history">
            <el-card>
              <template #header>
                <div class="card-header">
                  <el-icon><List /></el-icon>
                  <span>Borrowing History</span>
                  <el-badge :value="borrowingHistory.length" class="badge" />
                </div>
              </template>

              <div v-if="loading" class="loading">
                <el-icon class="is-loading"><Loading /></el-icon>
                Loading borrowing history...
              </div>

              <div v-else-if="borrowingHistory.length === 0" class="empty">
                <el-icon><Document /></el-icon>
                <p>No borrowing history</p>
              </div>

              <div v-else class="history-list">
                <el-timeline>
                  <el-timeline-item
                    v-for="record in borrowingHistory"
                    :key="`${record.userId}-${record.inventoryId}`"
                    :timestamp="formatDate(record.borrowingTime)"
                    placement="top"
                  >
                    <el-card class="history-card">
                      <div class="history-content">
                        <div class="book-info">
                          <h4 class="book-title">{{ record.bookName }}</h4>
                          <p class="book-author">by {{ record.author }}</p>
                          <p class="book-isbn">ISBN: {{ record.isbn }}</p>
                        </div>

                        <div class="history-details">
                          <div class="borrow-info">
                            <el-tag type="primary" size="small">
                              <el-icon><Clock /></el-icon>
                              Borrowed: {{ formatDate(record.borrowingTime) }}
                            </el-tag>
                          </div>

                          <div v-if="record.returnTime" class="return-info">
                            <el-tag type="success" size="small">
                              <el-icon><Check /></el-icon>
                              Returned: {{ formatDate(record.returnTime) }}
                            </el-tag>
                          </div>

                          <div v-else class="return-info">
                            <el-tag type="warning" size="small">
                              <el-icon><Clock /></el-icon>
                              Currently Borrowed
                            </el-tag>
                          </div>
                        </div>
                      </div>
                    </el-card>
                  </el-timeline-item>
                </el-timeline>
              </div>
            </el-card>
          </el-tab-pane>

          <el-tab-pane label="Statistics" name="stats">
            <el-card>
              <template #header>
                <div class="card-header">
                  <el-icon><DataAnalysis /></el-icon>
                  <span>Borrowing Statistics</span>
                </div>
              </template>

              <div v-if="!stats" class="loading">
                <el-icon class="is-loading"><Loading /></el-icon>
                Loading statistics...
              </div>

              <div v-else class="stats-grid">
                <el-card class="stat-card">
                  <div class="stat-content">
                    <div class="stat-icon">
                      <el-icon><Clock /></el-icon>
                    </div>
                    <div class="stat-info">
                      <div class="stat-number">{{ stats.activeBorrowings }}</div>
                      <div class="stat-label">Active Borrowings</div>
                    </div>
                  </div>
                </el-card>

                <el-card class="stat-card">
                  <div class="stat-content">
                    <div class="stat-icon">
                      <el-icon><List /></el-icon>
                    </div>
                    <div class="stat-info">
                      <div class="stat-number">{{ stats.totalBorrowings }}</div>
                      <div class="stat-label">Total Borrowings</div>
                    </div>
                  </div>
                </el-card>

                <el-card class="stat-card">
                  <div class="stat-content">
                    <div class="stat-icon">
                      <el-icon><Check /></el-icon>
                    </div>
                    <div class="stat-info">
                      <div class="stat-number">{{ stats.returnedBorrowings }}</div>
                      <div class="stat-label">Returned Books</div>
                    </div>
                  </div>
                </el-card>
              </div>
            </el-card>
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'

export default {
  name: 'Borrowing',
  setup() {
    const store = useStore()

    const activeTab = ref('active')
    const returningLoading = ref(false)

    const activeBorrowings = computed(() => store.getters['borrowing/activeBorrowings'])
    const borrowingHistory = computed(() => store.getters['borrowing/borrowingHistory'])
    const stats = computed(() => store.getters['borrowing/stats'])
    const loading = computed(() => store.getters['borrowing/loading'])

    const formatDate = (dateString) => {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString() + ' ' + date.toLocaleTimeString()
    }

    const returnBook = async (inventoryId) => {
      try {
        returningLoading.value = true
        const result = await store.dispatch('borrowing/returnBook', inventoryId)

        if (result.success) {
          ElMessage.success(result.message)
        } else {
          ElMessage.error(result.message)
        }
      } catch (error) {
        ElMessage.error('Failed to return book')
      } finally {
        returningLoading.value = false
      }
    }

    const handleTabClick = async (tab) => {
      if (tab.name === 'active') {
        await store.dispatch('borrowing/fetchActiveBorrowings')
      } else if (tab.name === 'history') {
        await store.dispatch('borrowing/fetchBorrowingHistory')
      } else if (tab.name === 'stats') {
        await store.dispatch('borrowing/fetchStats')
      }
    }

    onMounted(async () => {
      await Promise.all([
        store.dispatch('borrowing/fetchActiveBorrowings'),
        store.dispatch('borrowing/fetchBorrowingHistory'),
        store.dispatch('borrowing/fetchStats')
      ])
    })

    return {
      activeTab,
      returningLoading,
      activeBorrowings,
      borrowingHistory,
      stats,
      loading,
      formatDate,
      returnBook,
      handleTabClick
    }
  }
}
</script>

<style scoped>
.borrowing {
  padding: 20px;
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

.badge {
  margin-left: 8px;
}

.loading, .empty {
  text-align: center;
  padding: 40px;
  color: #909399;
}

.empty .el-icon {
  font-size: 48px;
  margin-bottom: 16px;
}

.borrowing-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.borrowing-card {
  transition: transform 0.2s;
}

.borrowing-card:hover {
  transform: translateY(-2px);
}

.borrowing-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.book-info {
  flex: 1;
}

.book-title {
  font-size: 18px;
  font-weight: bold;
  color: #303133;
  margin: 0 0 8px 0;
}

.book-author {
  font-size: 14px;
  color: #909399;
  margin: 0 0 4px 0;
}

.book-isbn {
  font-size: 12px;
  color: #C0C4CC;
  margin: 0 0 8px 0;
}

.borrowing-details {
  margin-top: 8px;
}

.return-button {
  min-width: 120px;
}

.history-list {
  max-height: 600px;
  overflow-y: auto;
}

.history-card {
  margin-bottom: 8px;
}

.history-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.history-details {
  display: flex;
  flex-direction: column;
  gap: 8px;
  align-items: flex-end;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
}

.stat-card {
  text-align: center;
}

.stat-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.stat-icon {
  font-size: 40px;
  color: #409EFF;
  margin-bottom: 16px;
}

.stat-number {
  font-size: 32px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 8px;
}

.stat-label {
  font-size: 14px;
  color: #909399;
}
</style>
