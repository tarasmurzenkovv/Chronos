import {connect} from 'react-redux';
import {compose} from 'recompose';

import CurrentModal from './CurrentModal';

const mapStateToProps = (state) => ({
  modal: state.modals[0]
});
const mapDispatchToProps = null;
export default compose(
  connect(
    mapStateToProps,
    mapDispatchToProps
  )
)(CurrentModal);
