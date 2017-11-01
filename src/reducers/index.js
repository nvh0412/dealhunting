import { combineReducers } from 'redux';
import auth from './auth';
import bill from './bill';
import customer from './customer';

export default combineReducers({
  auth,
  bill,
  customer
});

