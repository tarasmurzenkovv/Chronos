import {connect} from 'react-redux';
import {compose, withHandlers, withState} from 'recompose';

import {removeCurrentModal} from 'modules/modals/actions/modalsActions';

import TimesheetRecordModal from './TimesheetRecordModal';

const mapStateToProps = null;
const mapDispatchToProps = {removeCurrentModal};
export default compose(
  connect(
    mapStateToProps,
    mapDispatchToProps
  ),
  withState('isOpen', 'setIsOpen', true),
  withHandlers({
    handleOnClose: ({isOpen, setIsOpen, removeCurrentModal}) => () => {
      if (isOpen) {
        setIsOpen(false);
        removeCurrentModal();
      }
    },
    handleOnSave: () => () => console.log('handleOnSave')
  })
)(TimesheetRecordModal);
