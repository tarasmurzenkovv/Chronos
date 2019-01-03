import {EMAIL_PATTERN} from 'shared/utils/patterns';

import createAction from '.';

import {INVALID_EMAIL_LOCAL} from '../constants/localErrorCodes';

export const ADD_LOCAL_FORGOT_PASSWORD_ERROR_CODES = createAction(
  'ADD_LOCAL_FORGOT_PASSWORD_ERROR_CODES'
);
export const RESET_FORGOT_PASSWORD_ERROR_CODES = createAction(
  'RESET_FORGOT_PASSWORD_ERROR_CODES'
);

const clientErrorHandlers = {
  [INVALID_EMAIL_LOCAL]: ({email}) => !email.match(EMAIL_PATTERN)
};

const clientVerification = (email) => (dispatch) => {
  const errorCodes = Object.keys(clientErrorHandlers)
    .filter((key) =>
      clientErrorHandlers[key]({
        email
      })
    )
    .map(Number);

  if (errorCodes.length) {
    return dispatch({
      type: ADD_LOCAL_FORGOT_PASSWORD_ERROR_CODES,
      payload: {errorCodes}
    });
  }
};

export const resetForgotPasswordErrorCodes = () => (dispatch, getState) => {
  const errorCodes = getState().auth.forgotPassword.errorCodes;

  if (errorCodes.length) {
    return dispatch({
      type: RESET_FORGOT_PASSWORD_ERROR_CODES
    });
  }
};

export const forgotPassword = ({email}) => (dispatch) => {
  dispatch(clientVerification(email));
};
