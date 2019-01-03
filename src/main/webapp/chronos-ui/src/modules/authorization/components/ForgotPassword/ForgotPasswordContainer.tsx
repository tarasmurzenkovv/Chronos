import * as React from 'react';
import {connect} from 'react-redux';
import {compose, withHandlers} from 'recompose';

import {
  forgotPassword,
  resetForgotPasswordErrorCodes
} from '../../actions/forgotPassword';

import ForgotPassword from './ForgotPassword';

const mapStateToProps = (state) => ({
  errorCodes: state.auth.forgotPassword.errorCodes
});

const mapDispatchToProps = {
  forgotPassword,
  resetForgotPasswordErrorCodes
};

export default compose(
  connect(
    mapStateToProps,
    mapDispatchToProps
  ),

  withHandlers({
    handleFormSubmit: ({forgotPassword}) => (event) => {
      event.preventDefault();

      const email = event.target.email.value;

      forgotPassword({email});
    }
  }),
  React.memo
)(ForgotPassword);
