import {connect} from 'react-redux';
import {compose, lifecycle, withHandlers, withState} from 'recompose';
import * as moment from 'moment';

import {defaultDateFormatApi} from 'shared/utils/constants';

import getProjectsList from 'modules/modals/actions/api/getProjectsList';
import {removeCurrentModal} from 'modules/modals/actions/modalsActions';
import {editRecordApi} from 'modules/modals/actions/api/editRecordApi';

import TimesheetRecordModal from './TimesheetEditModal';

const mapStateToProps = (state) => ({
  list: state.projects.list,
  userId: state.common.user.id,

  selectedId: state.timesheet.selectedId,
  selectedItemData: state.timesheet.list.find(
    (item) => item.task_id === state.timesheet.selectedId
  )
});

const mapDispatchToProps = {
  editRecordApi,
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
  withState(
    'projectId',
    'setProjectId',
    ({selectedItemData}) => selectedItemData.project_id
  ),
  withState('selectProjectError', 'setSelectProjectError', false),
  withState('timeError', 'setTimeError', false),
  withState('date', 'setDate', ({selectedItemData}) =>
    moment(selectedItemData.reporting_date, 'DD-MM-YYYY').format('YYYY-MM-DD')
  ),

  /* eslint-disable no-shadow */
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
      selectProjectError,
      setSelectProjectError
    }) => (event) => {
      if (selectProjectError) {
        setSelectProjectError(false);
      }

      const {value} = event.target;

      if (projectId !== value) {
        setProjectId(value);
      }
    },

    handleTimeChange: ({timeError, setTimeError}) => () => {
      if (timeError && timeError > 0) {
        setTimeError(false);
      }
    },

    handleDateChange: ({setDate}) => (date) => {
      setDate(date);
    }
  }),

  withHandlers({
    handleFormSubmit: ({
      editRecordApi,
      date,
      handleOnClose,
      projectId,
      setSelectProjectError,
      setTimeError,
      selectedItemData,
      userId
    }) => (event) => {
      event.preventDefault();

      const time = event.target.time.value;

      if (!projectId) {
        setSelectProjectError(true);
      }

      if (!time || time <= 0) {
        setTimeError(true);
        return;
      }

      const comments = event.target.comments.value;

      const params = {
        comments,
        project_id: projectId,
        reporting_date: moment(date).format(defaultDateFormatApi),
        spent_time: time,
        task_id: selectedItemData.task_id,
        user_id: userId
      };

      editRecordApi(params)
        .then(() => {
          handleOnClose();
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
