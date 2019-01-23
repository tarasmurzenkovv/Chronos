import {createReducer} from 'shared/utils/createReducer';
import requestsStatuses from 'shared/utils/requestsStatuses';

import {IUser} from 'modules/authorization/reducers/signIn';

import {FETCH_USERS_LIST, SELECT_USER_IN_USERLIST} from '../actions/constants';

export type TState = Readonly<{
  status: string;
  selectedId: number | null;
  list: IUser[];
}>;

const defaultState: TState = {
  status: requestsStatuses.default,
  selectedId: null,
  list: []
};

const timesheet = createReducer(defaultState, {
  [FETCH_USERS_LIST.pending]: (state: TState) => ({
    ...state,
    status: requestsStatuses.pending
  }),

  [FETCH_USERS_LIST.failure]: (state: TState) => ({
    ...state,
    status: requestsStatuses.failure
  }),

  [FETCH_USERS_LIST.success]: (state: TState, {payload: {list}}) => ({
    ...state,
    status: requestsStatuses.success,
    list
  }),

  [SELECT_USER_IN_USERLIST]: (state: TState, {payload: {selectedId}}) => ({
    ...state,
    selectedId
  })
});

export default timesheet;
