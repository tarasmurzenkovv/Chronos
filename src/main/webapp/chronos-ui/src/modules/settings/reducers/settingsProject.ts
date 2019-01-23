import {LOCATION_CHANGE} from 'connected-react-router';

import {createReducer} from 'shared/utils/createReducer';

import {SELECT_PROJECT} from '../actions/constants';

export type TState = Readonly<{
  selectedProjectId: number | null;
}>;

const defaultState: TState = {
  selectedProjectId: null
};

const settings = createReducer(defaultState, {
  [SELECT_PROJECT]: (state: TState, {payload: {id}}) => ({
    ...state,
    selectedProjectId: id
  }),

  [LOCATION_CHANGE]: () => defaultState
});

export default settings;
