/* eslint import/no-extraneous-dependencies: ["error", {"devDependencies": true}] */
import {createLogger} from 'redux-logger';

import requestsStatuses from '../shared/utils/requestsStatuses';

export default createLogger({
  collapsed: true,
  timestamp: false,
  diff: true,
  duration: true,

  colors: {
    title: (action) => {
      const {type} = action;

      if (type.endsWith(requestsStatuses.pending)) return '#bc9410';
      if (type.endsWith(requestsStatuses.failure)) return '#f20404';
      if (type.endsWith(requestsStatuses.success)) return '#4caf50';

      return 'inherit';
    },
    prevState: () => '#9e9e9e',
    action: () => '#ff6611',
    nextState: () => '#9e9e9e',
    error: () => '#f20404'
  }
});
