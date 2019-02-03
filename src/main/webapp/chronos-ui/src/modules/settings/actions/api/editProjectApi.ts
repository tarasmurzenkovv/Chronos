import apiCall from 'shared/utils/apiCall';
import errorAction from 'shared/utils/errorAction';
import {CREATE_PROJECT_URL} from '../../services';
import {EDIT_CURRENT_PROJECT} from '../constants';

export const editProjectApi = (params, token) => (dispatch) => {
  const apiCallParams = {
    method: 'PUT',
    body: JSON.stringify({
      ...params
    }),
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    }
  };

  return apiCall(CREATE_PROJECT_URL, apiCallParams)
    .then(({data}) =>
      dispatch({
        type: EDIT_CURRENT_PROJECT.success,
        payload: {data}
      })
    )
    .catch((err) => {
      dispatch(errorAction(EDIT_CURRENT_PROJECT.failure, err));
      throw err;
    });
};
