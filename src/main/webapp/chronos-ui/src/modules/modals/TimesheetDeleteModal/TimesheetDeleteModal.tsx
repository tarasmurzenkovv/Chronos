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

const TimesheetDeleteModal: React.FunctionComponent<IProps> = ({
  isOpen,
  handleOnClose,
  handleDeleteItem
}) => (
  <div>
    <Dialog open={isOpen} onClose={handleOnClose}>
      <DialogContent>
        <DialogContentText id="alert-dialog-description">
          Are you sure you want to delete this time report?
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button color="primary" onClick={handleOnClose}>
          Cancel
        </Button>
        <Button color="primary" onClick={handleDeleteItem} autoFocus>
          Delete
        </Button>
      </DialogActions>
    </Dialog>
  </div>
);

export default TimesheetDeleteModal;
