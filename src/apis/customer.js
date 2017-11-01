import apiClient from './apiClient';

export const getCustomers = (token, keyword) => {
  return apiClient().get(`/customers?token=${token}`, { params: { keyword } });
};

export const updateCustomer = (token, customer) => {
  return apiClient().put(`/customers/${customer.id}?token=${token}`, customer);
};

export const getCustomer = (token, id) => {
  return apiClient().get(`/customers/${id}?token=${token}`);
};

export const getCustomerReport = (token) => {
  return apiClient().get(`/customers/report?token=${token}`);
};

