import * as React from 'react';
import {Drawer, Header} from 'modules/common/components';

interface IProps {
  children: JSX.Element[] | JSX.Element;
}

const PrivateLayout: React.FunctionComponent<IProps> = ({children}) => (
  <div>
    <Drawer />
    <Header />
    {children}
  </div>
);

export default PrivateLayout;
