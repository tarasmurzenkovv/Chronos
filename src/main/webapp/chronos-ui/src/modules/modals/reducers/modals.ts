import {createReducer} from 'shared/utils/createReducer';

import {ADD_MODAL, REMOVE_CURRENT_MODAL} from '../actions/constants';

export interface IModal {
  id: string;
}

export type TState = Readonly<IModal[]>;
const defaultState: TState = [];

const modalsReducer = createReducer(defaultState, {
  [ADD_MODAL]: (state: TState, {payload}) => [...state, payload],

  [REMOVE_CURRENT_MODAL]: (state: TState, {payload: {modals}}) => modals
});

export default modalsReducer;
