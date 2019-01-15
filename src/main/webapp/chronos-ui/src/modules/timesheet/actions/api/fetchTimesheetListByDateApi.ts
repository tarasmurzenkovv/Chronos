import apiCall from 'shared/utils/apiCall';
import errorAction from 'shared/utils/errorAction';
import {FETCH_TIMESHEET_LIST_BY_DATE} from '../../constants';
import {timesheetByDateUrl} from '../../services';

export interface IProps {
  id: number;
  start: string;
  end: string;
}

export const fetchTimesheetListByDateApi = ({id, start, end}: IProps) => (
  dispatch
) => {
  dispatch({type: FETCH_TIMESHEET_LIST_BY_DATE.pending});

  return apiCall(timesheetByDateUrl({id, start, end}))
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
