import * as moment from 'moment';

import {defaultDateFormatApi} from 'shared/utils/constants';

import {IUserId} from 'shared/types';

export interface IProps {
  ids: IUserId[];
  start: string;
  end: string;
}

export const createFetchReportUrl = ({ids, start, end}: IProps) => {
  let idList = '';
  const startDay = moment(start).format(defaultDateFormatApi);
  const endDay = moment(end).format(defaultDateFormatApi);

  ids.map((item) => (idList += `id=${item}&`));
  return `/api/v0/reporting/xls?${idList}&start=${startDay}&end=${endDay}`;
};
