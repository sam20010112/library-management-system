import { createStore } from 'vuex'
import auth from './modules/auth'
import books from './modules/books'
import borrowing from './modules/borrowing'

export default createStore({
  modules: {
    auth,
    books,
    borrowing
  }
})
