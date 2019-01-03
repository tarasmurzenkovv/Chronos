import * as React from 'react';
import {Button, withStyles, WithStyles} from '@material-ui/core';

import Logo from 'shared/assets/img/logo.svg';

import * as theme from './SignIn.scss';
import styles from './styles';

interface IProps extends WithStyles<typeof styles> {}

const SignIn: React.FunctionComponent<IProps> = ({classes}) => (
  <div className={theme.root}>
    <div className={theme.logo}>
      <img src={Logo} alt="logo" width="140" height="41" />
    </div>
    <h2 className={theme.header}>Do you already have an account?</h2>
    <p className={theme.content}>
      That’s awesome! You can login by clicking on the button below. To skip
      this next time, you can ask us to remember your login credentials.
    </p>
    <Button
      variant="outlined"
      color="primary"
      size="large"
      className={classes.button}
    >
      Sign In
    </Button>
  </div>
);

SignIn.defaultProps = {};

export default withStyles(styles)(SignIn);
