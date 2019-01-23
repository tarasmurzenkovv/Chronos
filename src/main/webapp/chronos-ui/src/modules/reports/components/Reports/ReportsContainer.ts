import * as moment from 'moment';
import {connect} from 'react-redux';
import {compose, withHandlers, withState} from 'recompose';

import {sortAndCreateFullName} from 'shared/utils';
import {IUserId} from 'shared/types';

import {selectUserIdForReports} from '../../actions/reports';
import {fetchReport} from '../../actions/api/fetchReport';

import Reports from './Reports';

const mapStateToProps = (state) => ({
  list: sortAndCreateFullName(state.common.usersList.list),
  selectedUsersIds: state.reports.selectedUsersIds
});

const mapDispatchToProps = {
  fetchReport,
  selectUserIdForReports
};

export default compose(
  connect(
    mapStateToProps,
    mapDispatchToProps
  ),

  withState('startDay', 'setStartDay', moment().startOf('month')),
  withState('endDay', 'setEndDay', moment()),

  withHandlers({
    /* eslint-disable no-shadow */
    handleUserlistItemClick: ({selectUserIdForReports}) => (
      id: IUserId
    ) => () => {
      selectUserIdForReports(id);
    },

    handleStartDayChange: ({setStartDay}) => (date) => {
      setStartDay(date);
    },

    handleEndDayChange: ({setEndDay}) => (date) => {
      setEndDay(date);
    },

    handleExportButtonClick: ({
      fetchReport,
      selectedUsersIds,
      startDay,
      endDay
    }) => (event) => {
      event.preventDefault();
      fetchReport({ids: selectedUsersIds, start: startDay, end: endDay});
    }
  })
)(Reports);
