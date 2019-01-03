import {combineReducers} from 'redux';
import {connectRouter} from 'connected-react-router';

import modals from 'modules/modals/reducers';
import auth from 'modules/authorization/reducers';

export default (history) =>
  combineReducers({
    router: connectRouter(history),
    modals,
    auth
  });
