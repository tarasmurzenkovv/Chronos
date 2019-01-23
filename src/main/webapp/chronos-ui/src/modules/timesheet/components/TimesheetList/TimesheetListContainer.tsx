import * as React from 'react';
import {connect} from 'react-redux';
import {
  compose,
  lifecycle,
  setDisplayName,
  withHandlers,
  withState
} from 'recompose';
import * as moment from 'moment';

import {addModal} from 'modules/modals/actions/modalsActions';
import getProjectsList from 'modules/modals/actions/api/getProjectsList';

import {
  TIMESHEET_RECORD_DELETE_MODAL,
  TIMESHEET_RECORD_EDIT_MODAL,
  TIMESHEET_RECORD_MODAL
} from 'modules/modals/constants';

import {isVisibleToUser} from 'shared/utils';

import {selectRecord, setMonthFilter} from '../../actions/timesheetRecord';
import {formatTimesheetList} from '../../utils/formatTimesheetList';

import TimesheetList from './TimesheetList';

const mapStateToProps = (state) => {
  const timesheetList = state.timesheet.list;
  const projectsList = state.projects.list;

  const month = state.timesheet.filters.date.month;

  const list = formatTimesheetList(timesheetList, projectsList);

  const visibleToUser = isVisibleToUser(state);

  return {
    list,
    month,
    visibleToUser
  };
};

const mapDispatchToProps = {
  addModal,
  selectRecord,
  getProjectsList,
  setMonthFilter
};

interface IProps {
  monthFilter: string;
  getProjectsList: () => void;
  setMonthFilter: (month: any) => void;
}

export default compose(
  setDisplayName('TimesheetList'),

  connect(
    mapStateToProps,
    mapDispatchToProps
  ),

  withState('hoveredRow', 'setHoveredRow', null),

  withHandlers({
    /* eslint-disable no-shadow */
    handleButtonClick: ({addModal}) => () =>
      addModal({id: TIMESHEET_RECORD_MODAL}),

    handleAddMonthFilterButtonClick: ({month, setMonthFilter}) => () => {
      const newMonthFilter = moment(month).add(1, 'months');
      setMonthFilter(newMonthFilter);
    },

    handleMinusMonthFilterButtonClick: ({month, setMonthFilter}) => () => {
      const newMonthFilter = moment(month).subtract(1, 'months');
      setMonthFilter(newMonthFilter);
    },

    handleDeleteButtonClick: ({addModal, selectRecord}) => (id) => {
      addModal({id: TIMESHEET_RECORD_DELETE_MODAL});
      selectRecord(id);
    },

    handleEditButtonClick: ({addModal}) => () => {
      addModal({id: TIMESHEET_RECORD_EDIT_MODAL});
    },
    handleRowEnter: ({setHoveredRow}) => (id) => () => {
      setHoveredRow(id);
    },

    handleRowLeave: ({setHoveredRow}) => () => {
      setHoveredRow(null);
    },

    handleRowClick: ({selectRecord, setHoveredRow}) => (id) => () => {
      selectRecord(id);
      setHoveredRow(id);
    }
  }),
  lifecycle<IProps, {}>({
    componentDidMount() {
      const {getProjectsList, setMonthFilter} = this.props;

      const today = moment();

      setMonthFilter(today);

      getProjectsList();
    }
  }),

  React.memo
)(TimesheetList);
