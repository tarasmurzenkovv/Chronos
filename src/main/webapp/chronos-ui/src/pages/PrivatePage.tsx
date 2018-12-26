import * as React from 'react';
import {Route, Redirect} from 'react-router-dom';

interface IProps {
  component: any;
  rest: Object;
}

const isAuth = true;

const PrivatePage: React.FunctionComponent<IProps> = ({
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
