import {connect} from 'react-redux';

import PrivatePage from './PrivatePage';

const mapStateToProps = (state) => ({
  id: state.auth.signIn.user.id
});

const mapDispatchToProps = {};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PrivatePage);
