import {SELECT_USER_IN_USERLIST, SET_DRAWER_STATUS} from './constants';

export const setDrawerStatus = () => (dispatch, getState) => {
  const isOpenStatus = getState().common.drawer.isOpen;

  return dispatch({type: SET_DRAWER_STATUS, payload: {isOpen: !isOpenStatus}});
};

export const selectUserInUserlist = (selectedId: number) => (
  dispatch,
  getState
) => {
  const selectedIdStore = getState().common.usersList.selectedId;

  if (selectedId === selectedIdStore) {
    return null;
  }

  return dispatch({type: SELECT_USER_IN_USERLIST, payload: {selectedId}});
};
