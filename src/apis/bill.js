import apiClient from './apiClient';

export const fetchBillApi = (token, date) => {
  return apiClient().get(`/bills?token=${token}&date=${date}`);
};

export const removeBillApi = (token, id) => {
  return apiClient().delete(`/bills/${id}?token=${token}`);
};

export const createBillApi = (token, bill) => {
  return apiClient().post(`/bills?token=${token}`, bill);
};

export const updateBillApi = (token, bill) => {
  return apiClient().put(`/bills/${bill.id}?token=${token}`, bill);
};

export const getBillTrackingApi = (token, type) => {
  return apiClient().get(`/trackings/${type}?token=${token}`);
};

export const getBillReportApi = (token) => {
  return apiClient().get(`/bills/report?token=${token}`);
};

