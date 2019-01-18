import {combineReducers} from 'redux';
import {connectRouter} from 'connected-react-router';

import modals, {projects} from 'modules/modals/reducers';
import auth from 'modules/authorization/reducers';
import timesheet from 'modules/timesheet/reducers';
import common from 'modules/common/reducers';

export default (history) =>
  combineReducers({
    auth,
    modals,
    projects,
    router: connectRouter(history),
    timesheet,
    common
  });
