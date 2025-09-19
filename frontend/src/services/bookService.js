import api from './api'

const bookService = {
  async getAllBooks() {
    return await api.get('/books/all')
  },

  async getAvailableBooks() {
    return await api.get('/books/available')
  },

  async searchBooks(keyword) {
    return await api.get(`/books/search?keyword=${encodeURIComponent(keyword)}`)
  },

  async getBookByIsbn(isbn) {
    return await api.get(`/books/${isbn}`)
  }
}

export default bookService
