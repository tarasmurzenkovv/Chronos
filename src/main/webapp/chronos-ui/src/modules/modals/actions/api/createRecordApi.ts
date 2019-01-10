import apiCall from 'shared/utils/apiCall';
import errorAction from 'shared/utils/errorAction';
import {CREATE_RECORD_URL} from '../../services';
import {CREATE_TIMESHEET_RECORD} from '../../constants';

export const createRecordApi = (params) => (dispatch) => {
  const apiCallParams = {
    method: 'POST',
    body: JSON.stringify({
      ...params
    })
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
