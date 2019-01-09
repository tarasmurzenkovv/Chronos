import {connect} from 'react-redux';
import {compose, lifecycle, withHandlers, withState} from 'recompose';
import * as moment from 'moment';

import getProjectsList from 'modules/modals/actions/api/getProjectsList';
import {removeCurrentModal} from 'modules/modals/actions/modalsActions';
import createRecordApi from 'modules/modals/actions/api/createRecordApi';

import TimesheetRecordModal from './TimesheetRecordModal';

const mapStateToProps = (state) => ({
  list: state.projects.list,
  userId: state.auth.signIn.user.id
});

const mapDispatchToProps = {
  removeCurrentModal,
  getProjectsList,
  createRecordApi
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

  withHandlers({
    handleOnClose: ({isOpen, setIsOpen, removeCurrentModal}) => () => {
      if (isOpen) {
        setIsOpen(false);
        removeCurrentModal();
      }
    },

    handleProjectChange: ({projectId, setProjectId}) => (event) => {
      const {value} = event.target;
      if (projectId !== value) {
        setProjectId(value);
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
    handleFormSubmit: ({createRecordApi, handleOnClose, userId, projectId}) => (
      event
    ) => {
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

      createRecordApi(params)
        .then(() => handleOnClose())
        .catch(() => {});
    }
  }),

  lifecycle<IProps, {}>({
    componentDidMount() {
      this.props.getProjectsList();
    }
  })
)(TimesheetRecordModal);
