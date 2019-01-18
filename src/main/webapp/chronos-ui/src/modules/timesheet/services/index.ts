export const timesheetUrl = (id: number) => `/api/v0/user/${id}/task`;

export interface IProps {
  id: number;
  start: string;
  end: string;
}

export const timesheetByDateUrl = ({id, start, end}: IProps) =>
  `/api/v0/user/${id}?start=${start}&end=${end}`;
