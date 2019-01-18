import {ofType} from 'redux-observable';
import {mapTo} from 'rxjs/operators';

import {SELECT_USER_IN_USERLIST} from 'modules/common/actions/constants';
import {
  CREATE_TIMESHEET_RECORD,
  DELETE_TIMESHEET_RECORD,
  EDIT_TIMESHEET_RECORD
} from 'modules/modals/constants';
import {SET_MONTH_FILTER} from 'modules/timesheet/constants';
import {fetchTimesheetListByDateApi} from 'modules/timesheet/actions/api/fetchTimesheetListByDateApi';

export const fetchTimesheetListByDateEpic = (action$) =>
  action$.pipe(
    ofType(
      CREATE_TIMESHEET_RECORD.success,
      DELETE_TIMESHEET_RECORD.success,
      EDIT_TIMESHEET_RECORD.success,
      SELECT_USER_IN_USERLIST,
      SET_MONTH_FILTER
    ),
    mapTo(fetchTimesheetListByDateApi())
  );
