import * as React from 'react';
import {
  Button,
  Dialog,
  DialogContent,
  DialogTitle,
  FormControl,
  InputLabel,
  MenuItem,
  OutlinedInput,
  Select,
  TextField,
  withStyles,
  WithStyles
} from '@material-ui/core';

import {IListItem} from '../reducers/projects';

import styles from './styles';

interface IProps extends WithStyles<typeof styles> {
  isOpen: boolean;
  list: IListItem[];
  projectId: string;
  date: string;
  comments: string;

  handleProjectChange(): void;
  handleDateChange(): void;
  handleCommentsChange(): void;

  handleFormSubmit(): void;
  handleOnClose(): void;
}

const TimesheetRecordModal: React.FunctionComponent<IProps> = ({
  classes,

  isOpen,
  list,
  projectId,
  date,

  handleProjectChange,
  handleDateChange,
  handleCommentsChange,
  handleFormSubmit,
  handleOnClose
}) => (
  <div>
    <Dialog open={isOpen} onClose={handleOnClose}>
      <DialogTitle>Add new time Report</DialogTitle>
      <form className={classes.container} onSubmit={handleFormSubmit}>
        <DialogContent>
          <div>
            <FormControl variant="outlined" className={classes.formControl}>
              <InputLabel htmlFor="outlined-projectId">Project</InputLabel>
              <Select
                value={projectId}
                onChange={handleProjectChange}
                input={
                  <OutlinedInput
                    required
                    labelWidth={0}
                    name="projectId"
                    id="outlined-projectId"
                  />
                }
              >
                {list.map((item) => (
                  <MenuItem key={item.id} value={item.id}>
                    {item.project_name}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </div>

          <div>
            <TextField
              id="time"
              name="time"
              label="Spent time, h."
              margin="normal"
              variant="outlined"
              type="number"
              InputLabelProps={{
                classes: {
                  root: classes.textFieldLabel,
                  focused: classes.textFieldLabelFocused
                }
              }}
              InputProps={{
                classes: {
                  root: classes.textFieldOutlinedInput,
                  notchedOutline: classes.textFieldFocusedNotchedOutline
                }
              }}
              className={classes.textField}
              required
            />

            <TextField
              id="date"
              name="date"
              label="Date"
              margin="normal"
              variant="outlined"
              type="date"
              defaultValue={date}
              InputLabelProps={{
                shrink: true,
                classes: {
                  root: classes.textFieldLabel,
                  focused: classes.textFieldLabelFocused
                }
              }}
              InputProps={{
                classes: {
                  root: classes.textFieldOutlinedInput,
                  notchedOutline: classes.textFieldFocusedNotchedOutline
                }
              }}
              className={classes.textField}
              required
              onChange={handleDateChange}
            />
          </div>

          <div>Comments</div>
          <div>
            <TextField
              id="comments"
              name="comments"
              label="Comments"
              margin="normal"
              variant="outlined"
              type="text"
              InputLabelProps={{
                classes: {
                  root: classes.textFieldLabel,
                  focused: classes.textFieldLabelFocused
                }
              }}
              InputProps={{
                classes: {
                  root: classes.textFieldOutlinedInput,
                  notchedOutline: classes.textFieldFocusedNotchedOutline
                }
              }}
              className={classes.textField}
              multiline
              rows={2}
              rowsMax={4}
              onChange={handleCommentsChange}
            />
          </div>
          <div>
            <Button type="submit" color="primary">
              Save Report
            </Button>
          </div>
        </DialogContent>
      </form>
    </Dialog>
  </div>
);

const LoginModalWrapped = withStyles(styles)(TimesheetRecordModal);

export default LoginModalWrapped;
