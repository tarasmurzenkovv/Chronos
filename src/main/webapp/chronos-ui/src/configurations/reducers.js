import {combineReducers} from 'redux';
import {connectRouter} from 'connected-react-router';

import template from '../modules/module1/reducers';

export default (history) =>
  combineReducers({
    router: connectRouter(history),
    template
  });
