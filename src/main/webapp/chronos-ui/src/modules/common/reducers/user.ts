import {createReducer} from 'shared/utils/createReducer';

import {SIGN_IN} from 'modules/authorization/constants';

export interface IUser {
  id: number | string;
  email: string;
  first_name: string;
  last_name: string;
  role: string;
}

export type TState = Readonly<IUser>;

const defaultState: TState = {
  id: '',
  email: '',
  first_name: '',
  last_name: '',
  role: ''
};

const userItem = createReducer(defaultState, {
  [SIGN_IN.success]: (state: TState, {payload: {user}}) => ({
    ...user
  })
});

export default userItem;
