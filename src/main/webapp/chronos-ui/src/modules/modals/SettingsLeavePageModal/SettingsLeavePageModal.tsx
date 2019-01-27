import * as React from 'react';
import {
  Dialog,
  DialogContent,
  DialogContentText,
  DialogActions,
  Button
} from '@material-ui/core';

interface IProps {
  isOpen: boolean;

  handleOnClose(): void;
  handleDeleteItem(): void;
}

const SettingsLeavePageModal: React.FunctionComponent<IProps> = ({
  isOpen,
  handleOnClose,
  handleDeleteItem
}) => (
  <div>
    <Dialog open={isOpen} onClose={handleOnClose}>
      <DialogContent>
        <DialogContentText id="alert-dialog-description">
          Are you sure you want to leave thias page? All unsaved changes will be
          discarded
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button color="primary" onClick={handleOnClose}>
          Cancel
        </Button>
        <Button color="primary" onClick={handleDeleteItem} autoFocus>
          Ok
        </Button>
      </DialogActions>
    </Dialog>
  </div>
);

export default SettingsLeavePageModal;
