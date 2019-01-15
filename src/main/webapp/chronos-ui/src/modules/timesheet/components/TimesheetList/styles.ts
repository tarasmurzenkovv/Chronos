import {createStyles} from '@material-ui/core';

const styles = () =>
  createStyles({
    content: {
      overflowY: 'auto'
    },
    table: {
      width: '100%'
    },
    tableHeadCell: {
      height: '20px',
      paddingRight: '24px',
      color: 'rgba(173, 181, 199, 0.87)',
      borderRight: '1px solid rgb(224, 224, 224)'
    },
    row: {
      padding: 0,
      '&:hover': {
        backgroundColor: 'rgba(25, 25, 100, 0.07)'
      }
    },
    dateCell: {
      padding: '10px 0',
      borderRight: '1px solid rgb(224, 224, 224)'
    },
    dateCellDay: {
      marginBottom: '5px',
      textTransform: 'uppercase',
      color: 'rgba(173, 181, 199, 0.87)'
    },
    dateCellValue: {
      borderRadius: '11.5px',
      backgroundColor: '#d6d6e3',
      color: '#ffffff',
      padding: '2px 10px'
    },
    timeCell: {
      fontSize: '24px',
      paddingRight: '24px',
      color: 'rgba(0, 0, 0, 0.87)'
    },
    nameCell: {
      paddingLeft: '10px'
    },
    tableHeadCommentCell: {
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center'
    },
    commentCell: {
      maxWidth: '400px',
      padding: '10px',
      paddingLeft: '10px',
      color: 'rgba(173, 181, 199, 0.87)',
      wordWrap: 'break-word'
    },
    commentCellIcon: {
      marginRight: '10px'
    },
    button: {
      color: '#ffffff',
      position: 'fixed',
      bottom: 0,
      right: 0,
      transform: 'translate(-25%, -25%)'
    },
    deleteBtn: {
      '&:hover': {
        backgroundColor: 'transparent'
      }
    },
    deleteIcon: {
      color: 'rgba(173, 181, 199, 0.87)',
      '&:hover': {
        color: '#e32657',
        backgroundColor: 'transparent'
      }
    }
  });

export default styles;
