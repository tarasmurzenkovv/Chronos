import {createStyles} from '@material-ui/core';
import {Theme} from '@material-ui/core/styles/createMuiTheme';

const styles = (theme: Theme) =>
  createStyles({
    root: {
      flexGrow: 1,
      height: '100vh'
    },
    gridContainer: {
      height: '100%'
    },
    paper: {
      padding: theme.spacing.unit * 2,
      textAlign: 'center',
      color: theme.palette.text.secondary
    }
  });

export default styles;
