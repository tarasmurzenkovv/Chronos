import * as React from 'react';

import {CircularProgress, Fade} from '@material-ui/core';
import * as theme from './Loader.scss';

export default ({loading}) => (
  <div className={theme.root}>
    <div className={theme.placeholder}>
      <Fade
        in={loading}
        style={{
          transitionDelay: loading ? '800ms' : '0ms'
        }}
        unmountOnExit
      >
        <CircularProgress />
      </Fade>
    </div>
  </div>
);
