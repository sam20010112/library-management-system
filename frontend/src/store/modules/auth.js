import authService from '../../services/authService'

const state = {
  user: null,
  token: localStorage.getItem('token') || null,
  isLoggedIn: !!localStorage.getItem('token'),
  loading: false
}

const mutations = {
  SET_USER(state, user) {
    state.user = user
  },
  SET_TOKEN(state, token) {
    state.token = token
    state.isLoggedIn = !!token
    if (token) {
      localStorage.setItem('token', token)
    } else {
      localStorage.removeItem('token')
    }
  },
  SET_LOADING(state, loading) {
    state.loading = loading
  },
  LOGOUT(state) {
    state.user = null
    state.token = null
    state.isLoggedIn = false
    localStorage.removeItem('token')
  }
}

const actions = {
  async login({ commit }, credentials) {
    try {
      const response = await authService.login(credentials)
      if (response.data.success) {
        commit('SET_TOKEN', response.data.data)
        // Try to get user info, but don't fail login if it fails
        try {
          const userResponse = await authService.getUserInfo()
          if (userResponse.data.success) {
            commit('SET_USER', userResponse.data.data)
          }
        } catch (userInfoError) {
          console.warn('Failed to get user info:', userInfoError)
          // Continue with login even if user info fails
        }
        return { success: true, message: response.data.message }
      } else {
        return { success: false, message: response.data.message }
      }
    } catch (error) {
      return {
        success: false,
        message: error.response?.data?.message || 'Login failed'
      }
    }
  },

  async register({ commit }, userData) {
    try {
      const response = await authService.register(userData)
      if (response.data.success) {
        return { success: true, message: response.data.message }
      } else {
        return { success: false, message: response.data.message }
      }
    } catch (error) {
      return {
        success: false,
        message: error.response?.data?.message || 'Registration failed'
      }
    }
  },

  async logout({ commit }) {
    commit('LOGOUT')
  },

  async getUserInfo({ commit }) {
    commit('SET_LOADING', true)
    try {
      const response = await authService.getUserInfo()
      if (response.data.success) {
        commit('SET_USER', response.data.data)
        return { success: true, data: response.data.data }
      } else {
        return { success: false, message: response.data.message }
      }
    } catch (error) {
      return {
        success: false,
        message: error.response?.data?.message || 'Failed to get user info'
      }
    } finally {
      commit('SET_LOADING', false)
    }
  }
}

const getters = {
  isLoggedIn: state => state.isLoggedIn,
  user: state => state.user,
  token: state => state.token,
  loading: state => state.loading
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
}
