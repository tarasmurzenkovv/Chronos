import {combineReducers} from 'redux';

import drawer from './drawer';
import user from './user';
import usersList from './usersList';

export default combineReducers({
  drawer,
  user,
  usersList
});
