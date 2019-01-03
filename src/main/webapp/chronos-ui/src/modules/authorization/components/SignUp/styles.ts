import {createStyles} from '@material-ui/core';
import {Theme} from '@material-ui/core/styles/createMuiTheme';

const styles = (theme: Theme) =>
  createStyles({
    container: {
      display: 'flex',
      flexWrap: 'wrap',
      marginLeft: '-8px',
      marginRight: '-8px'
    },
    textField: {
      marginLeft: theme.spacing.unit,
      marginRight: theme.spacing.unit,
      width: '100%',
      height: '56px',
      '&:hover': {
        borderColor: '#636F88 !important'
      }
    },
    textFieldLabel: {
      color: '#4a4a4a'
    },
    textFieldLabelFocused: {
      color: 'rgba(173, 181, 199, 0.87)'
    },
    textFieldOutlinedInput: {
      $textFieldFocusedNotchedOutline: {
        borderColor: '#25cdda'
      },
      '&:hover $textFieldFocusedNotchedOutline': {
        borderColor: 'rgba(173, 181, 199, 0.87) !important'
      }
    },
    textFieldFocusedNotchedOutline: {
      borderWidth: '2px',
      borderColor: 'rgba(173, 181, 199, 0.87)',
      '&:hover': {
        borderWidth: '2px',
        borderColor: 'rgba(173, 181, 199, 0.87)'
      }
    },
    group: {
      margin: `${theme.spacing.unit}px 0`
    },
    formHelperTextProps: {
      position: 'absolute',
      bottom: '-20px'
    }
  });

export default styles;
