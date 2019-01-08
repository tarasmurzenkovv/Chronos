import * as React from 'react';
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControl,
  InputLabel,
  MenuItem,
  OutlinedInput,
  Select,
  withStyles,
  WithStyles
} from '@material-ui/core';

import styles from './styles';

interface IProps extends WithStyles<typeof styles> {
  isOpen: boolean;
  handleOnSave(): void;
  handleOnClose(): void;
}

const TimesheetRecordModal: React.FunctionComponent<IProps> = ({
  classes,
  isOpen,
  handleOnClose,
  handleOnSave
}) => (
  <div>
    <Dialog open={isOpen} onClose={handleOnClose}>
      <DialogTitle>Add new time Report</DialogTitle>
      <DialogContent>
        <form className={classes.container}>
          <FormControl className={classes.formControl}>
            <InputLabel htmlFor="type">Type</InputLabel>
            <Select
              value="Project"
              onChange={() => console.log('handleChange2')}
              input={
                <OutlinedInput labelWidth={0} name="type" id="outlined-type" />
              }
            >
              <MenuItem value="Project">Project</MenuItem>
              <MenuItem value="Internal">Internal</MenuItem>
            </Select>
          </FormControl>
        </form>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleOnSave} color="primary">
          Save Report
        </Button>
      </DialogActions>
    </Dialog>
  </div>
);

const LoginModalWrapped = withStyles(styles)(TimesheetRecordModal);

export default LoginModalWrapped;
