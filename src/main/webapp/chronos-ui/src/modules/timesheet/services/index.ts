import {IProps} from '../actions/api/fetchTimesheetListByDateApi';

export const timesheetUrl = (id: number) => `/api/v0/user/${id}/task`;

export const timesheetByDateUrl = ({id, start, end}: IProps) =>
  `/api/v0/user/${id}?start=${start}&end=${end}`;
