import {ADMIN_ROLE} from '../constatns';

export const isUserAdmin = (state) => state.common.user.role === ADMIN_ROLE;
