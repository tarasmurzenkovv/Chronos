import {combineEpics} from 'redux-observable';
import templateEpic from './templateEpic';

export default combineEpics(templateEpic);
