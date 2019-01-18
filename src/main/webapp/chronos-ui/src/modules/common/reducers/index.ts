import {combineReducers} from 'redux';

import drawer from './drawer';
import users from './users';

export default combineReducers({
  drawer,
  users
});
