import {createStyles} from '@material-ui/core';

const styles = () =>
  createStyles({
    container: {
      display: 'flex',
      flexWrap: 'wrap',
      marginLeft: '-8px',
      marginRight: '-8px'
    },
    button: {
      fontSize: '16px',
      fontWeight: 'normal',
      textTransform: 'capitalize',
      borderWidth: '2px'
    }
  });

export default styles;
