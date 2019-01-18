import {connect} from 'react-redux';

import {isUserAdmin} from 'shared/utils';
import {setDrawerStatus} from '../../actions/drawer';
import Header from './Header';

const mapStateToProps = (state) => ({
  pathname: state.router.location.pathname,
  userName: `${state.auth.signIn.user.first_name} ${
    state.auth.signIn.user.last_name
  }`,
  isAdmin: isUserAdmin(state),
  isDrawerOpen: state.common.drawer.isOpen
});

const mapDispatchToProps = {setDrawerStatus};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Header);
