import * as React from 'react';
import {WithStyles} from '@material-ui/core';
import {ForgotPassword, AuthInfoSignUp} from 'modules/authorization/components';
import styles from './styles';
import * as theme from './ForgotPasswordPage.scss';

interface IProps extends WithStyles<typeof styles> {}

const ForgotPasswordPage: React.FunctionComponent<IProps> = ({}) => (
  <div className={theme.root}>
    <div className={theme.content}>
      <div className={theme.left}>
        <ForgotPassword />
      </div>
      <div className={theme.right}>
        <AuthInfoSignUp />
      </div>
    </div>
  </div>
);

export default ForgotPasswordPage;
