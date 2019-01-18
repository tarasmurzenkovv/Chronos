import {connect} from 'react-redux';

import PrivatePage from './PrivatePage';

const mapStateToProps = (state) => ({
  id: state.auth.signIn.user.id
});

export default connect(mapStateToProps)(PrivatePage);
