import * as React from 'react';
import {connect} from 'react-redux';
import {compose} from 'recompose';

import {addModal} from 'modules/modals/actions/modals';

import Component1 from './Component1';

const mapStateToProps = () => ({});

const mapDispatchToProps = {addModal};

export default compose(
  connect(
    mapStateToProps,
    mapDispatchToProps
  ),
  React.memo
)(Component1);
