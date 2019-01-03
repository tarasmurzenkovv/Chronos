import * as React from 'react';
import {Button, TextField, withStyles, WithStyles} from '@material-ui/core';

import intersection from 'lodash/intersection';

import * as theme from './ForgotPassword.scss';
import styles from './styles';

import {
  emailInputErrorCodes,
  errorMessagesMap
} from '../../constants/localErrorCodes';

interface IProps extends WithStyles<typeof styles> {
  errorCodes: number[];

  resetForgotPasswordErrorCodes(): void;
  handleFormSubmit(): void;
}

const ForgotPassword: React.FunctionComponent<IProps> = ({
  classes,
  errorCodes,
  resetForgotPasswordErrorCodes,

  handleFormSubmit
}) => {
  const emailErrorCodes = intersection(errorCodes, emailInputErrorCodes);
  const emailErrorMessage = emailErrorCodes.length
    ? errorMessagesMap[emailErrorCodes[0]]
    : null;

  return (
    <div className={theme.root}>
      <h1 className={theme.header}>Forgot Your password?</h1>
      <h5 className={theme.subHeader}>
        Don’t worry. Reseting your password is easy, just tell us the email
        address you registered.
      </h5>
      <form
        className={classes.container}
        noValidate
        autoComplete="off"
        onSubmit={handleFormSubmit}
      >
        <TextField
          id="email"
          name="email"
          label="Email"
          fullWidth
          margin="normal"
          variant="outlined"
          InputLabelProps={{
            classes: {
              root: classes.textFieldLabel,
              focused: classes.textFieldLabelFocused
            }
          }}
          InputProps={{
            classes: {
              root: classes.textFieldOutlinedInput,
              notchedOutline: classes.textFieldFocusedNotchedOutline
            }
          }}
          className={classes.textField}
          type="email"
          onClick={resetForgotPasswordErrorCodes}
          required
          error={!!emailErrorCodes.length}
          helperText={emailErrorMessage}
          FormHelperTextProps={{
            classes: {
              root: classes.formHelperTextProps
            }
          }}
          autoFocus
        />

        <div className={theme.bottomFormContainer}>
          <Button
            variant="contained"
            color="primary"
            className={theme.button}
            type="submit"
          >
            Send
          </Button>
        </div>
      </form>
    </div>
  );
};

ForgotPassword.defaultProps = {};

export default withStyles(styles)(ForgotPassword);
