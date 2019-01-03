import * as React from 'react';
import {connect} from 'react-redux';
import {compose} from 'recompose';

import AuthInfoSignIn from './AuthInfoSignIn';

const mapStateToProps = () => ({});

const mapDispatchToProps = {};

export default compose(
  connect(
    mapStateToProps,
    mapDispatchToProps
  ),

  React.memo
)(AuthInfoSignIn);
