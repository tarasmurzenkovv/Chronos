import * as moment from 'moment';

import {defaultDateFormatApi} from 'shared/utils/constants';
import {SELECT_RECORD, SET_MONTH_FILTER} from '../constants';

export const selectRecord = (id: number) => (dispatch) =>
  dispatch({type: SELECT_RECORD, payload: {id}});

export const setMonthFilter = (month: any) => (dispatch) => {
  const startOfMonth = month.startOf('month').format(defaultDateFormatApi);
  const endOfMonth = month.endOf('month').format(defaultDateFormatApi);

  return dispatch({
    type: SET_MONTH_FILTER,
    payload: {
      month: moment(month).format('MMM YYYY'),
      startOfMonth,
      endOfMonth
    }
  });
};
