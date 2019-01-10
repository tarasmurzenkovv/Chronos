const styles = (theme) => ({
  container: {
    display: 'flex',
    flexWrap: 'wrap',
    width: '700px',
    textAlign: 'center'
  },
  formControl: {
    margin: theme.spacing.unit,
    minWidth: 120
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
  }
});

export default styles;
