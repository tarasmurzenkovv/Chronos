import {connect} from 'react-redux';
import {compose, lifecycle, withHandlers, withState} from 'recompose';

import {getProjectsList} from 'modules/modals/actions/_api/getProjectsList';
import {removeCurrentModal} from 'modules/modals/actions/modalsActions';

import TimesheetRecordModal from './TimesheetRecordModal';

const mapStateToProps = (state) => ({
  list: state.projects.list
});

interface DispatchProps {
  removeCurrentModal: () => void;
  getProjectsList: () => void;
}

const mapDispatchToProps: DispatchProps = {removeCurrentModal, getProjectsList};

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
    },

    handleFormSubmit: ({}) => (event) => {
      event.preventDefault();
      const projectId = event.target.projectId.value;
      const time = event.target.time.value;
      const date = event.target.date.value;
      const comments = event.target.comments.value;
    }
  }),

  lifecycle<IProps, {}>({
    componentDidMount() {
      this.props.getProjectsList();
    }
  })
)(TimesheetRecordModal);
