import apiCall from 'shared/utils/apiCall';
import errorAction from 'shared/utils/errorAction';
import {CREATE_RECORD_URL} from '../../services';
import {CREATE_TIMESHEET_RECORD} from '../../constants';

export const createRecordApi = (params, token) => (dispatch) => {
  const apiCallParams = {
    method: 'POST',
    body: JSON.stringify({
      ...params
    }),
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
      Accept: 'application/json'
    }
  };
  dispatch({type: CREATE_TIMESHEET_RECORD.pending});

  return apiCall(CREATE_RECORD_URL, apiCallParams)
    .then(({data}) =>
      dispatch({
        type: CREATE_TIMESHEET_RECORD.success,
        payload: {data}
      })
    )
    .catch((err) => {
      dispatch(errorAction(CREATE_TIMESHEET_RECORD.failure, err));
      throw err;
    });
};
