import apiCall from 'shared/utils/apiCall';
import {SIGN_IN_URL} from 'modules/authorization/services';
import errorAction from 'shared/utils/errorAction';
import {SIGN_IN} from '../../constants';

export default ({email, password}) => (dispatch) => {
  const apiCallParams = {
    method: 'POST',
    body: JSON.stringify({
      email,
      password
    })
  };
  dispatch({type: SIGN_IN.pending});

  return apiCall(SIGN_IN_URL, apiCallParams)
    .then(({data}) =>
      dispatch({
        type: SIGN_IN.success,
        payload: {user: data}
      })
    )
    .catch((err) => {
      dispatch(errorAction(SIGN_IN.failure, err));
      throw err;
    });
};
