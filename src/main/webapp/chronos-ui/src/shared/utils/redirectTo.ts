import {push} from 'connected-react-router';

export const redirectTo = (route: string = '/') => (dispatch) =>
  dispatch(push(route));
