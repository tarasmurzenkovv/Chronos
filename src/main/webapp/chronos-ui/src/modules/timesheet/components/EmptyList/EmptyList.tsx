import * as React from 'react';

import * as theme from './EmptyList.scss';

interface IProps {}

const EmptyList: React.FunctionComponent<IProps> = () => (
  <div className={theme.root}>List is empty</div>
);

export default EmptyList;
