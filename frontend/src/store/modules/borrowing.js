import borrowingService from '../../services/borrowingService'

const state = {
  borrowingHistory: [],
  activeBorrowings: [],
  stats: null,
  loading: false
}

const mutations = {
  SET_BORROWING_HISTORY(state, history) {
    state.borrowingHistory = history
  },
  SET_ACTIVE_BORROWINGS(state, borrowings) {
    state.activeBorrowings = borrowings
  },
  SET_STATS(state, stats) {
    state.stats = stats
  },
  SET_LOADING(state, loading) {
    state.loading = loading
  }
}

const actions = {
  async fetchBorrowingHistory({ commit }) {
    commit('SET_LOADING', true)
    try {
      const response = await borrowingService.getBorrowingHistory()
      if (response.data.success) {
        commit('SET_BORROWING_HISTORY', response.data.data)
        return { success: true, data: response.data.data }
      } else {
        return { success: false, message: response.data.message }
      }
    } catch (error) {
      return {
        success: false,
        message: error.response?.data?.message || 'Failed to fetch borrowing history'
      }
    } finally {
      commit('SET_LOADING', false)
    }
  },

  async fetchActiveBorrowings({ commit }) {
    commit('SET_LOADING', true)
    try {
      const response = await borrowingService.getActiveBorrowings()
      if (response.data.success) {
        commit('SET_ACTIVE_BORROWINGS', response.data.data)
        return { success: true, data: response.data.data }
      } else {
        return { success: false, message: response.data.message }
      }
    } catch (error) {
      return {
        success: false,
        message: error.response?.data?.message || 'Failed to fetch active borrowings'
      }
    } finally {
      commit('SET_LOADING', false)
    }
  },

  async fetchStats({ commit }) {
    try {
      const response = await borrowingService.getStats()
      if (response.data.success) {
        commit('SET_STATS', response.data.data)
        return { success: true, data: response.data.data }
      } else {
        return { success: false, message: response.data.message }
      }
    } catch (error) {
      return {
        success: false,
        message: error.response?.data?.message || 'Failed to fetch stats'
      }
    }
  },

  async borrowBook({ dispatch }, inventoryId) {
    try {
      const response = await borrowingService.borrowBook(inventoryId)
      if (response.data.success) {
        // Refresh data
        await dispatch('fetchActiveBorrowings')
        await dispatch('fetchStats')
        return { success: true, message: response.data.message }
      } else {
        return { success: false, message: response.data.message }
      }
    } catch (error) {
      return {
        success: false,
        message: error.response?.data?.message || 'Failed to borrow book'
      }
    }
  },

  async returnBook({ dispatch }, inventoryId) {
    try {
      const response = await borrowingService.returnBook(inventoryId)
      if (response.data.success) {
        // Refresh data
        await dispatch('fetchActiveBorrowings')
        await dispatch('fetchBorrowingHistory')
        await dispatch('fetchStats')
        return { success: true, message: response.data.message }
      } else {
        return { success: false, message: response.data.message }
      }
    } catch (error) {
      return {
        success: false,
        message: error.response?.data?.message || 'Failed to return book'
      }
    }
  }
}

const getters = {
  borrowingHistory: state => state.borrowingHistory,
  activeBorrowings: state => state.activeBorrowings,
  stats: state => state.stats,
  loading: state => state.loading
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
}
