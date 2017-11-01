import {
  EMAIL_CHANGED,
  PASSWORD_CHANGED,
  USER_LOGGED_IN,
  USER_LOGGED_OUT,
  USER_LOGIN_FAILED
} from './types';
import { loginApi } from '../apis/auth';

const emailLoginChange = (email) => ({
  type: EMAIL_CHANGED,
  payload: email
});

const passwordLoginChange = (password) => ({
  type: PASSWORD_CHANGED,
  payload: password
});

const logIn = ({email, password}) => {
  return (dispatch) => {
    return loginApi({email, password})
      .then(res => {
        const data = res.data;
        return dispatch({
          type: USER_LOGGED_IN,
          payload: data
        });
      })
      .catch(() => dispatch({
        type: USER_LOGIN_FAILED
      }));
  }
}

const logOut = () => ({ type: USER_LOGGED_OUT })

export { emailLoginChange, passwordLoginChange, logIn, logOut };
