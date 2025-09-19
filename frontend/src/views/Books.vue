<template>
  <div class="books">
    <el-row :gutter="20">
      <el-col :span="24">
        <div class="page-header">
          <div class="header-content">
            <h1>所有藏書</h1>
            <p>你想借什麼書呢？抱歉我們只有程式書</p>
          </div>
          <el-button 
            type="primary" 
            @click="goToDashboard"
            class="back-button"
          >
            <el-icon><ArrowLeft /></el-icon>
            回到儀表板
          </el-button>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="24">
        <el-card>

          <div v-if="loading" class="loading">
            <el-icon class="is-loading"><Loading /></el-icon>
            載入書籍中...
          </div>

          <div v-else-if="allBooks.length === 0" class="empty">
            <el-icon><Reading /></el-icon>
            <p>找不到書籍</p>
          </div>

          <div v-else class="books-grid">
            <el-card
              v-for="book in allBooks"
              :key="book.inventoryId || book.isbn"
              class="book-card"
              shadow="hover"
            >
              <div class="book-content">
                <div class="book-info">
                  <h3 class="book-title">{{ book.name }}</h3>
                  <p class="book-author">作者：{{ book.author }}</p>
                  <p class="book-isbn">ISBN: {{ book.isbn }}</p>
                  <p class="book-introduction" v-if="book.introduction">
                    {{ book.introduction.substring(0, 100) }}{{ book.introduction.length > 100 ? '...' : '' }}
                  </p>
                </div>

                <div class="book-actions">
                  <el-tag
                    :type="book.status === 'AVAILABLE' ? 'success' : 'info'"
                    class="status-tag"
                  >
                    {{ book.status === 'AVAILABLE' ? '可借閱' : '不可借閱' }}
                  </el-tag>

                  <el-button
                    v-if="book.status === 'AVAILABLE' && book.inventoryId"
                    type="primary"
                    @click="borrowBook(book.inventoryId)"
                    :loading="borrowingLoading"
                    class="borrow-button"
                  >
                    <el-icon><Plus /></el-icon>
                    借閱
                  </el-button>

                  <el-button
                    v-else
                    type="info"
                    disabled
                    class="borrow-button"
                  >
                    <el-icon><Lock /></el-icon>
                    不可借閱
                  </el-button>
                </div>
              </div>
            </el-card>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

export default {
  name: 'Books',
  setup() {
    const store = useStore()
    const router = useRouter()

    const borrowingLoading = ref(false)

    const allBooks = computed(() => store.getters['books/books'])
    const loading = computed(() => store.getters['books/loading'])


    const borrowBook = async (inventoryId) => {
      try {
        borrowingLoading.value = true
        const result = await store.dispatch('borrowing/borrowBook', inventoryId)

        if (result.success) {
          ElMessage.success(result.message)
          // Refresh all books
          await store.dispatch('books/fetchBooks')
        } else {
          ElMessage.error(result.message)
        }
      } catch (error) {
        console.error('借閱書籍錯誤:', error)
        ElMessage.error('借閱書籍失敗: ' + (error.response?.data?.message || error.message))
      } finally {
        borrowingLoading.value = false
      }
    }

    const goToDashboard = () => {
      router.push('/dashboard')
    }

    onMounted(async () => {
      await store.dispatch('books/fetchBooks')
    })

    return {
      borrowingLoading,
      allBooks,
      loading,
      borrowBook,
      goToDashboard
    }
  }
}
</script>

<style scoped>
.books {
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

.back-button {
  margin-left: 20px;
  flex-shrink: 0;
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

.books-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

.book-card {
  transition: transform 0.2s;
}

.book-card:hover {
  transform: translateY(-2px);
}

.book-content {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.book-info {
  flex: 1;
  margin-bottom: 16px;
}

.book-title {
  font-size: 18px;
  font-weight: bold;
  color: #303133;
  margin: 0 0 8px 0;
  line-height: 1.4;
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

.book-introduction {
  font-size: 14px;
  color: #606266;
  line-height: 1.5;
  margin: 0;
}

.book-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: auto;
}

.status-tag {
  font-size: 12px;
}

.borrow-button {
  min-width: 100px;
}
</style>
