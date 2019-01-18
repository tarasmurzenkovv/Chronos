import {connect} from 'react-redux';
import {compose, lifecycle, withHandlers} from 'recompose';
import sortBy from 'lodash/sortBy';

import {fetchTimesheetListByDateApi} from 'modules/timesheet/actions/api/fetchTimesheetListByDateApi';
import {fetchUsersList} from '../../actions/api/fetchUsersList';
import {selectUserInUserlist, setDrawerStatus} from '../../actions/drawer';
import Drawer from './Drawer';

const mapStateToProps = (state) => ({
  userId: state.auth.signIn.user.id,
  startOfMonth: state.timesheet.filters.date.startOfMonth,
  endOfMonth: state.timesheet.filters.date.endOfMonth,

  isOpen: state.common.drawer.isOpen,
  list: sortBy(state.common.users.list, ['first_name']),
  selectedId: state.common.users.selectedId
});

const mapDispatchToProps = {
  fetchTimesheetListByDateApi,
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
    handleUserlistItemClick: ({
      fetchTimesheetListByDateApi,
      startOfMonth,
      endOfMonth,
      userId,
      setDrawerStatus,
      selectUserInUserlist,
      selectedId
    }) => (id) => () => {
      selectUserInUserlist(id);
      setDrawerStatus();
      fetchTimesheetListByDateApi({
        id: selectedId || userId,
        start: startOfMonth,
        end: endOfMonth
      });
    }
  }),

  lifecycle<IProps, {}>({
    componentDidMount() {
      this.props.fetchUsersList();
    }
  })
)(Drawer);
