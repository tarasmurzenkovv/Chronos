import createAsyncAction from 'shared/utils/createAsyncAction';
import createAction from 'modules/modals/actions';

export const TIMESHEET_RECORD_MODAL = 'TIMESHEET_RECORD_MODAL';
export const TIMESHEET_RECORD_DELETE_MODAL = 'TIMESHEET_RECORD_DELETE_MODAL';

export const CREATE_TIMESHEET_RECORD = createAsyncAction(
  createAction('CREATE_TIMESHEET_RECORD')
);

export const DELETE_TIMESHEET_RECORD = createAsyncAction(
  createAction('DELETE_TIMESHEET_RECORD')
);
