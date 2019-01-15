import apiCall from 'shared/utils/apiCall';
import errorAction from 'shared/utils/errorAction';
import {DELETE_TIMESHEET_RECORD} from '../../constants';
import {DELETE_RECORD_URL} from '../../services';

export const deleteRecordApi = (id: number) => (dispatch) => {
  const apiCallParams = {
    method: 'DELETE'
  };
  return apiCall(DELETE_RECORD_URL(id), apiCallParams)
    .then(() =>
      dispatch({
        type: DELETE_TIMESHEET_RECORD.success
      })
    )
    .catch((err) => {
      dispatch(errorAction(DELETE_TIMESHEET_RECORD.failure, err));
      throw err;
    });
};
