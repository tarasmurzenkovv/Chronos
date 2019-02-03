import {connect} from 'react-redux';
import {compose, withHandlers} from 'recompose';

import {isUserAdmin, redirectTo} from 'shared/utils';

import {setDrawerStatus} from '../../actions/drawer';
import Header from './Header';

const mapStateToProps = (state) => ({
  pathname: state.router.location.pathname,
  userName: `${state.common.user.first_name} ${state.common.user.last_name}`,
  isAdmin: isUserAdmin(state),
  isDrawerOpen: state.common.drawer.isOpen
});

const mapDispatchToProps = {redirectTo, setDrawerStatus};

export default compose(
  connect(
    mapStateToProps,
    mapDispatchToProps
  ),
  withHandlers({
    /* eslint-disable no-shadow */
    handleButtonClick: ({redirectTo}) => (route) => () => {
      redirectTo(route);
    }
  })
)(Header);
