import {createStyles, Theme} from '@material-ui/core';

const styles = (theme: Theme) =>
  createStyles({
    root: {
      top: '72px',
      backgroundColor: 'transparent !important'
    },
    backdropProps: {
      top: '72px',
      backgroundColor: 'transparent !important'
    },
    paper: {
      top: 80,
      minWidth: '331px',
      backgroundColor: '#121958'
    },
    listItem: {
      '&:hover': {
        backgroundColor: theme.palette.primary.main
      }
    },
    listItemSelected: {
      backgroundColor: theme.palette.primary.main
    },

    listItemText: {
      color: '#ffffff',
      display: 'list-item',
      listStyleType: 'disc',
      listStylePosition: 'inside'
    }
  });

export default styles;
