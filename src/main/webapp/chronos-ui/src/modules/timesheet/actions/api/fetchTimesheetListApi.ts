import apiCall from 'shared/utils/apiCall';
import errorAction from 'shared/utils/errorAction';
import {FETCH_TIMESHEET_LIST} from '../../constants';
import timesheetUrl from '../../services';

export default (id: number) => (dispatch) => {
  dispatch({type: FETCH_TIMESHEET_LIST.pending});

  return apiCall(timesheetUrl(id))
    .then(({data}) =>
      dispatch({
        type: FETCH_TIMESHEET_LIST.success,
        payload: {list: data}
      })
    )
    .catch((err) => {
      dispatch(errorAction(FETCH_TIMESHEET_LIST.failure, err));
      throw err;
    });
};
