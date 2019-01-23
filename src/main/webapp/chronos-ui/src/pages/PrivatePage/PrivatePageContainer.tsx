import {connect} from 'react-redux';

import PrivatePage from './PrivatePage';

const mapStateToProps = (state) => ({
  id: state.common.user.id
});

export default connect(mapStateToProps)(PrivatePage);
