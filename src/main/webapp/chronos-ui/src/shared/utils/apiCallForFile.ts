import * as moment from 'moment';
import {saveAs} from 'file-saver';

import config from 'configurations/config';

export const apiCallForFile = (endpoint: string, options?: any) =>
  /* eslint-disable no-shadow */
  fetch(`${config.api_server}${endpoint}`, {
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application/json',
      'Access-Control-Allow-Origin': '*'
    },
    ...options
  })
    .then((res) => res.blob())
    .then((blob) =>
      saveAs(blob, `Time report_${moment().format('DD_MM_YYYY')}.xlsx`)
    );
