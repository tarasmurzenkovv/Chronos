import {LOCATION_CHANGE} from 'connected-react-router';

import {createReducer} from 'shared/utils/createReducer';
import requestsStatuses from 'shared/utils/requestsStatuses';

import {
  FETCH_TIMESHEET_LIST,
  FETCH_TIMESHEET_LIST_BY_DATE,
  SELECT_RECORD
} from '../constants';

export interface IListItem {
  comments: string;
  project_id: number;
  reporting_date: any;
  spent_time: number;
  task_id: number;
  user_id: number;
}

export type TState = Readonly<{
  status: string;
  selectedId: number | null;
  list: IListItem[];
}>;

const defaultState: TState = {
  status: requestsStatuses.default,
  selectedId: null,
  list: []
};

const timesheet = createReducer(defaultState, {
  [FETCH_TIMESHEET_LIST.pending]: (state: TState) => ({
    ...state,
    status: requestsStatuses.pending
  }),

  [FETCH_TIMESHEET_LIST.failure]: (state: TState) => ({
    ...state,
    status: requestsStatuses.failure
  }),

  [FETCH_TIMESHEET_LIST.success]: (state: TState, {payload: {list}}) => ({
    ...state,
    status: requestsStatuses.success,
    list
  }),

  [FETCH_TIMESHEET_LIST_BY_DATE.pending]: (state: TState) => ({
    ...state,
    status: requestsStatuses.pending
  }),

  [FETCH_TIMESHEET_LIST_BY_DATE.failure]: (state: TState) => ({
    ...state,
    status: requestsStatuses.failure
  }),

  [FETCH_TIMESHEET_LIST_BY_DATE.success]: (
    state: TState,
    {payload: {list}}
  ) => ({
    ...state,
    status: requestsStatuses.success,
    list
  }),

  [SELECT_RECORD]: (state: TState, {payload: {id}}) => ({
    ...state,
    selectedId: id
  }),

  [LOCATION_CHANGE]: () => defaultState
});

export default timesheet;
