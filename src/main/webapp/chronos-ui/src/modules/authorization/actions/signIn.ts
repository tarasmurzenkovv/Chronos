import {EMAIL_PATTERN, PASSWORD_PATTERN} from 'shared/utils/patterns';
import {history} from 'configurations/store';
import signInApi from './api/signInApi';

import {
  ADD_LOCAL_SIGN_IN_ERROR_CODES,
  RESET_SIGN_IN_ERROR_CODES
} from '../constants';

import {
  INVALID_EMAIL_LOCAL,
  INVALID_PASSWORD_LOCAL
} from '../constants/localErrorCodes';

const clientErrorHandlers = {
  [INVALID_EMAIL_LOCAL]: ({email}) => !email.match(EMAIL_PATTERN),
  [INVALID_PASSWORD_LOCAL]: ({password}) => !password.match(PASSWORD_PATTERN)
};

const clientVerification = (email, password) => (dispatch) => {
  const errorCodes = Object.keys(clientErrorHandlers)
    .filter((key) =>
      clientErrorHandlers[key]({
        email,
        password
      })
    )
    .map(Number);

  if (errorCodes.length) {
    return dispatch({
      type: ADD_LOCAL_SIGN_IN_ERROR_CODES,
      payload: {errorCodes}
    });
  }

  return null;
};

export const resetSignInErrorCodes = () => (dispatch, getState) => {
  const {errorCodes} = getState().auth.signIn;

  if (errorCodes.length) {
    return dispatch({
      type: RESET_SIGN_IN_ERROR_CODES
    });
  }
  return null;
};

export const signIn = ({email, password}) => (dispatch, getState) => {
  dispatch(resetSignInErrorCodes());
  dispatch(clientVerification(email, password));
  const signInState = getState().auth.signIn;

  if (!signInState.errorCodes.length) {
    return dispatch(signInApi({email, password}))
      .then(() => history.push('/'))
      .catch(() => {});
  }

  return null;
};
