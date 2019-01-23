import * as React from 'react';
import {
  Button,
  Dialog,
  DialogContent,
  DialogTitle,
  FormControl,
  FormHelperText,
  IconButton,
  InputLabel,
  MenuItem,
  OutlinedInput,
  Select,
  TextField,
  withStyles
} from '@material-ui/core';
import CloseIcon from '@material-ui/icons/Close';
import {InlineDatePicker, MuiPickersUtilsProvider} from 'material-ui-pickers';
import MomentUtils from '@date-io/moment';
import classnames from 'classnames';

import * as theme from './TimesheetRecordModal.scss';
import styles from './styles';

const TimesheetRecordModal = ({
  classes,
  date,
  list,
  projectId,
  isOpen,
  selectProjectError,
  timeError,

  handleProjectChange,
  handleDateChange,
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
          <FormControl
            variant="outlined"
            required
            className={classes.formControl}
            error={selectProjectError}
          >
            <InputLabel
              htmlFor="outlined-projectId"
              className={classes.labelText}
            >
              Project
            </InputLabel>
            <Select
              value={projectId}
              onChange={handleProjectChange}
              input={
                <OutlinedInput
                  labelWidth={70}
                  name="projectId"
                  id="outlined-projectId"
                  classes={{
                    root: classnames(classes.selectOutlinedInput, {
                      [classes.selectOutlinedInputError]: selectProjectError
                    }),
                    notchedOutline: classes.textFieldFocusedNotchedOutline
                  }}
                />
              }
            >
              {list.map((item) => (
                <MenuItem key={item.id} value={item.id}>
                  {item.project_name}
                </MenuItem>
              ))}
            </Select>

            {selectProjectError && (
              <FormHelperText className={classes.errorText}>
                Please, select your project
              </FormHelperText>
            )}
          </FormControl>

          <div className={theme.timeAndDateBlock}>
            <TextField
              id="time"
              name="time"
              label="Spent time, h."
              margin="normal"
              variant="outlined"
              type="number"
              className={classes.textFieldTime}
              InputLabelProps={{
                classes: {
                  root: classes.textFieldLabel,
                  focused: classes.textFieldLabelFocused
                },
                required: true
              }}
              InputProps={{
                classes: {
                  root: classnames(classes.textFieldOutlinedInput, {
                    [classes.textFieldOutlinedInputError]: timeError
                  }),
                  notchedOutline: classes.textFieldFocusedNotchedOutline
                }
              }}
              error={timeError}
              onChange={handleTimeChange}
            />
            {timeError && (
              <FormHelperText className={classes.errorTime}>
                Please, add correct time value
              </FormHelperText>
            )}
            <MuiPickersUtilsProvider utils={MomentUtils}>
              <InlineDatePicker
                variant="outlined"
                label="Date"
                format="DD.MM.YYYY"
                value={date}
                onChange={handleDateChange}
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
                required
              />
            </MuiPickersUtilsProvider>
          </div>

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
