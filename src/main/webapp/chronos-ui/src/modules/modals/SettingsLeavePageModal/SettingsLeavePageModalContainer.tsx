import {connect} from 'react-redux';
import {compose, withHandlers, withState} from 'recompose';
import {removeCurrentModal} from 'modules/modals/actions/modalsActions';
import {deleteRecordApi} from '../../modals/actions/api/deleteRecordApi';
import SettingsLeavePageModal from '../SettingsLeavePageModal';

const mapStateToProps = (state) => ({
  selectedId: state.timesheet.selectedId
});

const mapDispatchToProps = {
  deleteRecordApi,
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
  })
)(SettingsLeavePageModal);
