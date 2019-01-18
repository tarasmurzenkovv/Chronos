import {combineEpics} from 'redux-observable';

import {fetchTimesheetListByDateEpic} from './fetchTimesheetListByDateEpic';

export default combineEpics(fetchTimesheetListByDateEpic);
