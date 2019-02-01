const styles = () => ({
  root: {
    padding: '0px 56px',
    width: '700px',
    maxWidth: '700px',
    maxHeight: '700px',
    boxSizing: 'border-box',
    overflowY: 'hidden',
    overflowX: 'hidden',
    '@media (max-width:560px)': {
      // eslint-disable-line no-useless-computed-key
      paddingRight: '36px',
      paddingLeft: '36px'
    }
  },
  container: {
    display: 'flex',
    flexWrap: 'wrap',
    textAlign: 'center'
  },
  formControl: {
    width: '100%',
    borderRadius: '5px',
    textAlign: 'left'
  },
  textFieldTime: {
    margin: 0,
    '@media (max-width:560px)': {
      // eslint-disable-line no-useless-computed-key
      width: '100%'
    }
  },
  textFieldLabel: {
    color: '#4a4a4a'
  },
  textFieldLabelFocused: {
    color: 'rgba(173, 181, 199, 0.87)'
  },
  textFieldRequired: {
    '& span': {
      color: 'red'
    }
  },
  labelFieldRequired: {
    '& span': {
      float: 'left',
      marginRight: '5px',
      color: 'red'
    }
  },
  labelFiledBlock: {
    fontSize: '13px',
    marginLeft: '20px',
    color: '#000000'
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
    },
    '@media (max-width:560px)': {
      // eslint-disable-line no-useless-computed-key
      width: 'auto'
    }
  },
  textFieldOutlinedInputError: {
    width: '257px',
    $textFieldFocusedNotchedOutline: {
      borderColor: '#25cdda'
    },
    '&:hover $textFieldFocusedNotchedOutline': {
      borderColor: 'red !important'
    },
    '@media (max-width:560px)': {
      // eslint-disable-line no-useless-computed-key
      width: 'auto'
    }
  },
  dateBlock: {
    '@media (max-width:560px)': {
      // eslint-disable-line no-useless-computed-key
      marginTop: '25px',
      width: '100%'
    }
  },
  selectOutlinedInput: {
    width: '100%',
    $textFieldFocusedNotchedOutline: {
      borderColor: '#25cdda'
    },
    '&:hover $textFieldFocusedNotchedOutline': {
      borderColor: 'rgba(173, 181, 199, 0.87) !important'
    }
  },

  selectOutlinedInputError: {
    width: '100%',
    $textFieldFocusedNotchedOutline: {
      borderColor: '#25cdda'
    },
    '&:hover $textFieldFocusedNotchedOutline': {
      borderColor: 'red !important'
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
    marginTop: '24px',
    marginBottom: '32px',
    width: '137px',
    height: '42px',
    backgroundColor: '#25cdda',
    color: '#ffffff',
    fontWeight: '400',
    textTransform: 'capitalize',
    '&:hover': {
      backgroundColor: '#25cdda !important'
    }
  },
  modalContent: {
    paddingTop: '18px!important',
    paddingBottom: '0px!important',
    overflowY: 'hidden',
    overflowX: 'hidden',
    '@media (max-width:768px)': {
      // eslint-disable-line no-useless-computed-key
      paddingRight: '15px',
      paddingLeft: '15px'
    }
  },
  selectionList: {
    position: 'absolute',
    top: '300px'
  },
  closeBtn: {
    position: 'absolute',
    top: '19px',
    right: '17px'
  },
  iconClose: {
    width: '36px',
    height: '36px'
  },
  errorText: {
    color: '#f44336',
    position: 'absolute',
    bottom: '-20px'
  },
  errorTime: {
    color: '#f44336',
    position: 'absolute',
    top: '257px',
    left: '79px'
  }
});

export default styles;
