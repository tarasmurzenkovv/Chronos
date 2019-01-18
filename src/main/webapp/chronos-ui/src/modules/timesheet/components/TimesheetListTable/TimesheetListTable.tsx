import * as React from 'react';
import * as moment from 'moment';
import classnames from 'classnames';

import {
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
import {Comment} from '@material-ui/icons';
import EditFilledIcon from '@material-ui/icons/Edit';

import {defaultDateFormatApi} from 'shared/utils/constants';

import {IListItem as IListItemProject} from 'modules/modals/reducers/projects';

import {IListItem as IListItemTimesheet} from '../../reducers/timesheet';
import styles from './styles';

interface IList extends IListItemTimesheet, IListItemProject {}

interface IProps extends WithStyles<typeof styles> {
  list: IList[];
  hoveredRow: number;

  handleDeleteButtonClick(id: number): void;
  handleRowEnter: (id: number) => (id: React.SyntheticEvent) => void;
  handleRowClick: (id: number) => (id: React.SyntheticEvent) => void;
  handleRowLeave(item: any): void;
  handleEditButtonClick(item: any): void;
}

const TimesheetListTable: React.FunctionComponent<IProps> = ({
  classes,
  handleDeleteButtonClick,
  list,
  handleRowClick,
  handleRowEnter,
  handleRowLeave,
  hoveredRow,
  handleEditButtonClick
}) => (
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
          <TableRow
            className={classes.row}
            key={item.task_id}
            onMouseEnter={handleRowEnter(item.task_id)}
            onMouseLeave={handleRowLeave}
            onClick={handleRowClick(item.task_id)}
          >
            <TableCell align="center" className={classes.dateCell}>
              <div className={classes.dateCellDay}>
                {moment(item.reporting_date, defaultDateFormatApi).format(
                  'ddd'
                )}
              </div>
              <span
                className={classnames(classes.dateCellValue, {
                  [classes.dateCellValueActive]: moment(
                    item.reporting_date,
                    defaultDateFormatApi
                  ).isSame(moment(), 'day')
                })}
              >
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

            <TableCell align="center" className={classes.actionCell}>
              {hoveredRow === item.task_id && (
                <IconButton
                  aria-label="Edit"
                  onClick={() => handleEditButtonClick(item)}
                  className={classes.editBtn}
                >
                  <EditFilledIcon />
                </IconButton>
              )}
              {hoveredRow === item.task_id && (
                <IconButton
                  aria-label="Delete"
                  onClick={() => handleDeleteButtonClick(item.task_id)}
                  className={classes.deleteBtn}
                >
                  <DeleteOutlinedIcon />
                </IconButton>
              )}
            </TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  </Paper>
);

export default withStyles(styles)(TimesheetListTable);
