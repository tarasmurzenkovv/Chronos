import * as React from 'react';
import {Redirect, Route} from 'react-router-dom';

import {PrivateLayout} from 'modules/common/layouts';

const PrivatePage: React.FunctionComponent<any> = ({
  component: Component,
  id,
  ...rest
}) => (
  <Route
    {...rest}
    render={(props: Object) =>
      id ? (
        <PrivateLayout>
          <Component {...props} />
        </PrivateLayout>
      ) : (
        <Redirect to="/sign-in" />
      )
    }
  />
);

export default PrivatePage;
