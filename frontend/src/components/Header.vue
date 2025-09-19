<template>
  <el-header>
    <div class="header-content">
      <div class="logo">
        <el-icon><Reading /></el-icon>
        Library Management System
      </div>
      <div class="nav-menu">
        <el-menu
          mode="horizontal"
          :default-active="activeMenu"
          @select="handleMenuSelect"
          background-color="#409EFF"
          text-color="#fff"
          active-text-color="#ffd04b"
        >
          <el-menu-item index="/dashboard">
            <el-icon><House /></el-icon>
            Dashboard
          </el-menu-item>
          <el-menu-item index="/books">
            <el-icon><Reading /></el-icon>
            Books
          </el-menu-item>
          <el-menu-item index="/borrowing">
            <el-icon><List /></el-icon>
            My Borrowing
          </el-menu-item>
          <el-menu-item index="/profile">
            <el-icon><User /></el-icon>
            Profile
          </el-menu-item>
        </el-menu>
      </div>
      <div class="user-info">
        <el-dropdown @command="handleCommand">
          <span class="user-dropdown">
            <el-icon><User /></el-icon>
            {{ user?.userName || 'User' }}
            <el-icon class="el-icon--right"><ArrowDown /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="profile">Profile</el-dropdown-item>
              <el-dropdown-item command="logout" divided>Logout</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </el-header>
</template>

<script>
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useStore } from 'vuex'

export default {
  name: 'Header',
  setup() {
    const router = useRouter()
    const route = useRoute()
    const store = useStore()

    const user = computed(() => store.getters['auth/user'])
    const activeMenu = computed(() => route.path)

    const handleMenuSelect = (index) => {
      router.push(index)
    }

    const handleCommand = (command) => {
      if (command === 'profile') {
        router.push('/profile')
      } else if (command === 'logout') {
        store.dispatch('auth/logout')
        router.push('/login')
      }
    }

    return {
      user,
      activeMenu,
      handleMenuSelect,
      handleCommand
    }
  }
}
</script>

<style scoped>
.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
}

.logo {
  display: flex;
  align-items: center;
  font-size: 20px;
  font-weight: bold;
  color: white;
}

.logo .el-icon {
  margin-right: 8px;
  font-size: 24px;
}

.nav-menu {
  flex: 1;
  margin: 0 20px;
}

.user-info {
  color: white;
}

.user-dropdown {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 0 12px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.user-dropdown:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.user-dropdown .el-icon {
  margin: 0 4px;
}
</style>
