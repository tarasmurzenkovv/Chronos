import * as React from 'react';

import {
  AppBar,
  IconButton,
  Toolbar,
  Typography,
  withStyles,
  WithStyles
} from '@material-ui/core';
import SettingsIcon from '@material-ui/icons/Settings';
import CalendarIcon from '@material-ui/icons/CalendarToday';
import mainLogo from 'shared/assets/img/syngentaLogo.svg';
import * as theme from './Header.scss';
import styles from './styles';

interface IProps extends WithStyles<typeof styles> {
  pathname: string;
}

const Header: React.FunctionComponent<IProps> = ({classes, pathname}) => (
  <div>
    <AppBar position="static" className={classes.navBar}>
      <Toolbar>
        {/* <IconButton className={theme.menuButton} aria-label="Menu" disabled> */}
        {/* <MenuIcon className={theme.iconMenu} /> */}
        {/* </IconButton> */}
        <div className={theme.logo}>
          <a href="/">
            <img src={mainLogo} alt="Syngenta logo" width="109" height="39" />
          </a>
        </div>
        <Typography variant="h6" color="inherit" className={classes.navTitle}>
          Calendar
        </Typography>
        <IconButton
          aria-label="Calendar"
          className={`${classes.navButton} ${
            pathname === '/' ? classes.active : ''
          }`}
        >
          <CalendarIcon className={classes.iconCalendar} />
        </IconButton>
        <IconButton
          aria-label="Settings"
          className={`${classes.navButton} ${classes.navSettings} ${
            pathname === '/settings' ? classes.active : ''
          }`}
          disabled
        >
          <SettingsIcon className={classes.iconSettings} />
        </IconButton>
      </Toolbar>
    </AppBar>
  </div>
);

export default withStyles(styles)(Header);
