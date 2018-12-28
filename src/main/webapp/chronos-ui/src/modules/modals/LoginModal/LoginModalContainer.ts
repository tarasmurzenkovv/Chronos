import {connect} from 'react-redux';
import {compose, withState, withHandlers} from 'recompose';

import {removeCurrentModal} from 'modules/modals/actions/modals';

import LoginModal from './LoginModal';

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
    }
  })
)(LoginModal);
