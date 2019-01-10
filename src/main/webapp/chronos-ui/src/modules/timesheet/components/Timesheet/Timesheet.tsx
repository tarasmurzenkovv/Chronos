import * as React from 'react';

import {
  Fab,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  withStyles,
  WithStyles
} from '@material-ui/core';
import AddIcon from '@material-ui/icons/Add';
import {IListItem as IListItemProject} from 'modules/modals/reducers/projects';
import Header from 'modules/header';
import styles from './styles';
import {IListItem as IListItemTimesheet} from '../../reducers/timesheet';

interface IList extends IListItemTimesheet, IListItemProject {}

interface IProps extends WithStyles<typeof styles> {
  list: IList[];

  handleButtonClick(): void;
}

const Timesheet: React.FunctionComponent<IProps> = ({
  classes,
  handleButtonClick,
  list
}) => (
  <div>
    <Header />
    <div>
      <Paper className={classes.root}>
        <Table className={classes.table}>
          <TableHead>
            <TableRow>
              <TableCell align="center">Date</TableCell>
              <TableCell align="center">hours</TableCell>
              <TableCell align="center">Name</TableCell>
              <TableCell align="center"> Comments</TableCell>
              <TableCell align="center">Action</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {list.map((item) => (
              <TableRow className={classes.row} key={item.task_id}>
                <TableCell align="center">{item.reporting_date}</TableCell>
                <TableCell align="center">{item.spent_time}</TableCell>
                <TableCell align="center">{item.project_name}</TableCell>
                <TableCell align="center">{item.comments}</TableCell>
                <TableCell align="center">
                  <button type="button">edit</button>
                  <button type="button"> delete</button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </Paper>
    </div>
    <div>
      <Fab color="primary" aria-label="Add" onClick={handleButtonClick}>
        <AddIcon />
      </Fab>
    </div>
  </div>
);

export default withStyles(styles)(Timesheet);
