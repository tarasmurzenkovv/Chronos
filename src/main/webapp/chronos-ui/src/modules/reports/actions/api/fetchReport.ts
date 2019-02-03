import {apiCallForFile} from 'shared/utils';
import errorAction from 'shared/utils/errorAction';

import {FETCH_REPORT} from '../constants';
import {createFetchReportUrl} from '../../services';

export const fetchReport = ({ids, start, end, token}) => (dispatch) => {
  if (!ids.length) {
    return null;
  }
  const params = {
    headers: {
      Authorization: `Bearer ${token}`
    }
  };
  dispatch({type: FETCH_REPORT.pending});

  return apiCallForFile(
    createFetchReportUrl({
      ids,
      start,
      end
    }),
    params
  )
    .then((data) =>
      dispatch({type: FETCH_REPORT.success, payload: {list: data}})
    )
    .catch((err) => dispatch(errorAction(FETCH_REPORT.failure, err)));
};
