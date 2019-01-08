import * as React from 'react';

import {TIMESHEET_RECORD_MODAL} from '../constants';
import {TimesheetRecordModal} from '..';

interface IProps {
  modal: {
    id: string;
  };
}

const modalsMap: any = {
  [TIMESHEET_RECORD_MODAL]: <TimesheetRecordModal />
};

const CurrentModal: React.FunctionComponent<IProps> = ({modal}) => (
  <React.Fragment>{modal && modalsMap[modal.id]}</React.Fragment>
);

export default CurrentModal;
