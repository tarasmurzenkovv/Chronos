import {Store} from 'redux';

interface IDefaultState {
  [key: string]: any;
}

interface IHandlers {
  [key: string]: (state: IDefaultState, payload: any) => any;
}

export type TCreateReducer<T> = (
  defaultState: IDefaultState,
  handlers: IHandlers
) => (store: Store<T>, action: string) => T;

export const createReducer = (
  defaultState: IDefaultState,
  handlers: IHandlers
) => (state = defaultState, action: any) =>
  handlers[action.type] ? handlers[action.type](state, action) : state;
