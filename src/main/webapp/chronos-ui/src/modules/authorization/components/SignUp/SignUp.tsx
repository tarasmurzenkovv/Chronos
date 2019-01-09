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

import * as theme from './SignUp.scss';
import styles from './styles';

import {
  emailInputErrorCodes,
  passwordInputErrorCodes,
  passwordConfirmInputErrorCodes,
  firstNameInputErrorCodes,
  lastNameInputErrorCodes,
  errorMessagesMap
} from '../../constants/errorMessagesMap';

interface IProps extends WithStyles<typeof styles> {
  showPassword: boolean;
  showConfirmPassword: boolean;
  rememberMeValue: boolean;
  errorCodes: number[];

  handleShowPassword(e: React.SyntheticEvent): void;
  handleShowConfirmPassword(e: React.SyntheticEvent): void;
  handleRememberMeValue(e: React.SyntheticEvent): void;
  resetSignUpErrorCodes(): void;
  handleFormSubmit(): void;
}

const SignUp: React.FunctionComponent<IProps> = ({
  classes,
  showPassword,
  showConfirmPassword,
  rememberMeValue,
  errorCodes,
  resetSignUpErrorCodes,

  handleShowPassword,
  handleShowConfirmPassword,
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

  const passwordConfirmErrorCodes = intersection(
    errorCodes,
    passwordConfirmInputErrorCodes
  );
  const passwordConfirmErrorMessage = passwordConfirmErrorCodes.length
    ? errorMessagesMap[passwordConfirmErrorCodes[0]]
    : null;

  const firstNameErrorCodes = intersection(
    errorCodes,
    firstNameInputErrorCodes
  );
  const firstNameErrorMessage = firstNameErrorCodes.length
    ? errorMessagesMap[firstNameErrorCodes[0]]
    : null;

  const lastNameErrorCodes = intersection(errorCodes, lastNameInputErrorCodes);
  const lastNameErrorMessage = lastNameErrorCodes.length
    ? errorMessagesMap[lastNameErrorCodes[0]]
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
        autoComplete="on"
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
          onClick={resetSignUpErrorCodes}
          required
          error={!!emailErrorCodes.length}
          helperText={emailErrorMessage}
          FormHelperTextProps={{
            classes: {
              root: classes.formHelperTextProps
            }
          }}
        />
        <TextField
          id="password"
          name="password"
          label="Password"
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
          onClick={resetSignUpErrorCodes}
          required
          error={!!passwordErrorCodes.length}
          helperText={passwordErrorMessage}
          FormHelperTextProps={{
            classes: {
              root: classes.formHelperTextProps
            }
          }}
        />
        <TextField
          id="confirmPassword"
          name="confirmPassword"
          label="Confirm password"
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
            },
            endAdornment: (
              <InputAdornment position="end">
                <IconButton
                  aria-label="Toggle password visibility"
                  onClick={handleShowConfirmPassword}
                >
                  {showConfirmPassword ? <Visibility /> : <VisibilityOff />}
                </IconButton>
              </InputAdornment>
            )
          }}
          color="primary"
          className={classes.textField}
          type={showConfirmPassword ? 'text' : 'password'}
          onClick={resetSignUpErrorCodes}
          required
          error={!!passwordConfirmErrorCodes.length}
          helperText={passwordConfirmErrorMessage}
          FormHelperTextProps={{
            classes: {
              root: classes.formHelperTextProps
            }
          }}
        />
        <TextField
          id="firstName"
          name="firstName"
          label="First Name"
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
          color="primary"
          className={classes.textField}
          onClick={resetSignUpErrorCodes}
          required
          error={!!firstNameErrorCodes.length}
          helperText={firstNameErrorMessage}
          FormHelperTextProps={{
            classes: {
              root: classes.formHelperTextProps
            }
          }}
        />
        <TextField
          id="lastName"
          name="lastName"
          label="Last Name"
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
          color="primary"
          className={classes.textField}
          onClick={resetSignUpErrorCodes}
          required
          error={!!lastNameErrorCodes.length}
          helperText={lastNameErrorMessage}
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
            Sign Up
          </Button>
        </div>
      </form>
    </div>
  );
};

SignUp.defaultProps = {};

export default withStyles(styles)(SignUp);
