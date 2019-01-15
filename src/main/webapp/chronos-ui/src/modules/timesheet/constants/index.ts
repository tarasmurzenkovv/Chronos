import createAsyncAction from 'shared/utils/createAsyncAction';
import createAction from '../actions';

export const SELECT_RECORD = createAction('SELECT_RECORD');
export const SET_MONTH_FILTER = createAction('SET_MONTH_FILTER');

export const FETCH_TIMESHEET_LIST = createAsyncAction(
  createAction('FETCH_TIMESHEET_LIST')
);

export const FETCH_TIMESHEET_LIST_BY_DATE = createAsyncAction(
  createAction('FETCH_TIMESHEET_LIST_BY_DATE')
);
