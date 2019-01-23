import {IUserId} from 'shared/types';

import {
  SELECET_ALL_USER_ID_FOR_REPORTS,
  SELECET_USER_ID_FOR_REPORTS
} from './constants';

export const selectUserIdForReports = (id: IUserId) => (dispatch, getState) => {
  const selectedUsersIdsFromStore = getState().reports.selectedUsersIds;

  let selectedUsersIds: IUserId[];

  if (selectedUsersIdsFromStore.includes(id)) {
    selectedUsersIds = selectedUsersIdsFromStore.filter((item) => item !== id);
  } else {
    selectedUsersIds = [...selectedUsersIdsFromStore, id];
  }
  return dispatch({
    type: SELECET_USER_ID_FOR_REPORTS,
    payload: {selectedUsersIds}
  });
};

export const selectAllUserIdForReports = () => (dispatch, getState) => {
  const usersList = getState().common.usersList.list;

  if (usersList.length) {
    const ids: IUserId[] = [];
    usersList.map((item) => ids.push(item.id));
    return dispatch({
      type: SELECET_ALL_USER_ID_FOR_REPORTS,
      payload: {ids}
    });
  }

  return null;
};
