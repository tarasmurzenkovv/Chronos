import {connect} from 'react-redux';
import {compose, withHandlers, withState} from 'recompose';
import {removeCurrentModal} from 'modules/modals/actions/modalsActions';
import TimesheetDeleteModal from './TimesheetDeleteModal';
import {deleteRecordApi} from '../../modals/actions/api/deleteRecordApi';
import {fetchTimesheetListApi} from '../../timesheet/actions/api/fetchTimesheetListApi';

const mapStateToProps = (state) => ({
  selectedId: state.timesheet.selectedId,
  userId: state.auth.signIn.user.id
});

const mapDispatchToProps = {
  removeCurrentModal,
  fetchTimesheetListApi,
  deleteRecordApi
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
      selectedId,
      handleOnClose,
      userId,
      deleteRecordApi,
      fetchTimesheetListApi
    }) => () => {
      deleteRecordApi(selectedId)
        .then(() => fetchTimesheetListApi(userId))
        .catch(() => {})
        .finally(handleOnClose());
    }
  })
)(TimesheetDeleteModal);
