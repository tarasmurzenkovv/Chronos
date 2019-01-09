import * as React from 'react';

import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import MenuIcon from '@material-ui/icons/Menu';
import SettingsIcon from '@material-ui/icons/Settings';
import CalendarIcon from '@material-ui/icons/CalendarToday';
import IconButton from '@material-ui/core/IconButton';
import * as theme from './Header.scss';
import mainLogo from 'shared/assets/img/syngentaLogo.svg';

interface Props {}

interface State {
  isActive: boolean;
}
class Header extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {isActive: false};
  }

  handleBtnClick = () => {
    this.setState((prevState) => ({
      isActive: !prevState.isActive
    }));
  };

  render() {
    return (
      <div>
        <AppBar position="static" className={theme.navBar}>
          <Toolbar>
            <IconButton className={theme.menuButton} aria-label="Menu">
              <MenuIcon className={theme.iconMenu} />
            </IconButton>
            <div className={theme.logo}>
              <img src={mainLogo} alt="Syngenta logo" width="109" height="39" />
            </div>
            <Typography variant="h6" color="inherit" className={theme.navTitle}>
              Calendar
            </Typography>
            <IconButton
              aria-label="Calendar"
              className={`${theme.navButton} ${
                !this.state.isActive ? theme.active : ''
              }`}
              onClick={this.handleBtnClick}
            >
              <CalendarIcon className={theme.iconCalendar} />
            </IconButton>
            <IconButton
              aria-label="Settings"
              className={`${theme.navButton} ${theme.navSettings} ${
                this.state.isActive ? theme.active : ''
              }`}
              onClick={this.handleBtnClick}
            >
              <SettingsIcon className={theme.iconSettings} />
            </IconButton>
          </Toolbar>
        </AppBar>
      </div>
    );
  }
}

export default Header;
