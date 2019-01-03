import React, {lazy, Suspense} from 'react';
import {Switch, Route} from 'react-router-dom';
import {ConnectedRouter} from 'connected-react-router';

import SignUpPage from './SignUpPage';
import SignInPage from './SignInPage';
import ForgotPasswordPage from './ForgotPasswordPage';

const App = lazy(() => import('../App'));

const Pages = ({history}) => (
  <ConnectedRouter history={history}>
    <div>
      <Suspense fallback={<div>Loading...</div>}>
        <Switch>
          <Route exact path="/" component={(props) => <App {...props} />} />
          <Route path="/sign-up" component={SignUpPage} />
          <Route path="/sign-in" component={SignInPage} />
          <Route path="/forgot-password" component={ForgotPasswordPage} />
        </Switch>
      </Suspense>
    </div>
  </ConnectedRouter>
);

export default Pages;
