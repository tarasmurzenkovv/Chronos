import {isUserAdmin} from '.';

export const isVisibleToUser = (state) => {
  const isAdmin = isUserAdmin(state);
  const userId = state.common.user.id;
  const selectedUserId = state.common.usersList.selectedId;

  return !(isAdmin && selectedUserId !== userId);
};
