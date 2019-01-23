import {combineReducers} from 'redux';
import {connectRouter} from 'connected-react-router';

import auth from 'modules/authorization/reducers';
import common from 'modules/common/reducers';
import settings from 'modules/settings/reducers';
import modals, {projects} from 'modules/modals/reducers';
import timesheet from 'modules/timesheet/reducers';
import reports from 'modules/reports/reducers';

export default (history) =>
  combineReducers({
    auth,
    common,
    modals,
    projects,
    reports,
    router: connectRouter(history),
    timesheet,
    settings
  });
