import * as React from 'react';
import {WithStyles} from '@material-ui/core';
import {ForgotPassword, AuthInfoSignUp} from 'modules/authorization/components';
import styles from './styles';
import * as theme from './ForgotPasswordPage.scss';

interface IProps extends WithStyles<typeof styles> {}

const ForgotPasswordPage: React.FunctionComponent<IProps> = ({}) => (
  <div className={theme.root}>
    <div className={theme.content}>
      <div className={theme.signIn}>
        <ForgotPassword />
      </div>
      <div className={theme.authInfo}>
        <AuthInfoSignUp />
      </div>
    </div>
  </div>
);

export default ForgotPasswordPage;
