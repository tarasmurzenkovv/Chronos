import {createReducer} from 'shared/utils/createReducer';
import {IUserId} from 'shared/types';
import {
  SELECET_ALL_USER_ID_FOR_REPORTS,
  SELECET_USER_ID_FOR_REPORTS
} from '../actions/constants';

export type TState = Readonly<{
  selectedUsersIds: IUserId[];
}>;

const defaultState: TState = {
  selectedUsersIds: []
};

const reports = createReducer(defaultState, {
  [SELECET_USER_ID_FOR_REPORTS]: (
    state: TState,
    {payload: {selectedUsersIds}}
  ) => ({
    ...state,
    selectedUsersIds
  }),

  [SELECET_ALL_USER_ID_FOR_REPORTS]: (state: TState, {payload: {ids}}) => ({
    ...state,
    selectedUsersIds: ids
  })
});

export default reports;
