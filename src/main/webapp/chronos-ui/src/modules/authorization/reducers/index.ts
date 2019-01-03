import {combineReducers} from 'redux';

import signUp from './signUp';
import signIn from './signIn';
import forgotPassword from './forgotPassword';

export default combineReducers({
  signUp,
  signIn,
  forgotPassword
});
