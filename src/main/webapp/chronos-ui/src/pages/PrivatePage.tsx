import * as React from 'react';
import {Redirect, Route} from 'react-router-dom';

const isAuth = true;

const PrivatePage: React.FunctionComponent<any> = ({
  component: Component,
  ...rest
}) => (
  <Route
    {...rest}
    render={(props: Object) =>
      isAuth ? <Component {...props} /> : <Redirect to="/" />
    }
  />
);

export default PrivatePage;
