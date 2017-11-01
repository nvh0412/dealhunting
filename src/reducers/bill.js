import {
  BILL_LOADED,
  BILL_SELECTED,
  BILL_REMOVED,
  BILL_ADDED,
  BILL_UPDATED,
  BILL_TRACKING_FETCHED,
  BILL_REPORT_LOADED,
  CONTACT_SELECTED
} from '../actions/types';
import { Actions } from 'react-native-router-flux';

const initialState = {
  bills: [{ id: 1 }],
  selectedBill: {},
  formType: 'NEW',
  billReport: { total: 0 },
  trackingData: { total: 0, report: { '': 0, '': 0, '': 0, '': 0, '': 0, '': 0, '': 0, '': 0,  } }
};

export default (state = initialState, action) => {
  switch (action.type) {
    case BILL_LOADED:
      return {
        ...state,
        bills: action.payload,
        dateFilter: action.date
      };
    case BILL_SELECTED:
      Actions.billForm();
      return {
        ...state,
        selectedBill: action.payload,
        formType: action.formType
      }
    case BILL_REMOVED:
      const bills = state.bills.filter((bill) => bill.id !== parseInt(action.id));
      return { ...state, bills };
    case BILL_ADDED:
      Actions.pop();
      return {
        ...state,
        bills: [{ ...action.payload, id: action.payload.id }].concat(state.bills)
      };
    case BILL_UPDATED:
      Actions.pop();
      return {
        ...state,
        bills: state.bills.map(bill => {
          if (bill.id === action.payload.id) {
            return action.payload;
          }
          return bill;
        })
      }
    case BILL_TRACKING_FETCHED:
      return {
        ...state,
        trackingData: action.payload
      };
    case BILL_REPORT_LOADED:
      return {
        ...state,
        billReport: action.payload
      }
    case CONTACT_SELECTED:
      return {
        ...state,
        selectedBill: { full_name: action.payload.name, phone_number: action.payload.phone }
      }
    default:
      return state;
  }
}

