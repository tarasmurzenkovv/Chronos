import React, {lazy, Suspense} from 'react';
// import PropTypes from 'prop-types';
import {Switch, Route} from 'react-router-dom';
import {ConnectedRouter} from 'connected-react-router';

import PrivatePage from './PrivatePage';
import {Component1} from '../modules/module1/components';

// const propTypes = {};

const App = lazy(() => import('../App'));

const Pages = ({history}) => (
  <ConnectedRouter history={history}>
    <div>
      <Suspense fallback={<div>Loading...</div>}>
        <Switch>
          <Route exact path="/" component={(props) => <App {...props} />} />
          <PrivatePage path="/component1" component={Component1} />
        </Switch>
      </Suspense>
    </div>
  </ConnectedRouter>
);

// Pages.propTypes = propTypes;

export default Pages;
