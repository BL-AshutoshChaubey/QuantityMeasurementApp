import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1',
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
