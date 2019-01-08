import * as React from 'react';
import {connect} from 'react-redux';
import {compose, withHandlers} from 'recompose';

import {addModal} from 'modules/modals/actions/modalsActions';
import {TIMESHEET_RECORD_MODAL} from 'modules/modals/constants';
import Timesheet from './Timesheet';

const mapStateToProps = () => {};

const mapDispatchToProps = {addModal};

export default compose(
  connect(
    mapStateToProps,
    mapDispatchToProps
  ),

  withHandlers({
    handleButtonClick: ({addModal}) => () =>
      addModal({id: TIMESHEET_RECORD_MODAL})
  }),

  React.memo
)(Timesheet);
