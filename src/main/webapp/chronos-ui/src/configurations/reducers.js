import {combineReducers} from 'redux';
import {connectRouter} from 'connected-react-router';

import modals from '../modules/modals/reducers';

export default (history) =>
  combineReducers({
    router: connectRouter(history),
    modals
  });
