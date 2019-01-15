import {SELECT_RECORD} from '../constants';

export const selectRecord = (id: number) => (dispatch) => {
  dispatch({type: SELECT_RECORD, payload: {id}});
};
