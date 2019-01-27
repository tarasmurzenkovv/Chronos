import {connect} from 'react-redux';
import {compose, lifecycle, withHandlers, withState} from 'recompose';
import * as moment from 'moment';

import {defaultDateFormatApi} from 'shared/utils/constants';

import getProjectsList from 'modules/modals/actions/api/getProjectsList';
import {removeCurrentModal} from 'modules/modals/actions/modalsActions';
import {createRecordApi} from 'modules/modals/actions/api/createRecordApi';

import TimesheetRecordModal from './TimesheetRecordModal';

const mapStateToProps = (state) => {
  const list = state.projects.list.filter((item) => !item.deleted);
  const userId = state.common.user.id;

  return {
    list,
    userId
  };
};

const mapDispatchToProps = {
  createRecordApi,
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
  withState('selectProjectError', 'setSelectProjectError', false),
  withState('timeError', 'setTimeError', false),
  withState('date', 'setDate', moment()),

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
      createRecordApi,
      date,
      handleOnClose,
      projectId,
      setSelectProjectError,
      setTimeError,
      userId
    }) => (event) => {
      event.preventDefault();

      const time = event.target.time.value;

      if (!projectId) {
        setSelectProjectError(true);
      }

      if (!time || time <= 0) {
        setTimeError(true);
      }

      if (!projectId || !time) return;

      const comments = event.target.comments.value;

      const params = {
        comments,
        project_id: projectId,
        reporting_date: moment(date).format(defaultDateFormatApi),
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
