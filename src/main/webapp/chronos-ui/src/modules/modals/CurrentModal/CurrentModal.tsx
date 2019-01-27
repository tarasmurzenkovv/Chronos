import * as React from 'react';

import {
  TIMESHEET_RECORD_DELETE_MODAL,
  TIMESHEET_RECORD_EDIT_MODAL,
  TIMESHEET_RECORD_MODAL
} from '../constants';
import {TimesheetRecordModal} from '..';
import TimesheetDeleteModal from '../TimesheetDeleteModal';
import TimesheetEditModal from '../TimesheetEditModal';

interface IProps {
  modal: {
    id: string;
  };
}

const modalsMap: any = {
  [TIMESHEET_RECORD_MODAL]: <TimesheetRecordModal />,
  [TIMESHEET_RECORD_DELETE_MODAL]: <TimesheetDeleteModal />,
  [TIMESHEET_RECORD_EDIT_MODAL]: <TimesheetEditModal />
  // [SETTINGS_LEAVE_PAGE_MODAL]: <SettingsLeavePageModal />
};

const CurrentModal: React.FunctionComponent<IProps> = ({modal}) => (
  <React.Fragment>{modal && modalsMap[modal.id]}</React.Fragment>
);

export default CurrentModal;
