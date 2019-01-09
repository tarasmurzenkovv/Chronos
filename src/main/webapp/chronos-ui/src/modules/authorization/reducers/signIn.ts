import {LOCATION_CHANGE} from 'connected-react-router';

import {createReducer} from 'shared/utils/createReducer';
import requestsStatuses from 'shared/utils/requestsStatuses';

import {
  ADD_LOCAL_SIGN_IN_ERROR_CODES,
  RESET_SIGN_IN_ERROR_CODES,
  SIGN_IN
} from '../constants';

export type TState = Readonly<{
  errorCodes: number[];
  errorMessage: string | null;
  status: string;
  user: {
    id: number | null;
    email: string;
    first_name: string;
    last_name: string;
  };
}>;

const defaultState: TState = {
  errorCodes: [],
  errorMessage: null,
  status: requestsStatuses.default,
  user: {
    id: null,
    email: '',
    first_name: '',
    last_name: ''
  }
};

const signUp = createReducer(defaultState, {
  [ADD_LOCAL_SIGN_IN_ERROR_CODES]: (
    state: TState,
    {payload: {errorCodes}}
  ) => ({
    ...state,
    status: requestsStatuses.failure,
    errorCodes: [...errorCodes]
  }),

  [RESET_SIGN_IN_ERROR_CODES]: (state: TState) => ({
    ...state,
    status: requestsStatuses.default,
    errorCodes: [],
    errorMessage: null
  }),

  [SIGN_IN.failure]: (
    state: TState,
    {
      payload: {
        error: {error_code, error_message}
      }
    }
  ) => ({
    ...state,
    status: requestsStatuses.failure,
    errorCodes: [error_code],
    errorMessage: error_message
  }),

  [SIGN_IN.success]: (state: TState, {payload: {user}}) => ({
    ...state,
    status: requestsStatuses.success,
    user
  }),

  [LOCATION_CHANGE]: () => defaultState
});

export default signUp;
