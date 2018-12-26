import * as React from 'react';
import {bindActionCreators, Dispatch} from 'redux';
import {connect} from 'react-redux';
import {compose} from 'recompose';

import Component1 from './Component1';

const mapStateToProps = () => ({});

const mapDispatchToProps = (dispatch: Dispatch<any>) =>
  bindActionCreators({}, dispatch);

export default compose(
  connect(
    mapStateToProps,
    mapDispatchToProps
  ),
  React.memo
)(Component1);
