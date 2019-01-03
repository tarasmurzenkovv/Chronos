import * as React from 'react';
import {connect} from 'react-redux';
import {compose, withState, withHandlers} from 'recompose';

import {signUp, resetSignUpErrorCodes} from '../../actions/signUp';

import SignUp from './SignUp';

const mapStateToProps = (state) => ({
  errorCodes: state.auth.signUp.errorCodes
});

const mapDispatchToProps = {
  signUp,
  resetSignUpErrorCodes
};

export default compose(
  connect(
    mapStateToProps,
    mapDispatchToProps
  ),

  withState('rememberMeValue', 'setRememberMeValue', false),
  withState('showPassword', 'setShowPassword', false),
  withState('showConfirmPassword', 'setShowConfirmPassword', false),

  withHandlers({
    handleRememberMeValue: ({rememberMeValue, setRememberMeValue}) => (
      event,
      checked
    ) => setRememberMeValue(checked),

    handleShowPassword: ({showPassword, setShowPassword}) => () =>
      setShowPassword(!showPassword),

    handleShowConfirmPassword: ({
      showConfirmPassword,
      setShowConfirmPassword
    }) => () => setShowConfirmPassword(!showConfirmPassword),

    handleFormSubmit: ({signUp}) => (event) => {
      event.preventDefault();

      const email = event.target.email.value;
      const password = event.target.password.value;
      const confirmPassword = event.target.confirmPassword.value;
      const firstName = event.target.firstName.value;
      const lastName = event.target.lastName.value;

      signUp({
        email,
        password,
        confirmPassword,
        firstName,
        lastName
      });
    }
  }),
  React.memo
)(SignUp);
