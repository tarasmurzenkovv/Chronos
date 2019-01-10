import * as React from 'react';

import {
  AppBar,
  Toolbar,
  Typography,
  withStyles,
  WithStyles
} from '@material-ui/core';
import MenuIcon from '@material-ui/icons/Menu';
import SettingsIcon from '@material-ui/icons/Settings';
import CalendarIcon from '@material-ui/icons/CalendarToday';
import IconButton from '@material-ui/core/IconButton';
import * as theme from './Header.scss';
import mainLogo from 'shared/assets/img/syngentaLogo.svg';
import styles from './styles';

interface IProps extends WithStyles<typeof styles> {
  isActive: boolean;
  handleBtnChange(): void;
}

const Header: React.FunctionComponent<IProps> = ({
  isActive,
  handleBtnChange,
  classes
}) => (
  <div>
    <AppBar position="static" className={classes.navBar}>
      <Toolbar>
        <IconButton className={theme.menuButton} aria-label="Menu">
          <MenuIcon className={theme.iconMenu} />
        </IconButton>
        <div className={theme.logo}>
          <img src={mainLogo} alt="Syngenta logo" width="109" height="39" />
        </div>
        <Typography variant="h6" color="inherit" className={classes.navTitle}>
          Calendar
        </Typography>
        <IconButton
          aria-label="Calendar"
          className={`${classes.navButton} ${!isActive ? classes.active : ''}`}
          onClick={handleBtnChange}
        >
          <CalendarIcon className={classes.iconCalendar} />
        </IconButton>
        <IconButton
          aria-label="Settings"
          className={`${classes.navButton} ${classes.navSettings} ${
            isActive ? classes.active : ''
          }`}
          onClick={handleBtnChange}
        >
          <SettingsIcon className={classes.iconSettings} />
        </IconButton>
      </Toolbar>
    </AppBar>
  </div>
);

export default withStyles(styles)(Header);
