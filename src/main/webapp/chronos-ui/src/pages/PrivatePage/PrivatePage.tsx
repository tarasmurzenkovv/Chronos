import * as React from 'react';
import {Redirect, Route} from 'react-router-dom';

const PrivatePage: React.FunctionComponent<any> = ({
  component: Component,
  id,
  ...rest
}) => (
  <Route
    {...rest}
    render={(props: Object) =>
      id ? <Component {...props} /> : <Redirect to="/sign-in" />
    }
  />
);

export default PrivatePage;
