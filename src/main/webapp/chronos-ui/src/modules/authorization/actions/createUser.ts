import createAction from '.';
import createAsyncAction from 'shared/utils/createAsyncAction';
import apiCall from 'shared/utils/apiCall';
import {CREATE_USER_URL} from '../services';

import {EMAIL_PATTERN, PASSWORD_PATTERN} from 'shared/utils/patterns';

import {
  INVALID_EMAIL_LOCAL,
  INVALID_PASSWORD_LOCAL,
  INVALID_CONFIRM_PASSWORD_LOCAL,
  PASSWORDS_NOT_MATCH_LOCAL,
  INVALID_FIRST_NAME_LOCAL,
  INVALID_LAST_NAME_LOCAL
} from '../constants/localErrorCodes';

export const ADD_LOCAL_SIGN_UP_ERROR_CODES = createAction(
  'ADD_LOCAL_SIGN_UP_ERROR_CODES'
);
export const RESET_SIGN_UP_ERROR_CODES = createAction(
  'RESET_SIGN_UP_ERROR_CODES'
);

export const CREATE_USER = createAsyncAction('CREATE_USER');

const clientErrorHandlers = {
  [INVALID_EMAIL_LOCAL]: ({email}) => !email.match(EMAIL_PATTERN),
  [INVALID_PASSWORD_LOCAL]: ({password}) => !password.match(PASSWORD_PATTERN),
  [INVALID_CONFIRM_PASSWORD_LOCAL]: ({confirmPassword}) =>
    !confirmPassword.match(PASSWORD_PATTERN),
  [PASSWORDS_NOT_MATCH_LOCAL]: ({password, confirmPassword}) =>
    password !== confirmPassword,
  [INVALID_FIRST_NAME_LOCAL]: ({firstName}) =>
    firstName.toString().trim().length < 3,
  [INVALID_LAST_NAME_LOCAL]: ({lastName}) =>
    lastName.toString().trim().length < 3
};

const clientVerification = (
  email,
  password,
  confirmPassword,
  firstName,
  lastName
) => (dispatch) => {
  const errorCodes = Object.keys(clientErrorHandlers)
    .filter((key) =>
      clientErrorHandlers[key]({
        email,
        password,
        confirmPassword,
        firstName,
        lastName
      })
    )
    .map(Number);

  return dispatch({
    type: ADD_LOCAL_SIGN_UP_ERROR_CODES,
    payload: {errorCodes}
  });
};

export const resetSignUpErrorCodes = () => (dispatch, getState) => {
  const errorCodes = getState().auth.signUp.errorCodes;

  if (errorCodes.length) {
    return dispatch({
      type: RESET_SIGN_UP_ERROR_CODES
    });
  }
};

export const createUser = ({
  email,
  password,
  confirmPassword,
  firstName,
  lastName
}) => (dispatch) => {
  dispatch(
    clientVerification(email, password, confirmPassword, firstName, lastName)
  );

  const apiCallParams = {
    method: 'POST',
    body: JSON.stringify({
      email: email,
      password: password,
      first_name: firstName,
      last_name: lastName
    })
  };

  dispatch({type: CREATE_USER.pending});

  return apiCall(CREATE_USER_URL, apiCallParams)
    .then((result) => console.log('result', result))
    .catch((error) => console.log('error', error));
};
