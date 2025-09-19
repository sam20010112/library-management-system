import { createRouter, createWebHistory } from 'vue-router'
import store from '../store'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue'),
    meta: { requiresGuest: true }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue'),
    meta: { requiresGuest: true }
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('../views/Dashboard.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/books',
    name: 'Books',
    component: () => import('../views/Books.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/borrowing',
    name: 'Borrowing',
    component: () => import('../views/Borrowing.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('../views/Profile.vue'),
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// Navigation guards
router.beforeEach((to, from, next) => {
  const isLoggedIn = store.getters['auth/isLoggedIn']
  console.log('Navigation guard - to:', to.path, 'isLoggedIn:', isLoggedIn)

  if (to.meta.requiresAuth && !isLoggedIn) {
    console.log('Redirecting to login - requires auth but not logged in')
    next('/login')
  } else if (to.meta.requiresGuest && isLoggedIn) {
    console.log('Redirecting to dashboard - logged in but trying to access guest page')
    next('/dashboard')
  } else {
    console.log('Navigation allowed')
    next()
  }
})

export default router
