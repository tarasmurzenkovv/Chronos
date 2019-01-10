import {connect} from 'react-redux';

import Header from './Header';

const mapStateToProps = (state) => ({
  pathname: state.router.location.pathname
});
export default connect(mapStateToProps)(Header);
