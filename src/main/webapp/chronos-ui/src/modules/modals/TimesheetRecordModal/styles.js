const styles = () => ({
  root: {
    padding: '0px 56px',
    width: '700px',
    maxWidth: '700px',
    maxHeight: '700px',
    boxSizing: 'border-box',
    overflowY: 'hidden',
    overflowX: 'hidden'
  },
  container: {
    display: 'flex',
    flexWrap: 'wrap',
    textAlign: 'center'
  },
  formControl: {
    width: '100%',
    borderRadius: '5px'
  },
  textFieldLabel: {
    color: '#4a4a4a'
  },
  textFieldLabelFocused: {
    color: 'rgba(173, 181, 199, 0.87)'
  },
  commentsInput: {
    width: '100%',
    $textFieldFocusedNotchedOutline: {
      borderColor: '#25cdda'
    },
    '&:hover $textFieldFocusedNotchedOutline': {
      borderColor: 'rgba(173, 181, 199, 0.87) !important'
    }
  },
  textFieldOutlinedInput: {
    width: '257px',
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

  modalTitle: {
    textAlign: 'center',
    fontSize: '20px'
  },
  labelText: {
    color: 'rgba(0, 0, 0, 0.87)'
  },
  saveBtn: {
    marginTop: '70px',
    marginBottom: '50px',
    width: '137px',
    height: '42px',
    backgroundColor: '#25cdda',
    color: '#ffffff',
    fontWeight: '400',
    textTransform: 'capitalize',
    '&:hover': {
      backgroundColor: '#25cdda'
    }
  },
  modalContent: {
    overflowY: 'hidden',
    overflowX: 'hidden'
  },
  selectionList: {
    position: 'absolute',
    top: '300px'
  },
  closeBtn: {
    position: 'absolute',
    top: '5px',
    right: '0px'
  },
  iconClose: {
    width: '36px',
    height: '36px'
  },
  errorText: {
    color: '#f44336'
  }
});

export default styles;
