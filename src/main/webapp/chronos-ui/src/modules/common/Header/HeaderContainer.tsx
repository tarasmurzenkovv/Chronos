import {connect} from 'react-redux';

import Header from './Header';

const mapStateToProps = (state) => ({
  pathname: state.router.location.pathname,
  userName: `${state.auth.signIn.user.first_name} ${
    state.auth.signIn.user.last_name
  }`
});
export default connect(mapStateToProps)(Header);
