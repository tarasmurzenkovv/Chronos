/* eslint react/prop-types: 0 */

import * as React from 'react';
import {Link} from 'react-router-dom';

export interface IProps {
  link?: string;
  addModal?: any;
}

const Component1: React.FunctionComponent<IProps> = ({link, addModal}) => (
  <div>
    Component1
    <div>
      <Link to="/">
        <span>{link}</span>
        <div>
          <button onClick={() => addModal({id: 'LOGIN_MODAL', test: 'test'})}>
            Show modal
          </button>
        </div>
      </Link>
    </div>
  </div>
);

Component1.defaultProps = {
  link: 'Root'
};

export default Component1;
