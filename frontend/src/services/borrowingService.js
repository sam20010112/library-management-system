import api from './api'

const borrowingService = {
  async borrowBook(inventoryId) {
    try {
      console.log('Attempting to borrow book with inventoryId:', inventoryId)
      const response = await api.post(`/borrowing/borrow/${inventoryId}`)
      console.log('Borrow book response:', response)
      return response
    } catch (error) {
      console.error('Borrow book error details:', error)
      throw error
    }
  },

  async returnBook(inventoryId) {
    return await api.post(`/borrowing/return/${inventoryId}`)
  },

  async getBorrowingHistory() {
    return await api.get('/borrowing/history')
  },

  async getActiveBorrowings() {
    return await api.get('/borrowing/active')
  },

  async getStats() {
    return await api.get('/borrowing/stats')
  }
}

export default borrowingService
