import {createReducer, TCreateReducer} from 'shared/utils';

import {ADD_MODAL, REMOVE_CURRENT_MODAL} from '../actions/constants';

export interface IModal {
  id: string;
}

export type TState = Readonly<IModal[]>;
const defaultState: TState = [];

const modals: TCreateReducer<TState> = createReducer(defaultState, {
  [ADD_MODAL]: (state: TState, {payload}) => [...state, payload],

  [REMOVE_CURRENT_MODAL]: (state: TState, {payload: {modals}}) => modals
});

export default modals;
