import apiCall from 'shared/utils/apiCall';
import errorAction from 'shared/utils/errorAction';

import {FETCH_USERS_LIST, FETCH_USERS_LIST_URL} from '../constants';

export const fetchUsersList = () => (dispatch) => {
  dispatch({type: FETCH_USERS_LIST.pending});

  return apiCall(FETCH_USERS_LIST_URL)
    .then(({data}) =>
      dispatch({
        type: FETCH_USERS_LIST.success,
        payload: {list: data}
      })
    )
    .catch((err) => {
      dispatch(errorAction(FETCH_USERS_LIST.failure, err));
      throw err;
    });
};
