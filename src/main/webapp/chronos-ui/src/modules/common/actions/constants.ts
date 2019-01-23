import createAsyncAction from 'shared/utils/createAsyncAction';

import createAction from '.';

export const SET_DRAWER_STATUS = createAction('SET_DRAWER_STATUS');
export const SELECT_USER_IN_USERLIST = createAction('SELECT_USER_IN_USERLIST');

export const FETCH_USERS_LIST_URL = '/api/v0/user/information';
export const FETCH_USERS_LIST = createAsyncAction(
  createAction('FETCH_USERS_LIST')
);
