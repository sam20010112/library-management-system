import axios from 'axios'
import logger from '../utils/logger'

const API_BASE_URL = 'http://localhost:8080/api'

const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// Request interceptor to add auth token
api.interceptors.request.use(
  config => {
    const startTime = Date.now()
    config.metadata = { startTime }
    
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    
    // Debug: Log the full URL being requested
    console.log('Full request URL:', config.baseURL + config.url)
    console.log('Request headers:', config.headers)
    
    logger.apiRequest(config.method, config.url, config.data)
    return config
  },
  error => {
    logger.error('Request interceptor error', error)
    return Promise.reject(error)
  }
)

// Response interceptor to handle errors
api.interceptors.response.use(
  response => {
    const duration = response.config.metadata ? Date.now() - response.config.metadata.startTime : null
    logger.apiResponse(response.config.method, response.config.url, response, duration)
    return response
  },
  error => {
    const duration = error.config?.metadata ? Date.now() - error.config.metadata.startTime : null
    logger.apiError(error.config?.method, error.config?.url, error, duration)
    
    if (error.response?.status === 401) {
      // Token expired or invalid
      logger.warn('Authentication failed, redirecting to login...')
      localStorage.removeItem('token')
      window.location.href = '/login'
    }
    
    return Promise.reject(error)
  }
)

export default api
