import apiCall from 'shared/utils/apiCall';
import errorAction from 'shared/utils/errorAction';
import {CREATE_RECORD_URL} from '../../services';
import {EDIT_TIMESHEET_RECORD} from '../../constants';

export const editRecordApi = (params, token) => (dispatch) => {
  const apiCallParams = {
    method: 'PUT',
    body: JSON.stringify({
      ...params
    }),
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
      Accept: 'application/json'
    }
  };

  return apiCall(CREATE_RECORD_URL, apiCallParams)
    .then(({data}) =>
      dispatch({
        type: EDIT_TIMESHEET_RECORD.success,
        payload: {data}
      })
    )
    .catch((err) => {
      dispatch(errorAction(EDIT_TIMESHEET_RECORD.failure, err));
      throw err;
    });
};
