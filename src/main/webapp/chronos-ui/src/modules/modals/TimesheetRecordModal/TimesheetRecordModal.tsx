import * as React from 'react';
import {
  Button,
  Dialog,
  DialogContent,
  DialogTitle,
  FormControl,
  InputLabel,
  IconButton,
  MenuItem,
  OutlinedInput,
  Select,
  TextField,
  FormHelperText,
  withStyles,
  WithStyles
} from '@material-ui/core';

import CloseIcon from '@material-ui/icons/Close';
import {IListItem} from '../reducers/projects';
import * as theme from './TimesheetRecordModal.scss';
import styles from './styles';

interface IProps extends WithStyles<typeof styles> {
  isOpen: boolean;
  list: IListItem[];
  projectId: string;
  date: string;
  comments: string;
  isSelected: boolean;
  hasError: boolean;

  handleProjectChange(): void;
  handleDateChange(): void;
  handleCommentsChange(): void;

  handleFormSubmit(): void;
  handleTimeChange(): void;
  handleOnClose(): void;
}

const TimesheetRecordModal: React.FunctionComponent<IProps> = ({
  classes,

  isOpen,
  list,
  projectId,
  date,
  // isSelected,
  hasError,

  handleProjectChange,
  handleDateChange,
  handleCommentsChange,
  handleFormSubmit,
  handleOnClose,
  handleTimeChange
}) => (
  <div>
    <Dialog
      open={isOpen}
      onClose={handleOnClose}
      PaperProps={{classes: {root: classes.root}}}
    >
      <DialogTitle className={classes.modalTitle}>
        Add new time Report
      </DialogTitle>
      <IconButton
        color="inherit"
        onClick={handleOnClose}
        aria-label="Close"
        className={classes.closeBtn}
      >
        <CloseIcon />
      </IconButton>
      <form className={classes.container} onSubmit={handleFormSubmit}>
        <DialogContent className={classes.modalContent}>
          <div>
            <FormControl variant="outlined" className={classes.formControl}>
              <InputLabel
                htmlFor="outlined-projectId"
                className={classes.labelText}
              >
                Project
              </InputLabel>
              <Select
                value={projectId}
                onChange={handleProjectChange}
                className={classes.labelProjectSelect}
                input={
                  <OutlinedInput
                    labelWidth={60}
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
            {hasError && (
              <FormHelperText className={classes.errorText}>
                Please, select your project
              </FormHelperText>
            )}
          </div>

          <div className={theme.timeInputBlock}>
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
              onChange={handleTimeChange}
              className={classes.textField}
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
          {hasError && (
            <FormHelperText className={classes.errorText}>
              Please, fill out your spent time
            </FormHelperText>
          )}

          <div className={theme.labelText}>Comments:</div>
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
                  root: classes.commentsInput,
                  notchedOutline: classes.textFieldFocusedNotchedOutline
                }
              }}
              className={classes.textField}
              multiline
              rows={3}
              rowsMax={6}
              onChange={handleCommentsChange}
              fullWidth
            />
          </div>
          <div>
            <Button type="submit" color="primary" className={classes.saveBtn}>
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
