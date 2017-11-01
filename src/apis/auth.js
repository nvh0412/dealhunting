import apiClient from './apiClient';

export const loginApi = ({email, password}) => {
  return apiClient().post('/users/sign_in', { email, password });
}

export const getCurrentUser = () => {
  return apiClient().get('/users/info');
}
