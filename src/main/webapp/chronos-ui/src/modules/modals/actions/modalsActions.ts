import {Dispatch} from 'redux';

import {ADD_MODAL, REMOVE_CURRENT_MODAL} from '../actions/constants';

interface IModalParams {
  id: string;
}

export const addModal = (params: IModalParams) => (dispatch: Dispatch<any>) =>
  dispatch({
    type: ADD_MODAL,
    payload: params
  });

export const removeCurrentModal = () => (
  dispatch: Dispatch<any>,
  getState: any
) =>
  dispatch({
    type: REMOVE_CURRENT_MODAL,
    payload: {
      modals: getState().modals.slice(1)
    }
  });
