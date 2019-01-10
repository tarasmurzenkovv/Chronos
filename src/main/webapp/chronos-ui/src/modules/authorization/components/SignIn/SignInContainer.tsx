import * as React from 'react';
import {connect} from 'react-redux';
import {compose, withHandlers, withState} from 'recompose';

import {resetSignInErrorCodes, signIn} from '../../actions/signIn';

import SignIn from './SignIn';

const mapStateToProps = (state) => ({
  errorCodes: state.auth.signIn.errorCodes
});

const mapDispatchToProps = {
  signIn,
  resetSignInErrorCodes
};

export default compose(
  connect(
    mapStateToProps,
    mapDispatchToProps
  ),

  withState('rememberMeValue', 'setRememberMeValue', false),
  withState('showPassword', 'setShowPassword', false),

  withHandlers({
    handleRememberMeValue: ({setRememberMeValue}) => (event, checked) =>
      setRememberMeValue(checked),

    handleShowPassword: ({showPassword, setShowPassword}) => () =>
      setShowPassword(!showPassword),

    handleFormSubmit: ({signIn}) => (event) => {
      event.preventDefault();

      const email = event.target.email.value;
      const password = event.target.password.value;

      signIn({
        email,
        password
      });
    }
  }),
  React.memo
)(SignIn);
