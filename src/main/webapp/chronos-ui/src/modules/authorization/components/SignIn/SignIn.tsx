import * as React from 'react';
import {
  Button,
  Checkbox,
  FormControlLabel,
  InputAdornment,
  IconButton,
  TextField,
  withStyles,
  WithStyles
} from '@material-ui/core';

import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/VisibilityOff';

import intersection from 'lodash/intersection';

import * as theme from './SignIn.scss';
import styles from './styles';

import {
  emailInputErrorCodes,
  passwordInputErrorCodes,
  errorMessagesMap
} from '../../constants/errorMessagesMap';

interface IProps extends WithStyles<typeof styles> {
  showPassword: boolean;
  rememberMeValue: boolean;
  errorCodes: number[];

  handleShowPassword(e: React.SyntheticEvent): void;
  handleRememberMeValue(e: React.SyntheticEvent): void;
  resetSignInErrorCodes(): void;
  handleFormSubmit(): void;
}

const SignIn: React.FunctionComponent<IProps> = ({
  classes,
  showPassword,
  rememberMeValue,
  errorCodes,
  resetSignInErrorCodes,

  handleShowPassword,
  handleRememberMeValue,

  handleFormSubmit
}) => {
  const emailErrorCodes = intersection(errorCodes, emailInputErrorCodes);
  const emailErrorMessage = emailErrorCodes.length
    ? errorMessagesMap[emailErrorCodes[0]]
    : null;

  const passwordErrorCodes = intersection(errorCodes, passwordInputErrorCodes);
  const passwordErrorMessage = passwordErrorCodes.length
    ? errorMessagesMap[passwordErrorCodes[0]]
    : null;

  return (
    <div className={theme.root}>
      <h1 className={theme.header}>Welcome to Syngenta</h1>
      <h5 className={theme.subHeader}>
        Create your account by filling the form bellow.
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
          type="email"
          fullWidth
          margin="normal"
          variant="outlined"
          required
          autoFocus
          error={!!emailErrorCodes.length}
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
          helperText={emailErrorMessage}
          FormHelperTextProps={{
            shrink: true,
            classes: {
              root: classes.formHelperTextProps
            }
          }}
          onClick={resetSignInErrorCodes}
        />
        <TextField
          id="password"
          name="password"
          label="Password"
          margin="normal"
          variant="outlined"
          InputLabelProps={{
            shrink: true,
            classes: {
              root: classes.textFieldLabel,
              focused: classes.textFieldLabelFocused
            }
          }}
          InputProps={{
            classes: {
              root: classes.textFieldOutlinedInput,
              notchedOutline: classes.textFieldFocusedNotchedOutline
            },
            endAdornment: (
              <InputAdornment position="end">
                <IconButton
                  aria-label="Toggle password visibility"
                  onClick={handleShowPassword}
                >
                  {showPassword ? <Visibility /> : <VisibilityOff />}
                </IconButton>
              </InputAdornment>
            )
          }}
          color="primary"
          className={classes.textField}
          type={showPassword ? 'text' : 'password'}
          onClick={resetSignInErrorCodes}
          required
          error={!!passwordErrorCodes.length}
          helperText={passwordErrorMessage}
          FormHelperTextProps={{
            classes: {
              root: classes.formHelperTextProps
            }
          }}
        />

        <div className={theme.bottomFormContainer}>
          <FormControlLabel
            control={
              <Checkbox
                checked={rememberMeValue}
                onChange={handleRememberMeValue}
                color="primary"
              />
            }
            label="Remember me"
            className={theme.checkboxLabel}
          />

          <Button
            variant="contained"
            color="primary"
            className={theme.button}
            type="submit"
          >
            Sign In
          </Button>
        </div>
      </form>
    </div>
  );
};

SignIn.defaultProps = {};

export default withStyles(styles)(SignIn);
