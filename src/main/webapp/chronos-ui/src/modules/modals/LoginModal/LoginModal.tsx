import * as React from 'react';
import {withStyles} from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Modal from '@material-ui/core/Modal';

import styles from './styles';

interface IProps {
  classes?: any;
  isOpen: boolean;
  handleOnClose(): void;
  children?: any;
}

const LoginModal: React.FunctionComponent<IProps> = ({
  classes,
  isOpen,
  handleOnClose
}) => (
  <div>
    <Modal
      aria-labelledby="modal-title"
      aria-describedby="-modal-description"
      open={isOpen}
      onClose={handleOnClose}
    >
      <div className={classes.paper}>
        <Typography variant="h6" id="modal-title">
          Text in a modal
        </Typography>
        <Typography variant="subtitle1" id="simple-modal-description">
          Duis mollis, est non commodo luctus, nisi erat porttitor ligula.
        </Typography>
      </div>
    </Modal>
  </div>
);

const LoginModalWrapped = withStyles(styles)(LoginModal);

export default LoginModalWrapped;
