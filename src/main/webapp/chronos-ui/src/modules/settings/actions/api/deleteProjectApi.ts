import apiCall from 'shared/utils/apiCall';
import errorAction from 'shared/utils/errorAction';
import {DELETE_CURRENT_PROJECT} from '../constants';
import {DELETE_PROJECT_URL} from '../../services';

export const deleteProjectApi = (id: number) => (dispatch) => {
  const apiCallParams = {
    method: 'DELETE'
  };
  return apiCall(DELETE_PROJECT_URL(id), apiCallParams)
    .then(() =>
      dispatch({
        type: DELETE_CURRENT_PROJECT.success
      })
    )
    .catch((err) => {
      dispatch(errorAction(DELETE_CURRENT_PROJECT.failure, err));
      throw err;
    });
};
