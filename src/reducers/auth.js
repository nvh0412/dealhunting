import { Actions} from 'react-native-router-flux';
import {
  EMAIL_CHANGED,
  PASSWORD_CHANGED,
  USER_LOGIN_FAILED,
  USER_LOGGED_IN,
  USER_LOGGED_OUT
} from '../actions/types';

const initialState = {
  email: '',
  password: '',
  error: '',
  user: {}
};

export default (state = initialState, action) => {
  switch (action.type) {
    case EMAIL_CHANGED:
      return { ...state, error: '', email: action.payload };
    case PASSWORD_CHANGED:
      return { ...state, error: '', password: action.payload };
    case USER_LOGIN_FAILED:
      return { ...state, error: 'Email hoặc mật khẩu không đúng' };
    case USER_LOGGED_IN:
      return { ...state, user: action.payload, loggedIn: true, email: '', password: '' };
    case USER_LOGGED_OUT:
      Actions.pop();
      return { ...state, user: {}, loggedIn: false };
    default:
      return state;
  }
};
