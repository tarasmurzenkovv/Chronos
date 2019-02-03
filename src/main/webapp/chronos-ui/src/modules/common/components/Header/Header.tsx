import * as React from 'react';

import {
  AppBar,
  IconButton,
  Toolbar,
  Typography,
  Tooltip,
  withStyles,
  WithStyles
} from '@material-ui/core';
import CloseIcon from '@material-ui/icons/Close';
import SettingsIcon from '@material-ui/icons/Settings';
import CalendarIcon from '@material-ui/icons/CalendarToday';
import ChartIcon from '@material-ui/icons/InsertChart';
import PermIdentity from '@material-ui/icons/PermIdentity';

import mainLogo from 'shared/assets/img/syngentaLogo.svg';

import * as theme from './Header.scss';
import styles from './styles';

interface IProps extends WithStyles<typeof styles> {
  pathname: string;
  userName: string;
  isAdmin: boolean;
  isDrawerOpen: boolean;

  handleButtonClick: (route: string) => (event: React.SyntheticEvent) => void;
  setDrawerStatus(): void;
}
const HeaderText = (userName) => ({
  '/': (
    <span>
      Calendar <span>{userName}</span>
    </span>
  ),
  '/reports': 'Reports',
  '/settings': 'Settings'
});
const Header: React.FunctionComponent<IProps> = ({
  classes,
  pathname,
  isAdmin,
  isDrawerOpen,
  handleButtonClick,
  setDrawerStatus,
  userName
}) => (
  <div>
    <AppBar position="static" className={classes.navBar}>
      <Toolbar>
        {isAdmin && (
          <Tooltip title="Users">
            <IconButton
              color="primary"
              aria-label="Menu"
              onClick={setDrawerStatus}
            >
              {isDrawerOpen ? <CloseIcon /> : <PermIdentity />}
            </IconButton>
          </Tooltip>
        )}
        <div className={theme.logo}>
          <a href="/">
            <img src={mainLogo} alt="Syngenta logo" width="109" height="39" />
          </a>
        </div>
        <Typography variant="h6" color="inherit" className={classes.navTitle}>
          {HeaderText(userName)[pathname]}
        </Typography>
        {isAdmin && (
          <Tooltip title="Reports">
            <IconButton
              aria-label="Reports"
              className={`${classes.navButton} ${
                pathname === '/reports' ? classes.active : ''
              }`}
              onClick={handleButtonClick('/reports')}
            >
              <ChartIcon className={classes.iconCalendar} />
            </IconButton>
          </Tooltip>
        )}
        <Tooltip title="Calendar">
          <IconButton
            aria-label="Calendar"
            className={`${classes.navButton} ${
              pathname === '/' || pathname === '/calendar' ? classes.active : ''
            }`}
            onClick={handleButtonClick('/calendar')}
          >
            <CalendarIcon className={classes.iconCalendar} />
          </IconButton>
        </Tooltip>
        {isAdmin && (
          <Tooltip title="Settings">
            <IconButton
              aria-label="Settings"
              className={`${classes.navButton} ${classes.navSettings} ${
                pathname === '/settings' ? classes.active : ''
              }`}
              onClick={handleButtonClick('/settings')}
            >
              <SettingsIcon className={classes.iconSettings} />
            </IconButton>
          </Tooltip>
        )}
      </Toolbar>
    </AppBar>
  </div>
);

export default withStyles(styles)(Header);
