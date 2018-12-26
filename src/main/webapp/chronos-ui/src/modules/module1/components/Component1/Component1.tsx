/* eslint react/prop-types: 0 */

import * as React from 'react';
import {Link} from 'react-router-dom';

export interface IProps {
  link?: string;
}

const Component1: React.FunctionComponent<IProps> = ({link}) => (
  <div>
    Component1
    <div>
      <Link to="/">
        <span>{link}</span>
      </Link>
    </div>
  </div>
);

Component1.defaultProps = {
  link: 'Root'
};

export default Component1;
