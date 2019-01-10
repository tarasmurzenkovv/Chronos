import {LOCATION_CHANGE} from 'connected-react-router';

import {createReducer} from 'shared/utils/createReducer';
import requestsStatuses from 'shared/utils/requestsStatuses';

import {GET_PROJECTS_LIST} from '../actions/constants';

export interface IListItem {
  id: number;
  project_name: string;
  project_description: string;
  project_type_id: number;
}

export type TState = Readonly<{
  status: string;
  list: IListItem[];
}>;

const defaultState: TState = {
  status: requestsStatuses.default,
  list: []
};

const projects = createReducer(defaultState, {
  [GET_PROJECTS_LIST.pending]: (state: TState) => ({
    ...state,
    status: requestsStatuses.pending
  }),

  [GET_PROJECTS_LIST.failure]: (state: TState) => ({
    ...state,
    status: requestsStatuses.failure
  }),

  [GET_PROJECTS_LIST.success]: (state: TState, {payload: {list}}) => ({
    ...state,
    status: requestsStatuses.success,
    list
  }),
  [LOCATION_CHANGE]: () => defaultState
});

export default projects;
