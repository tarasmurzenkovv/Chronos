import * as React from 'react';
import * as moment from 'moment';
import {
  Fab,
  IconButton,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  withStyles,
  WithStyles
} from '@material-ui/core';
import DeleteOutlinedIcon from '@material-ui/icons/DeleteOutlined';
import AddIcon from '@material-ui/icons/Add';
import KeyboardArrowLeft from '@material-ui/icons/KeyboardArrowLeft';
import KeyboardArrowRight from '@material-ui/icons/KeyboardArrowRight';
import {Comment} from '@material-ui/icons';

import {defaultDateFormatApi} from 'shared/utils/constants';

import {IListItem as IListItemProject} from 'modules/modals/reducers/projects';
import Header from 'modules/common/Header';

import {IListItem as IListItemTimesheet} from '../../reducers/timesheet';
import * as theme from './TimesheetList.scss';
import styles from './styles';

interface IList extends IListItemTimesheet, IListItemProject {}

interface IProps extends WithStyles<typeof styles> {
  list: IList[];
  monthFilter: string;

  handleAddMonthFilterButtonClick(): void;
  handleMinusMonthFilterButtonClick(): void;
  handleButtonClick(): void;
  handleDeleteButtonClick(id: number): void;
}

const TimesheetList: React.FunctionComponent<IProps> = ({
  classes,
  handleButtonClick,
  handleDeleteButtonClick,
  list,
  monthFilter,
  handleAddMonthFilterButtonClick,
  handleMinusMonthFilterButtonClick
}) => (
  <React.Fragment>
    <Header />
    <div className={theme.root}>
      <div className={theme.filterByMonth}>
        <IconButton onClick={handleMinusMonthFilterButtonClick}>
          <KeyboardArrowLeft />
        </IconButton>
        <span>{moment(monthFilter).format('MMM YYYY')}</span>
        <IconButton onClick={handleAddMonthFilterButtonClick}>
          <KeyboardArrowRight />
        </IconButton>
      </div>
      {list.length ? (
        <Paper className={classes.content}>
          <Table className={classes.table}>
            <TableHead>
              <TableRow>
                <TableCell align="center" className={classes.tableHeadCell}>
                  Date
                </TableCell>
                <TableCell align="center" className={classes.tableHeadCell}>
                  Hours
                </TableCell>
                <TableCell align="center" className={classes.tableHeadCell}>
                  Name
                </TableCell>
                <TableCell align="center" className={classes.tableHeadCell}>
                  <div className={classes.tableHeadCommentCell}>
                    <Comment className={classes.commentCellIcon} />
                    <span>Comments</span>
                  </div>
                </TableCell>
                <TableCell align="center" className={classes.tableHeadCell}>
                  Action
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {list.map((item) => (
                <TableRow className={classes.row} key={item.task_id}>
                  <TableCell align="center" className={classes.dateCell}>
                    <div className={classes.dateCellDay}>
                      {moment(item.reporting_date, defaultDateFormatApi).format(
                        'ddd'
                      )}
                    </div>
                    <span className={classes.dateCellValue}>
                      {moment(item.reporting_date, defaultDateFormatApi).format(
                        'DD.MM.YYYY'
                      )}
                    </span>
                  </TableCell>
                  <TableCell align="center" className={classes.timeCell}>
                    {item.spent_time}
                  </TableCell>
                  <TableCell align="left" className={classes.nameCell}>
                    {item.project_name}
                  </TableCell>
                  <TableCell align="left" className={classes.commentCell}>
                    {item.comments}
                  </TableCell>
                  <TableCell align="center">
                    <IconButton
                      aria-label="Delete"
                      onClick={() => handleDeleteButtonClick(item.task_id)}
                      className={classes.deleteBtn}
                    >
                      <DeleteOutlinedIcon className={classes.deleteIcon} />
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Paper>
      ) : (
        <div className={theme.emptyList}>List is empty</div>
      )}
      <Fab
        color="primary"
        aria-label="Add"
        className={classes.button}
        onClick={handleButtonClick}
      >
        <AddIcon />
      </Fab>
    </div>
  </React.Fragment>
);

export default withStyles(styles)(TimesheetList);
