import {isUserAdmin} from '.';

export const isVisibleToUser = (state) => {
  const isAdmin = isUserAdmin(state);
  const userId = state.auth.signIn.user.id;
  const selectedUserId = state.common.users.selectedId;

  return !(isAdmin && selectedUserId !== userId);
};
