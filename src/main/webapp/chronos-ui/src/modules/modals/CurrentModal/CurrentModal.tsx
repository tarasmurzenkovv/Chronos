import * as React from 'react';

import {
  TIMESHEET_RECORD_MODAL,
  TIMESHEET_RECORD_DELETE_MODAL
} from '../constants';
import {TimesheetRecordModal} from '..';
import TimesheetDeleteModal from '../TimesheetDeleteModal';

interface IProps {
  modal: {
    id: string;
  };
}

const modalsMap: any = {
  [TIMESHEET_RECORD_MODAL]: <TimesheetRecordModal />,
  [TIMESHEET_RECORD_DELETE_MODAL]: <TimesheetDeleteModal />
};

const CurrentModal: React.FunctionComponent<IProps> = ({modal}) => (
  <React.Fragment>{modal && modalsMap[modal.id]}</React.Fragment>
);

export default CurrentModal;
