import {connect} from 'react-redux';

import {ADMIN_ROLE} from 'shared/constatns';
import {setDrawerStatus} from '../../actions/drawer';
import Header from './Header';

const mapStateToProps = (state) => ({
  pathname: state.router.location.pathname,
  userName: `${state.auth.signIn.user.first_name} ${
    state.auth.signIn.user.last_name
  }`,
  isAdmin: state.auth.signIn.user.role === ADMIN_ROLE,
  isDrawerOpen: state.common.drawer.isOpen
});

const mapDispatchToProps = {setDrawerStatus};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Header);
