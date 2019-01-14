import createAsyncAction from 'shared/utils/createAsyncAction';
import createAction from '../actions';

export const FETCH_TIMESHEET_LIST = createAsyncAction(
  createAction('FETCH_TIMESHEET_LIST')
);

export const FETCH_TIMESHEET_LIST_BY_DATE = createAsyncAction(
  createAction('FETCH_TIMESHEET_LIST_BY_DATE')
);
