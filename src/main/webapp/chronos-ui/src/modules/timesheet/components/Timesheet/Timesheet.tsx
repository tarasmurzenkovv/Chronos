import * as React from 'react';

import Header from 'modules/header';
import {Fab, WithStyles} from '@material-ui/core';
import AddIcon from '@material-ui/icons/Add';

interface IProps extends WithStyles {
  handleButtonClick(): void;
}
const Timesheet: React.FunctionComponent<IProps> = ({handleButtonClick}) => (
  <div>
    <Header />
    <div>
      <Fab color="primary" aria-label="Add" onClick={handleButtonClick}>
        <AddIcon />
      </Fab>
    </div>
  </div>
);

export default Timesheet;
