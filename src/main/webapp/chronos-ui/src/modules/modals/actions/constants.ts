import createAsyncAction from 'shared/utils/createAsyncAction';

import createAction from '.';

export const ADD_MODAL = createAction('ADD_MODAL');
export const REMOVE_CURRENT_MODAL = createAction('REMOVE_CURRENT_MODAL');

export const GET_PROJECTS_LIST = createAsyncAction('GET_PROJECTS_LIST');
