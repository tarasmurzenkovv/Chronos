import apiCall from 'shared/utils/apiCall';
import {GET_PROJECTS_LIST_URL} from 'modules/modals/services';
import errorAction from 'shared/utils/errorAction';
import {GET_PROJECTS_LIST} from '../constants';

export default (token) => (dispatch) => {
  dispatch({type: GET_PROJECTS_LIST.pending, payload: {isLoading: true}});
  const params = {
    headers: {Authorization: `Bearer ${token}`}
  };

  return apiCall(GET_PROJECTS_LIST_URL, params)
    .then(({data}) => {
      const deletedProject = data.filter((item) => item.deleted === true);
      deletedProject.forEach((item) => {
        data.push(data.splice(data.indexOf(item), 1)[0]);
      });
      dispatch({
        type: GET_PROJECTS_LIST.success,
        payload: {list: data, isLoading: false}
      });
      return data;
    })
    .catch((err) => dispatch(errorAction(GET_PROJECTS_LIST.failure, err)));
};
