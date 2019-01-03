import {EMAIL_PATTERN, PASSWORD_PATTERN} from 'shared/utils/patterns';

import createAction from '.';

import {
  INVALID_EMAIL_LOCAL,
  INVALID_PASSWORD_LOCAL
} from '../constants/localErrorCodes';

export const ADD_LOCAL_SIGN_IN_ERROR_CODES = createAction(
  'ADD_LOCAL_SIGN_IN_ERROR_CODES'
);
export const RESET_SIGN_IN_ERROR_CODES = createAction(
  'RESET_SIGN_IN_ERROR_CODES'
);

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
};

export const resetSignInErrorCodes = () => (dispatch, getState) => {
  const errorCodes = getState().auth.signIn.errorCodes;

  if (errorCodes.length) {
    return dispatch({
      type: RESET_SIGN_IN_ERROR_CODES
    });
  }
};

export const signIn = ({email, password}) => (dispatch) => {
  dispatch(clientVerification(email, password));
};
