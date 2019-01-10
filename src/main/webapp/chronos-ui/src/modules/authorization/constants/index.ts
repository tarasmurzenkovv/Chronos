import createAsyncAction from 'shared/utils/createAsyncAction';
import createAction from '../actions';

export const SIGN_IN = createAsyncAction(createAction('SIGN_IN'));

export const ADD_LOCAL_SIGN_IN_ERROR_CODES = createAction(
  'ADD_LOCAL_SIGN_IN_ERROR_CODES'
);

export const RESET_SIGN_IN_ERROR_CODES = createAction(
  'RESET_SIGN_IN_ERROR_CODES'
);
