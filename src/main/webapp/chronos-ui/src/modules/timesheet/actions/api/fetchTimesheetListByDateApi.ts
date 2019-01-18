import apiCall from 'shared/utils/apiCall';
import errorAction from 'shared/utils/errorAction';
import {FETCH_TIMESHEET_LIST_BY_DATE} from '../../constants';
import {timesheetByDateUrl} from '../../services';

export const fetchTimesheetListByDateApi = () => (dispatch, getState) => {
  const state = getState();
  const userId = state.auth.signIn.user.id;
  const selectedId = state.common.users.selectedId;
  const startOfMonth = state.timesheet.filters.date.startOfMonth;
  const endOfMonth = state.timesheet.filters.date.endOfMonth;

  dispatch({type: FETCH_TIMESHEET_LIST_BY_DATE.pending});

  return apiCall(
    timesheetByDateUrl({
      id: selectedId || userId,
      start: startOfMonth,
      end: endOfMonth
    })
  )
    .then(({data}) =>
      dispatch({
        type: FETCH_TIMESHEET_LIST_BY_DATE.success,
        payload: {list: data}
      })
    )
    .catch((err) => {
      dispatch(errorAction(FETCH_TIMESHEET_LIST_BY_DATE.failure, err));
      throw err;
    });
};
