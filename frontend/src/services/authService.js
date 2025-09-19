import api from './api'

const authService = {
  async login(credentials) {
    return await api.post('/auth/login', credentials)
  },

  async register(userData) {
    return await api.post('/auth/register', userData)
  },

  async getUserInfo() {
    return await api.get('/auth/user-info')
  },

  async logout() {
    // Clear token from localStorage
    localStorage.removeItem('token')
  }
}

export default authService
