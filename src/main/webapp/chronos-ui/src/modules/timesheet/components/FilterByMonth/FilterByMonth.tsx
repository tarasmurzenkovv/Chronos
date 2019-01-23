import * as React from 'react';

import KeyboardArrowLeft from '@material-ui/icons/KeyboardArrowLeft';
import KeyboardArrowRight from '@material-ui/icons/KeyboardArrowRight';

import {IconButton} from '@material-ui/core';
import * as theme from './FilterByMonth.scss';

interface IProps {
  month: string;

  handleAddMonthFilterButtonClick(): void;
  handleMinusMonthFilterButtonClick(): void;
}

const FilterByMonth: React.FunctionComponent<IProps> = ({
  month,
  handleAddMonthFilterButtonClick,
  handleMinusMonthFilterButtonClick
}) => (
  <div className={theme.root}>
    <IconButton onClick={handleMinusMonthFilterButtonClick}>
      <KeyboardArrowLeft />
    </IconButton>
    <span>{month}</span>
    <IconButton onClick={handleAddMonthFilterButtonClick}>
      <KeyboardArrowRight />
    </IconButton>
  </div>
);

export default FilterByMonth;
