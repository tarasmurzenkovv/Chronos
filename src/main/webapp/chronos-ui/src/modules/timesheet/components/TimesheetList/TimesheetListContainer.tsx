import * as React from 'react';
import {connect} from 'react-redux';
import {compose, lifecycle, setDisplayName, withHandlers, withProps, withState} from 'recompose';

import {addModal} from 'modules/modals/actions/modalsActions';
import getProjectsList from 'modules/modals/actions/api/getProjectsList';
import {TIMESHEET_RECORD_MODAL} from 'modules/modals/constants';

import * as moment from 'moment';
import {
  fetchTimesheetListByDateApi,
  IProps as fetchTimesheetListByDateIProps
} from '../../actions/api/fetchTimesheetListByDateApi';
import TimesheetList from './TimesheetList';

const mapStateToProps = (state) => ({
  userId: state.auth.signIn.user.id,
  timesheetList: state.timesheet.list,
  projectsList: state.projects.list
});

const mapDispatchToProps = {addModal, fetchTimesheetListByDateApi, getProjectsList};

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

  withProps(({projectsList, timesheetList}) => {
    const list = timesheetList.map((timesheetItem) => ({
      ...timesheetItem,
      project_name: projectsList.length
        ? projectsList.find(
          (projectItem) => projectItem.id === timesheetItem.project_id
        ).project_name
        : ''
    }));

    return {list};
  }),

  withHandlers({
    handleMonthFilterButtonsClick: ({userId, monthFilter, fetchTimesheetListByDateApi}) => () =>{
      const startOfMonth = moment(monthFilter)
        .startOf('month')
        .format('DD/MM/YYYY');

      const endOfMonth = moment(monthFilter)
        .endOf('month')
        .format('DD/MM/YYYY');

        fetchTimesheetListByDateApi({id: userId, start: startOfMonth, end: endOfMonth})
      ;
    }
  }),

  withHandlers({

    /* eslint-disable no-shadow */
    handleButtonClick: ({addModal}) => () =>
      addModal({id: TIMESHEET_RECORD_MODAL}),

    handleAddMonthFilterButtonClick: ({monthFilter, setMonthFilter, handleMonthFilterButtonsClick}) => () => {
      setMonthFilter(moment(monthFilter).add(1, 'months'));
      handleMonthFilterButtonsClick();
    },

    handleMinusMonthFilterButtonClick: ({monthFilter, setMonthFilter, handleMonthFilterButtonsClick}) => () =>{
      setMonthFilter(moment(monthFilter).subtract(1, 'months'));
      handleMonthFilterButtonsClick();
    }
  }),
  lifecycle<IProps, {}>({
    componentDidMount() {
      const {fetchTimesheetListByDateApi, getProjectsList, userId, monthFilter} = this.props;

      const startOfMonth = moment(monthFilter)
        .startOf('month')
        .format('DD/MM/YYYY');

      const endOfMonth = moment(monthFilter)
        .endOf('month')
        .format('DD/MM/YYYY');

      Promise.all([
        getProjectsList(),
        fetchTimesheetListByDateApi({id: userId, start: startOfMonth, end: endOfMonth})
      ]);
    }
  }),

  React.memo
)(TimesheetList);
