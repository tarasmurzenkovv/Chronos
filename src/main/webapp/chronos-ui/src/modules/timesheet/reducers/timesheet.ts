import {LOCATION_CHANGE} from 'connected-react-router';

import {createReducer} from 'shared/utils/createReducer';
import requestsStatuses from 'shared/utils/requestsStatuses';

import {
  FETCH_TIMESHEET_LIST,
  FETCH_TIMESHEET_LIST_BY_DATE,
  SELECT_RECORD,
  SET_MONTH_FILTER
} from '../constants';

export interface IListItem {
  comments: string;
  editable: boolean;
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
  filters: {
    date: {
      month: string | null;
      startOfMonth: string | null;
      endOfMonth: string | null;
    };
  };
}>;

const defaultState: TState = {
  status: requestsStatuses.default,
  selectedId: null,
  list: [],
  filters: {
    date: {
      month: null,
      startOfMonth: null,
      endOfMonth: null
    }
  }
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

  [SET_MONTH_FILTER]: (
    state: TState,
    {payload: {month, startOfMonth, endOfMonth}}
  ) => ({
    ...state,
    filters: {
      date: {
        ...state.filters.date,
        month,
        startOfMonth,
        endOfMonth
      }
    }
  }),

  [LOCATION_CHANGE]: () => defaultState
});

export default timesheet;
