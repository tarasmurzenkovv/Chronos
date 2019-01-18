import * as React from 'react';
import {connect} from 'react-redux';
import {compose, lifecycle, setDisplayName, withHandlers, withState} from 'recompose';
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

import {
  fetchTimesheetListByDateApi,
  IProps as fetchTimesheetListByDateIProps
} from '../../actions/api/fetchTimesheetListByDateApi';
import TimesheetList from './TimesheetList';


const mapStateToProps = (state) => {
  const timesheetList= state.timesheet.list;
  const projectsList= state.projects.list;

  const month= state.timesheet.filters.date.month;
  const startOfMonth= state.timesheet.filters.date.startOfMonth;
  const endOfMonth= state.timesheet.filters.date.endOfMonth;

  const list = formatTimesheetList(timesheetList, projectsList);

  const visibleToUser = isVisibleToUser(state);

  return {
    endOfMonth,
    list,
    month,
    startOfMonth,
    visibleToUser
  }
};

const mapDispatchToProps = {
  addModal,
  selectRecord,
  fetchTimesheetListByDateApi,
  getProjectsList,
  setMonthFilter,
};

interface IProps {
  userId: number;
  monthFilter: string;
  startOfMonth: string;
  endOfMonth: string;
  getProjectsList: () => void;
  fetchTimesheetListByDateApi: ({id, start, end}: fetchTimesheetListByDateIProps) => void;
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
    handleMonthFilterButtonsClick: ({userId, monthFilter, fetchTimesheetListByDateApi, startOfMonth, endOfMonth}) => () =>{
        fetchTimesheetListByDateApi({id: userId, start: startOfMonth, end: endOfMonth})
      ;
    }
  }),

  withHandlers({

    /* eslint-disable no-shadow */
    handleButtonClick: ({addModal}) => () =>
      addModal({id: TIMESHEET_RECORD_MODAL}),

    handleAddMonthFilterButtonClick: ({month, setMonthFilter, handleMonthFilterButtonsClick}) => () => {
      const newMonthFilter = moment(month).add(1, 'months');
      setMonthFilter(newMonthFilter)
        .then(()=>handleMonthFilterButtonsClick());
    },

    handleMinusMonthFilterButtonClick: ({month, setMonthFilter, handleMonthFilterButtonsClick}) => () =>{
      const newMonthFilter = moment(month).subtract(1, 'months');
      setMonthFilter(newMonthFilter).then(()=>handleMonthFilterButtonsClick());
    },

    handleDeleteButtonClick: ({addModal, selectRecord}) => (id) => {
      addModal({id: TIMESHEET_RECORD_DELETE_MODAL});
      selectRecord(id);
    },

    handleEditButtonClick: ({addModal}) => () => {
      addModal({id: TIMESHEET_RECORD_EDIT_MODAL});
    },

    handleRowEnter: ({hoveredRow, setHoveredRow}) => (id) => () => {
      setHoveredRow(id);
    },

    handleRowLeave: ({hoveredRow, setHoveredRow}) => () => {
      setHoveredRow(null);
    },

    handleRowClick: ({selectRecord, isIconVisible, setHoveredRow}) => (id) => () => {
      selectRecord(id);
      setHoveredRow(id);
    }

  }),
  lifecycle<IProps, {}>({
    componentDidMount() {
      const {fetchTimesheetListByDateApi, getProjectsList, userId, startOfMonth, endOfMonth, setMonthFilter} = this.props;

      const today = moment();

      setMonthFilter(today);

      Promise.all([
        getProjectsList(),
        fetchTimesheetListByDateApi({id: userId, start: startOfMonth, end: endOfMonth})
      ]);
    }
  }),

  React.memo
)(TimesheetList);
