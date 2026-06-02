import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api/v1',
  headers: {
    'Content-Type': 'application/json'
  }
});

// Dynamic JWT Interceptor
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('JWT_TOKEN');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Response interceptor to handle 401s and token refresh
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    
    // If the error is 401 and we haven't retried yet, and it's not the login or refresh endpoint itself
    if (error.response && error.response.status === 401 && !originalRequest._retry && !originalRequest.url.includes('/auth/')) {
      originalRequest._retry = true;
      const refreshToken = localStorage.getItem('REFRESH_TOKEN');
      
      if (refreshToken) {
        try {
          const authBase = import.meta.env.VITE_API_BASE_URL || '/api/v1';
          // Use a fresh axios instance to avoid infinite interceptor loops
          const res = await axios.post(`${authBase}/auth/refreshtoken`, { refreshToken });
          
          if (res.data && res.data.accessToken) {
            localStorage.setItem('JWT_TOKEN', res.data.accessToken);
            if (res.data.refreshToken) {
              localStorage.setItem('REFRESH_TOKEN', res.data.refreshToken);
            }
            
            // Retry original request with new token
            originalRequest.headers['Authorization'] = `Bearer ${res.data.accessToken}`;
            return api(originalRequest);
          }
        } catch (refreshError) {
          // Refresh token failed, clear everything
          localStorage.removeItem('JWT_TOKEN');
          localStorage.removeItem('REFRESH_TOKEN');
          localStorage.removeItem('USER_NAME');
          localStorage.removeItem('USER_EMAIL');
          window.location.href = '/login';
        }
      }
    }
    
    return Promise.reject(error);
  }
);

export const performConversion = async (payload) => {
  try {
    const response = await api.post('/measurements/convert', payload);
    return response.data;
  } catch (error) {
    if (error.response && error.response.data && error.response.data.errorMessage) {
      throw new Error(error.response.data.errorMessage);
    }
    throw new Error('An unexpected error occurred during conversion.');
  }
};

export const performArithmetic = async (payload) => {
  try {
    const response = await api.post('/measurements/arithmetic', payload);
    return response.data;
  } catch (error) {
    if (error.response && error.response.data && error.response.data.errorMessage) {
      throw new Error(error.response.data.errorMessage);
    }
    throw new Error('An unexpected error occurred during arithmetic operation.');
  }
};

export const fetchHistory = async () => {
  try {
    const response = await api.get('/measurements/history');
    return response.data;
  } catch (error) {
    throw new Error('Unable to retrieve user measurement history.');
  }
};
