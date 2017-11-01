import * as CustomerApi from '../apis/customer';
import {
  CUSTOMER_LOADED,
  CUSTOMER_ITEM_LOADED,
  CUSTOMER_UPDATED,
  CUSTOMER_SELECTED,
  CUSTOMER_REPORT_LOADED,
} from './types';

const getCustomers = (token, keyword = '') => (dispatch) => CustomerApi.getCustomers(token, keyword).then(
  res => dispatch({
    type: CUSTOMER_LOADED,
    payload: res.data.customers,
  })
);

const updateCustomer = (token, customer) => (dispatch) => CustomerApi.updateCustomer(token, customer).then(
  res => dispatch({
    type: CUSTOMER_UPDATED,
    payload: res.data
  })
);

const getCustomer = (token, id) => (dispatch) => CustomerApi.getCustomer(token, id).then(
  res => dispatch({
    type: CUSTOMER_ITEM_LOADED,
    payload: res.data
  })
);

const getCustomerReport = (token) => (dispatch) => CustomerApi.getCustomerReport(token).then(
  res => dispatch({
    type: CUSTOMER_REPORT_LOADED,
    payload: res.data
  })
);

const selectCustomer = (customer) => {
  return {
    type: CUSTOMER_SELECTED,
    payload: customer
  };
};

export { getCustomerReport, getCustomers, updateCustomer, selectCustomer, getCustomer };

