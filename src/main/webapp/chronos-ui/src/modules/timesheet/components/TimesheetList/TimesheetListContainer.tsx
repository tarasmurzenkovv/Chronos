import * as React from 'react';
import {connect} from 'react-redux';
import {compose, lifecycle, setDisplayName, withHandlers, withState} from 'recompose';
import * as moment from 'moment';

import {defaultDateFormatApi} from 'shared/utils/constants';

import {addModal} from 'modules/modals/actions/modalsActions';
import getProjectsList from 'modules/modals/actions/api/getProjectsList';
import {TIMESHEET_RECORD_DELETE_MODAL, TIMESHEET_RECORD_MODAL} from 'modules/modals/constants';
import {selectRecord} from '../../actions/timesheetRecord';

import {formatTimesheetList} from '../../utils/formatTimesheetList';

import {
  fetchTimesheetListByDateApi,
  IProps as fetchTimesheetListByDateIProps
} from '../../actions/api/fetchTimesheetListByDateApi';
import TimesheetList from './TimesheetList';

const mapStateToProps = (state) => {
  const userId= state.auth.signIn.user.id;
  const timesheetList= state.timesheet.list;
  const projectsList= state.projects.list;

  const list = formatTimesheetList(timesheetList, projectsList);

  return {
    userId,
    list
  }
};

const mapDispatchToProps = {
  addModal,
  selectRecord,
  fetchTimesheetListByDateApi,
  getProjectsList
};

interface IProps {
  userId: number;
  monthFilter: string;
  getProjectsList: () => void;
  fetchTimesheetListByDateApi: ({id, start, end}: fetchTimesheetListByDateIProps) => void;
}

export default compose(
  setDisplayName('TimesheetList'),

  connect(
    mapStateToProps,
    mapDispatchToProps
  ),

  withState('monthFilter', 'setMonthFilter', moment()),

  withHandlers({
    handleMonthFilterButtonsClick: ({userId, monthFilter, fetchTimesheetListByDateApi}) => (newMonthFilter) =>{
      const startOfMonth = moment(newMonthFilter)
        .startOf('month')
        .format(defaultDateFormatApi);

      const endOfMonth = moment(newMonthFilter)
        .endOf('month')
        .format(defaultDateFormatApi);

        fetchTimesheetListByDateApi({id: userId, start: startOfMonth, end: endOfMonth})
      ;
    }
  }),

  withHandlers({

    /* eslint-disable no-shadow */
    handleButtonClick: ({addModal}) => () =>
      addModal({id: TIMESHEET_RECORD_MODAL}),

    handleAddMonthFilterButtonClick: ({monthFilter, setMonthFilter, handleMonthFilterButtonsClick}) => () => {
      const newMonthFilter = moment(monthFilter).add(1, 'months');
      setMonthFilter(newMonthFilter);
      handleMonthFilterButtonsClick(newMonthFilter);
    },

    handleMinusMonthFilterButtonClick: ({monthFilter, setMonthFilter, handleMonthFilterButtonsClick}) => () =>{
      setMonthFilter(moment(monthFilter).subtract(1, 'months'));
      handleMonthFilterButtonsClick();
    },

    handleDeleteButtonClick: ({addModal, selectRecord}) => (id) => {
      addModal({id: TIMESHEET_RECORD_DELETE_MODAL});
      selectRecord(id);
    }
  }),
  lifecycle<IProps, {}>({
    componentDidMount() {
      const {fetchTimesheetListByDateApi, getProjectsList, userId, monthFilter} = this.props;

      const startOfMonth = moment(monthFilter)
        .startOf('month')
        .format(defaultDateFormatApi);

      const endOfMonth = moment(monthFilter)
        .endOf('month')
        .format(defaultDateFormatApi);

      Promise.all([
        getProjectsList(),
        fetchTimesheetListByDateApi({id: userId, start: startOfMonth, end: endOfMonth})
      ]);
    }
  }),

  React.memo
)(TimesheetList);
