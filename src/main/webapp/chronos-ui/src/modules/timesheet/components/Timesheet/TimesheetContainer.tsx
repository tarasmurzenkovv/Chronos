import * as React from 'react';
import {connect} from 'react-redux';
import {
  compose,
  lifecycle,
  setDisplayName,
  withHandlers,
  withProps
} from 'recompose';
import {addModal} from 'modules/modals/actions/modalsActions';
import getProjectsList from 'modules/modals/actions/api/getProjectsList';
import {TIMESHEET_RECORD_MODAL} from 'modules/modals/constants';
import {fetchTimesheetListApi} from '../../actions/api/fetchTimesheetListApi';
import Timesheet from './Timesheet';

const mapStateToProps = (state) => ({
  userId: state.auth.signIn.user.id,
  timesheetList: state.timesheet.list,
  projectsList: state.projects.list
});

const mapDispatchToProps = {addModal, fetchTimesheetListApi, getProjectsList};

interface IProps {
  userId: number;
  getProjectsList: () => void;
  fetchTimesheetListApi: (id: number) => void;
}

export default compose<any, any>(
  connect(
    mapStateToProps,
    mapDispatchToProps
  ),

  setDisplayName('TimesheetComponent'),

  withProps(({projectsList, timesheetList}) => {
    const list = timesheetList.map((timesheetItem) => ({
      ...timesheetItem,
      ...projectsList.find(
        (projectItem) => projectItem.id === timesheetItem.project_id
      )
    }));
    return {list};
  }),

  withHandlers({
    handleButtonClick: ({addModal}) => () =>
      addModal({id: TIMESHEET_RECORD_MODAL})
  }),
  lifecycle<IProps, {}>({
    componentDidMount() {
      const {fetchTimesheetListApi, getProjectsList, userId} = this.props;
      Promise.all([getProjectsList(), fetchTimesheetListApi(userId)]);
    }
  }),

  React.memo
)(Timesheet);
