import createAsyncAction from 'shared/utils/createAsyncAction';
import createAction from '.';

export const DELETE_CURRENT_PROJECT = createAsyncAction(
  createAction('DELETE_CURRENT_PROJECT')
);
export const CREATE_CURRENT_PROJECT = createAsyncAction(
  createAction('CREATE_CURRENT_PROJECT')
);
export const EDIT_CURRENT_PROJECT = createAsyncAction(
  createAction('EDIT_CURRENT_PROJECT')
);
export const SELECT_PROJECT = createAction('SELECT_PROJECT');
