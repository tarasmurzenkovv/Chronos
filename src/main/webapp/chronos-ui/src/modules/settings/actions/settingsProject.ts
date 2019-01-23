import {SELECT_PROJECT} from './constants';

export const selectProject = (id: number) => (dispatch) =>
  dispatch({type: SELECT_PROJECT, payload: {id}});
