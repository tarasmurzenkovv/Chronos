import {connect} from 'react-redux';
import {compose, lifecycle, withHandlers, withState} from 'recompose';
import * as moment from 'moment';

import getProjectsList from 'modules/modals/actions/api/getProjectsList';
import {removeCurrentModal} from 'modules/modals/actions/modalsActions';
import {createRecordApi} from 'modules/modals/actions/api/createRecordApi';
import {fetchTimesheetListApi} from 'modules/timesheet/actions/api/fetchTimesheetListApi';
import TimesheetRecordModal from './TimesheetRecordModal';

const mapStateToProps = (state) => ({
  list: state.projects.list,
  userId: state.auth.signIn.user.id
});

const mapDispatchToProps = {
  createRecordApi,
  fetchTimesheetListApi,
  getProjectsList,
  removeCurrentModal
};

interface IProps {
  getProjectsList: () => void;
}

export default compose(
  connect(
    mapStateToProps,
    mapDispatchToProps
  ),

  withState('isOpen', 'setIsOpen', true),
  withState('projectId', 'setProjectId', ''),
  withState('date', 'setDate', new Date().toISOString().slice(0, 10)),
  withState('comments', 'setComments', ''),
  withState('hasError', 'handleError', false),
  withState('isSelected', 'handleSelection', null),
  withState('isTimeEmpty', 'handleTimeInput', null),

  withHandlers({
    handleOnClose: ({isOpen, setIsOpen, removeCurrentModal}) => () => {
      if (isOpen) {
        setIsOpen(false);
        removeCurrentModal();
      }
    },

    handleProjectChange: ({
      projectId,
      setProjectId,
      isSelected,
      hasError,
      handleSelection,
      handleError
    }) => (event) => {
      const {value} = event.target;
      handleSelection(value);
      if (hasError) {
        handleError(!hasError);
      }
      if (projectId !== value) {
        setProjectId(value);
      }
    },

    handleTimeChange: ({hasError, handleError, handleTimeInput}) => (event) => {
      const {value} = event.target;
      handleTimeInput(value);
      if (hasError) {
        handleError(!hasError);
      }
    },

    handleDateChange: ({date, setDate}) => (event) => {
      const {value} = event.target;
      if (date !== value) {
        setDate(value);
      }
    },

    handleCommentsChange: ({comments, setComments}) => (event) => {
      const {value} = event.target;
      if (comments !== value) {
        setComments(value);
      }
    }
  }),

  withHandlers({
    handleFormSubmit: ({
      createRecordApi,
      fetchTimesheetListApi,
      handleOnClose,
      projectId,
      userId,
      isSelected,
      hasError,
      handleError,
      isTimeEmpty
    }) => (event) => {
      event.preventDefault();
      const time = event.target.time.value;
      const date = event.target.date.value;
      const comments = event.target.comments.value;

      const params = {
        comments,
        project_id: projectId,
        reporting_date: moment(date).format('DD/MM/YYYY'),
        spent_time: time,
        user_id: userId
      };

      if (isSelected === null || isTimeEmpty === null) {
        handleError(!hasError);
      }

      createRecordApi(params)
        .then(() => {
          handleOnClose();
          fetchTimesheetListApi(userId);
        })
        .catch(() => {});
    }
  }),

  lifecycle<IProps, {}>({
    componentDidMount() {
      this.props.getProjectsList();
    }
  })
)(TimesheetRecordModal);
