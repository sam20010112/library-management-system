import bookService from '../../services/bookService'

const state = {
  books: [],
  availableBooks: [],
  searchResults: [],
  loading: false
}

const mutations = {
  SET_BOOKS(state, books) {
    state.books = books
  },
  SET_AVAILABLE_BOOKS(state, books) {
    state.availableBooks = books
  },
  SET_SEARCH_RESULTS(state, books) {
    state.searchResults = books
  },
  SET_LOADING(state, loading) {
    state.loading = loading
  }
}

const actions = {
  async fetchBooks({ commit }) {
    commit('SET_LOADING', true)
    try {
      const response = await bookService.getAllBooks()
      if (response.data.success) {
        commit('SET_BOOKS', response.data.data)
        return { success: true, data: response.data.data }
      } else {
        return { success: false, message: response.data.message }
      }
    } catch (error) {
      return {
        success: false,
        message: error.response?.data?.message || 'Failed to fetch books'
      }
    } finally {
      commit('SET_LOADING', false)
    }
  },

  async fetchAvailableBooks({ commit }) {
    commit('SET_LOADING', true)
    try {
      const response = await bookService.getAvailableBooks()
      if (response.data.success) {
        commit('SET_AVAILABLE_BOOKS', response.data.data)
        return { success: true, data: response.data.data }
      } else {
        return { success: false, message: response.data.message }
      }
    } catch (error) {
      return {
        success: false,
        message: error.response?.data?.message || 'Failed to fetch available books'
      }
    } finally {
      commit('SET_LOADING', false)
    }
  },

  async searchBooks({ commit }, keyword) {
    commit('SET_LOADING', true)
    try {
      const response = await bookService.searchBooks(keyword)
      if (response.data.success) {
        commit('SET_SEARCH_RESULTS', response.data.data)
        return { success: true, data: response.data.data }
      } else {
        return { success: false, message: response.data.message }
      }
    } catch (error) {
      return {
        success: false,
        message: error.response?.data?.message || 'Search failed'
      }
    } finally {
      commit('SET_LOADING', false)
    }
  }
}

const getters = {
  books: state => state.books,
  availableBooks: state => state.availableBooks,
  searchResults: state => state.searchResults,
  loading: state => state.loading
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
}
