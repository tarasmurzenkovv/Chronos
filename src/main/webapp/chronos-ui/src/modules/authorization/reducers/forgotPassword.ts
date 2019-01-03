import {LOCATION_CHANGE} from 'connected-react-router';

import {createReducer, TCreateReducer} from 'shared/utils/createReducer';
import requestsStatuses from 'shared/utils/requestsStatuses';

import {
  ADD_LOCAL_FORGOT_PASSWORD_ERROR_CODES,
  RESET_FORGOT_PASSWORD_ERROR_CODES
} from '../actions/forgotPassword';

export type TState = Readonly<{
  errorCodes: number[];
  status: string;
}>;

const defaultState: TState = {
  errorCodes: [],
  status: requestsStatuses.default
};

const signUp = createReducer(defaultState, {
  [ADD_LOCAL_FORGOT_PASSWORD_ERROR_CODES]: (
    state: TState,
    {payload: {errorCodes}}
  ) => ({
    ...state,
    status: requestsStatuses.failure,
    errorCodes
  }),

  [RESET_FORGOT_PASSWORD_ERROR_CODES]: (state: TState) => ({
    ...state,
    status: requestsStatuses.default,
    errorCodes: []
  }),

  [LOCATION_CHANGE]: () => defaultState
});

export default signUp;
