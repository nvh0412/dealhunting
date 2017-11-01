import {
  CUSTOMER_LOADED,
  CUSTOMER_ITEM_LOADED,
  CUSTOMER_UPDATED,
  CUSTOMER_SELECTED,
  CUSTOMER_REPORT_LOADED,
} from '../actions/types';
import { Actions } from 'react-native-router-flux';

const initialState = {
  customers: [{ id: 1 }],
  customer: {},
  customerReport: { total: 0 }
};

export default (state = initialState, action) => {
  switch (action.type) {
    case CUSTOMER_LOADED:
      return {
        ...state,
        customers: action.payload
      };
    case CUSTOMER_ITEM_LOADED:
      return {
        ...state,
        customer: action.payload
      };
    case CUSTOMER_SELECTED:
      Actions.customerForm();
      return {
        ...state,
        selectedCustomer: action.payload
      }
    case CUSTOMER_UPDATED:
      Actions.pop();
      return {
        ...state,
        customers: state.customers.map(customer => {
          if (customer.id === action.payload.id) {
            return action.payload;
          }
          return customer;
        })
      }
    case CUSTOMER_REPORT_LOADED:
      return {
        ...state,
        customerReport: action.payload
      }
    default:
      return state;
  }
}

