import {
  BILL_LOADED,
  BILL_SELECTED,
  BILL_REMOVED,
  BILL_ADDED,
  BILL_UPDATED,
  BILL_TRACKING_FETCHED,
  BILL_REPORT_LOADED,
  CONTACT_SELECTED
} from './types';
import {
  fetchBillApi,
  createBillApi,
  updateBillApi,
  removeBillApi,
  getBillTrackingApi,
  getBillReportApi
} from '../apis/bill';

const fetchBill = (token, date) => (dispatch) => fetchBillApi(token, date).then(
  res => dispatch({
    type: BILL_LOADED,
    payload: res.data.bills,
    date
  })
);

const createBill = (token, bill) => (dispatch) => createBillApi(token, bill).then(
  res => dispatch({
    type: BILL_ADDED,
    payload: res.data
  })
);

const updateBill = (token, bill) => (dispatch) => updateBillApi(token, bill).then(
  res => dispatch({
    type: BILL_UPDATED,
    payload: res.data
  })
);

const removeBill = (token, id) => (dispatch) => removeBillApi(token, id).then(
  res => dispatch({ type: BILL_REMOVED, id })
);

const selectBill = (bill, formType) => {
  return {
    type: BILL_SELECTED,
    payload: bill,
    formType
  };
};

const getBillTracking = (token, type = 'day') => (dispatch) => getBillTrackingApi(token, type).then(
  res => dispatch({
    type: BILL_TRACKING_FETCHED,
    payload: res.data
  })
);

const getBillReport = (token) => (dispatch) => getBillReportApi(token).then(
  res => dispatch({
    type: BILL_REPORT_LOADED,
    payload: res.data
  })
);

const contactSelect = (contact) => ({
  type: CONTACT_SELECTED,
  payload: contact
});


export { fetchBill, selectBill, removeBill, createBill, updateBill, getBillTracking, getBillReport };
