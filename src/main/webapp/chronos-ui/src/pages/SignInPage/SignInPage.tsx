import * as React from 'react';
import {WithStyles} from '@material-ui/core';
import {SignIn, AuthInfoSignUp} from 'modules/authorization/components';
import styles from './styles';
import * as theme from './SignInPage.scss';

interface IProps extends WithStyles<typeof styles> {}

const SignInPage: React.FunctionComponent<IProps> = ({}) => (
  <div className={theme.root}>
    <div className={theme.content}>
      <div className={theme.left}>
        <SignIn />
      </div>
      <div className={theme.right}>
        <AuthInfoSignUp />
      </div>
    </div>
  </div>
);

export default SignInPage;
