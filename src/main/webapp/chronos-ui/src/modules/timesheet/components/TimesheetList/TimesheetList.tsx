import * as React from 'react';

import {Fab, withStyles, WithStyles} from '@material-ui/core';
import AddIcon from '@material-ui/icons/Add';

import {IListItem as IListItemProject} from 'modules/modals/reducers/projects';

import {IListItem as IListItemTimesheet} from '../../reducers/timesheet';
import {EmptyList, FilterByMonth, TimesheetListTable} from '..';
import * as theme from './TimesheetList.scss';
import styles from './styles';

interface IList extends IListItemTimesheet, IListItemProject {}

interface IProps extends WithStyles<typeof styles> {
  list: IList[];
  month: string;
  isIconVisible: boolean;
  hoveredRow: number;

  handleAddMonthFilterButtonClick(): void;
  handleMinusMonthFilterButtonClick(): void;
  handleButtonClick(): void;
  handleDeleteButtonClick(id: number): void;
  handleEditButtonClick(item: any): void;
  handleRowEnter: (id: number) => (id: React.SyntheticEvent) => void;
  handleRowClick: (id: number) => (id: React.SyntheticEvent) => void;
  handleRowLeave(item: any): void;
}

const TimesheetList: React.FunctionComponent<IProps> = ({
  classes,
  handleButtonClick,
  handleDeleteButtonClick,
  handleEditButtonClick,
  handleRowClick,
  list,
  month,
  hoveredRow,
  handleRowEnter,
  handleRowLeave,
  handleAddMonthFilterButtonClick,
  handleMinusMonthFilterButtonClick
}) => (
  <div className={theme.root}>
    <FilterByMonth
      month={month}
      handleMinusMonthFilterButtonClick={handleMinusMonthFilterButtonClick}
      handleAddMonthFilterButtonClick={handleAddMonthFilterButtonClick}
    />
    {list.length ? (
      <TimesheetListTable
        list={list}
        handleDeleteButtonClick={handleDeleteButtonClick}
        handleEditButtonClick={handleEditButtonClick}
        handleRowEnter={handleRowEnter}
        hoveredRow={hoveredRow}
        handleRowLeave={handleRowLeave}
        handleRowClick={handleRowClick}
      />
    ) : (
      <EmptyList />
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
);

export default withStyles(styles)(TimesheetList);
