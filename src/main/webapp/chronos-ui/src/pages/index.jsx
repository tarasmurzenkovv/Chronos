import React, {lazy, Suspense} from 'react';
import {Switch, Route} from 'react-router-dom';
import {ConnectedRouter} from 'connected-react-router';

import {CurrentModal} from 'modules/modals';

import PrivatePage from './PrivatePage';
import {Component1} from '../modules/module1/components';

const App = lazy(() => import('../App'));

const Pages = ({history}) => (
  <ConnectedRouter history={history}>
    <div>
      <CurrentModal />
      <Suspense fallback={<div>Loading...</div>}>
        <Switch>
          <Route exact path="/" component={(props) => <App {...props} />} />
          <PrivatePage path="/component1" component={Component1} />
        </Switch>
      </Suspense>
    </div>
  </ConnectedRouter>
);

export default Pages;
