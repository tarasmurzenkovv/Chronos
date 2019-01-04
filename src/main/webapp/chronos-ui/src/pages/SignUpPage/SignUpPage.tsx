import * as React from 'react';
import {WithStyles} from '@material-ui/core';
import {SignUp, AuthInfoSignIn} from 'modules/authorization/components';
import styles from './styles';
import * as theme from './SignUpPage.scss';

interface IProps extends WithStyles<typeof styles> {}

const SignUpPage: React.FunctionComponent<IProps> = ({}) => (
  <div className={theme.root}>
    <div className={theme.content}>
      <div className={theme.left}>
        <SignUp />
      </div>
      <div className={theme.right}>
        <AuthInfoSignIn />
      </div>
    </div>
  </div>
);

export default SignUpPage;
