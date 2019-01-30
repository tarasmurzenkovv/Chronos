import {LOCATION_CHANGE} from 'connected-react-router';

import {createReducer} from 'shared/utils/createReducer';
import requestsStatuses from 'shared/utils/requestsStatuses';

import {GET_PROJECTS_LIST} from '../actions/constants';

export interface IListItem {
  id: number;
  project_name: string;
  project_description: string;
  project_type_id: number;
  color: string;
  deleted: boolean;
  isNew: boolean;
}

export type TState = Readonly<{
  status: string;
  list: IListItem[];
  isLoading: boolean;
}>;

const defaultState: TState = {
  status: requestsStatuses.default,
  list: [],
  isLoading: false
};

const projects = createReducer(defaultState, {
  [GET_PROJECTS_LIST.pending]: (state: TState, {payload: {isLoading}}) => ({
    ...state,
    status: requestsStatuses.pending,
    isLoading
  }),

  [GET_PROJECTS_LIST.failure]: (state: TState) => ({
    ...state,
    status: requestsStatuses.failure
  }),

  [GET_PROJECTS_LIST.success]: (
    state: TState,
    {payload: {list, isLoading}}
  ) => ({
    ...state,
    status: requestsStatuses.success,
    list,
    isLoading
  }),
  [LOCATION_CHANGE]: () => defaultState
});

export default projects;
