import {connect} from 'react-redux';
import {compose, withHandlers, withState} from 'recompose';
import {removeCurrentModal} from 'modules/modals/actions/modalsActions';
import {fetchTimesheetListByDateApi} from 'modules/timesheet/actions/api/fetchTimesheetListByDateApi';
import TimesheetDeleteModal from './TimesheetDeleteModal';
import {deleteRecordApi} from '../../modals/actions/api/deleteRecordApi';

const mapStateToProps = (state) => ({
  selectedId: state.timesheet.selectedId,
  userId: state.auth.signIn.user.id,

  startOfMonth: state.timesheet.filters.date.startOfMonth,
  endOfMonth: state.timesheet.filters.date.endOfMonth
});

const mapDispatchToProps = {
  deleteRecordApi,
  fetchTimesheetListByDateApi,
  removeCurrentModal
};

export default compose(
  connect(
    mapStateToProps,
    mapDispatchToProps
  ),
  withState('isOpen', 'setIsOpen', true),

  /* eslint-disable no-shadow */
  withHandlers({
    handleOnClose: ({isOpen, setIsOpen, removeCurrentModal}) => () => {
      if (isOpen) {
        setIsOpen(false);
        removeCurrentModal();
      }
    }
  }),
  withHandlers({
    handleDeleteItem: ({
      deleteRecordApi,
      fetchTimesheetListByDateApi,
      handleOnClose,
      selectedId,
      userId,

      startOfMonth,
      endOfMonth
    }) => () => {
      deleteRecordApi(selectedId)
        .then(() =>
          fetchTimesheetListByDateApi({
            id: userId,
            start: startOfMonth,
            end: endOfMonth
          })
        )
        .catch(() => {})
        .finally(handleOnClose());
    }
  })
)(TimesheetDeleteModal);
