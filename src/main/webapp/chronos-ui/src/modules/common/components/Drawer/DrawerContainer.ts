import {connect} from 'react-redux';
import {compose, lifecycle, withHandlers} from 'recompose';
import sortBy from 'lodash/sortBy';

import {fetchUsersList} from '../../actions/api/fetchUsersList';
import {selectUserInUserlist, setDrawerStatus} from '../../actions/drawer';
import Drawer from './Drawer';

const mapStateToProps = (state) => ({
  isOpen: state.common.drawer.isOpen,
  list: sortBy(state.common.users.list, ['first_name'])
});

const mapDispatchToProps = {
  fetchUsersList,
  selectUserInUserlist,
  setDrawerStatus
};

interface IProps {
  fetchUsersList: () => void;
}

export default compose(
  connect(
    mapStateToProps,
    mapDispatchToProps
  ),
  withHandlers({
    /* eslint-disable no-shadow */
    handleUserlistItemClick: ({setDrawerStatus, selectUserInUserlist}) => (
      id
    ) => () => {
      selectUserInUserlist(id);
      setDrawerStatus();
    }
  }),

  lifecycle<IProps, {}>({
    componentDidMount() {
      this.props.fetchUsersList();
    }
  })
)(Drawer);
