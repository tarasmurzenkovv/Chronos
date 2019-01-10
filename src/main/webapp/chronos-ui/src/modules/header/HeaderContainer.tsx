import {compose, withState, withHandlers} from 'recompose';

import Header from './Header';

export default compose<any, {}>(
  withState('isActive', 'changeBtnState', false),
  withHandlers({
    handleBtnChange: ({isActive, changeBtnState}) => () => {
      changeBtnState(!isActive);
    }
  })
)(Header);
