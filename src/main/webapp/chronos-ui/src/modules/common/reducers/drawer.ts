import {createReducer} from 'shared/utils/createReducer';
import {SET_DRAWER_STATUS} from '../actions/constants';

export type TState = Readonly<{
  isOpen: boolean;
}>;

const defaultState: TState = {
  isOpen: false
};

const timesheet = createReducer(defaultState, {
  [SET_DRAWER_STATUS]: (state: TState, {payload: {isOpen}}) => ({
    ...state,
    isOpen
  })
});

export default timesheet;
