import * as React from 'react';

import {
  Button,
  Checkbox,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  withStyles
} from '@material-ui/core';
import SaveAlt from '@material-ui/icons/SaveAlt';
import MomentUtils from '@date-io/moment';
import {InlineDatePicker, MuiPickersUtilsProvider} from 'material-ui-pickers';
import classnames from 'classnames';

import * as theme from './Reports.scss';
import styles from './styles';

const Report = ({
  classes,
  startDay,
  endDay,
  list,
  selectedUsersIds,

  handleStartDayChange,
  handleEndDayChange,
  handleExportButtonClick,
  handleUserlistItemClick
}) => (
  <div className={theme.root}>
    <div>
      <Paper classes={{root: classes.paper}}>
        <Table>
          <colgroup>
            <col style={{width: '15%'}} />
            <col style={{width: '85%'}} />
          </colgroup>

          <TableHead>
            <TableRow>
              <TableCell
                align="center"
                classes={{
                  root: classnames(
                    classes.tableHeadCell,
                    classes.tableHeadCellFirst
                  )
                }}
              >
                Select
              </TableCell>
              <TableCell align="center" classes={{root: classes.tableHeadCell}}>
                Employee
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {list.map((item) => (
              <TableRow
                key={item.id}
                className={theme.tableRow}
                onClick={handleUserlistItemClick(item.id)}
              >
                <TableCell
                  align="center"
                  classes={{root: classes.checkboxCell}}
                >
                  <Checkbox
                    color="primary"
                    checked={selectedUsersIds.includes(item.id)}
                  />
                </TableCell>
                <TableCell align="left" classes={{root: classes.fullNameCell}}>
                  {item.full_name}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </Paper>
      <form className={classes.form} onSubmit={handleExportButtonClick}>
        <div>
          <MuiPickersUtilsProvider utils={MomentUtils}>
            <InlineDatePicker
              variant="outlined"
              label="Start Date"
              format="DD.MM.YYYY"
              className={classes.startDay}
              value={startDay}
              onChange={handleStartDayChange}
            />

            <InlineDatePicker
              variant="outlined"
              label="End Date"
              format="DD.MM.YYYY"
              value={endDay}
              onChange={handleEndDayChange}
            />
          </MuiPickersUtilsProvider>
        </div>

        <Button
          type="submit"
          color="primary"
          classes={{root: classes.button}}
          disabled={!selectedUsersIds.length}
        >
          Export Reports
          <SaveAlt className={classes.downloadIcon} />
        </Button>
      </form>
    </div>
  </div>
);

export default withStyles(styles)(Report);
