import * as React from 'react';
import {connect} from 'react-redux';
import {compose} from 'recompose';

import {createUser} from '../../actions/createUser';

import SignIn from './SignIn';

const mapStateToProps = () => ({});

const mapDispatchToProps = {createUser};

export default compose(
  connect(
    mapStateToProps,
    mapDispatchToProps
  ),

  React.memo
)(SignIn);
