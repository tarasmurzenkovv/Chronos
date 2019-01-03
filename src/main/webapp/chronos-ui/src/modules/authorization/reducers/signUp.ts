import {createReducer, TCreateReducer} from 'shared/utils/createReducer';
import requestsStatuses from 'shared/utils/requestsStatuses';

import {
  ADD_LOCAL_SIGN_UP_ERROR_CODES,
  RESET_SIGN_UP_ERROR_CODES
} from '../actions/createUser';

export type TState = Readonly<{
  errorCodes: number[];
  status: string;
}>;

const defaultState: TState = {
  errorCodes: [],
  status: requestsStatuses.default
};

const signUp: TCreateReducer<TState> = createReducer(defaultState, {
  [ADD_LOCAL_SIGN_UP_ERROR_CODES]: (
    state: TState,
    {payload: {errorCodes}}
  ) => ({
    ...state,
    status: requestsStatuses.failure,
    errorCodes
  }),

  [RESET_SIGN_UP_ERROR_CODES]: (state: TState) => ({
    ...state,
    status: requestsStatuses.default,
    errorCodes: []
  })
});

export default signUp;
