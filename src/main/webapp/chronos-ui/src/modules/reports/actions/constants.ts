import createAsyncAction from 'shared/utils/createAsyncAction';

import createAction from '.';

export const SELECET_USER_ID_FOR_REPORTS = createAction(
  'SELECET_USER_ID_FOR_REPORTS'
);

export const SELECET_ALL_USER_ID_FOR_REPORTS = createAction(
  'SELECET_ALL_USER_ID_FOR_REPORTS'
);

export const FETCH_REPORT = createAsyncAction(createAction('FETCH_REPORT'));
