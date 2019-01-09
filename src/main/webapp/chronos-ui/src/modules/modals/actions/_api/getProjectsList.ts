import apiCall from 'shared/utils/apiCall';
import {GET_PROJECTS_LIST_URL} from 'modules/modals/services';
import errorAction from 'shared/utils/errorAction';
import {GET_PROJECTS_LIST} from '../constants';

export const getProjectsList = () => (dispatch) => {
  dispatch({type: GET_PROJECTS_LIST.pending});

  return apiCall(GET_PROJECTS_LIST_URL)
    .then(({data}) =>
      dispatch({type: GET_PROJECTS_LIST.success, payload: {list: data}})
    )
    .catch((err) => dispatch(errorAction(GET_PROJECTS_LIST.failure, err)));
};
