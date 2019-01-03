import * as React from 'react';
import {Button, withStyles, WithStyles} from '@material-ui/core';

import Logo from 'shared/assets/img/logo.svg';
import {history} from 'configurations/store';

import * as theme from './AuthInfoSignUp.scss';
import styles from './styles';

interface IProps extends WithStyles<typeof styles> {}

const AuthInfoSignUp: React.FunctionComponent<IProps> = ({classes}) => (
  <div className={theme.root}>
    <div className={theme.logo}>
      <img src={Logo} alt="logo" width="140" height="41" />
    </div>
    <div className={theme.header}>
      Don't have <br />
      an account?
    </div>
    <p className={theme.content}>
      No problem! You can sign up <br />
      by clicking on the button below.
    </p>
    <Button
      variant="outlined"
      color="primary"
      size="large"
      className={classes.button}
      onClick={() => history.push('/sign-up')}
    >
      Sign Up
    </Button>
  </div>
);

AuthInfoSignUp.defaultProps = {};

export default withStyles(styles)(AuthInfoSignUp);
