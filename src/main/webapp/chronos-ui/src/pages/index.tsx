import * as React from 'react';
import {Route, Switch} from 'react-router-dom';
import {ConnectedRouter} from 'connected-react-router';

import {CurrentModal} from 'modules/modals';

import SignUpPage from './SignUpPage';
import SignInPage from './SignInPage';
import ForgotPasswordPage from './ForgotPasswordPage';
import TimesheetPage from './TimesheetPage';
import PrivatePage from './PrivatePage';

const Pages = ({history}) => (
  <ConnectedRouter history={history}>
    <div>
      <Switch>
        <Route path="/sign-up" component={SignUpPage} />
        <Route path="/sign-in" component={SignInPage} />
        <Route path="/forgot-password" component={ForgotPasswordPage} />

        <PrivatePage exact path="/" component={() => <TimesheetPage />} />
      </Switch>
      <CurrentModal />
    </div>
  </ConnectedRouter>
);

export default Pages;
