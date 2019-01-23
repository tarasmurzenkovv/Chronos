import {createStyles, Theme} from '@material-ui/core';

const styles = (theme: Theme) =>
  createStyles({
    paper: {
      display: 'flex',
      flexWrap: 'wrap',
      textAlign: 'center',
      marginBottom: 29
    },
    hoveredRow: {
      '&:hover': {
        backgroundColor: 'green'
      }
    },
    tableHeadCell: {
      color: 'rgba(173, 181, 199, 0.87)'
    },
    tableHeadCellFirst: {
      borderRight: '1px solid #ececec',
      padding: 0
    },
    checkboxCell: {
      borderRight: '1px solid #ececec',
      padding: 0
    },
    checkboxChecked: {
      color: theme.palette.primary.main
    },
    form: {
      textAlign: 'center'
    },
    startDay: {
      marginRight: 20,
      borderColor: 'rgba(173, 181, 199, 0.87)'
    },
    button: {
      marginTop: 24,
      width: 200,
      height: 42,
      backgroundColor: theme.palette.primary.main,
      color: '#ffffff',
      textTransform: 'capitalize',
      '&:hover': {
        backgroundColor: '#25cdda !important'
      }
    },
    downloadIcon: {
      marginLeft: 8
    }
  });

export default styles;
