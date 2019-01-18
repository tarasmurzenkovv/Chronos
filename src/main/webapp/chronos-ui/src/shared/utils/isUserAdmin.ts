import {ADMIN_ROLE} from '../constatns';

export const isUserAdmin = (state) =>
  state.auth.signIn.user.role === ADMIN_ROLE;
