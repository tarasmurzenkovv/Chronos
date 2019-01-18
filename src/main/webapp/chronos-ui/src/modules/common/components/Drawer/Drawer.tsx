import * as React from 'react';

import {
  Drawer,
  List,
  ListItem,
  ListItemText,
  WithStyles,
  withStyles
} from '@material-ui/core';
import classnames from 'classnames';
import {IUser} from 'modules/common/reducers/user';
import styles from './styles';

interface IProps extends WithStyles<typeof styles> {
  list: IUser[];
  isOpen: boolean;
  selectedId: number;

  setDrawerStatus(): void;
  handleUserlistItemClick: (
    id: number | string
  ) => (event: React.SyntheticEvent) => void;
}

const DrawerComponent: React.FunctionComponent<IProps> = ({
  classes,
  list,
  isOpen,
  selectedId,
  setDrawerStatus,
  handleUserlistItemClick
}) => (
  <Drawer
    open={isOpen}
    onClose={setDrawerStatus}
    classes={{
      root: classes.root,
      paper: classes.paper
    }}
    ModalProps={{
      BackdropProps: {
        classes: {
          root: classes.backdropProps
        }
      }
    }}
  >
    <div tabIndex={0} role="button">
      <List>
        {list.map((item) => (
          <ListItem
            button
            key={item.id}
            classes={{
              root: classnames(classes.listItem, {
                [classes.listItemSelected]: selectedId === item.id
              })
            }}
            onClick={handleUserlistItemClick(item.id)}
          >
            <ListItemText
              primary={`${item.first_name} ${item.last_name} `}
              classes={{primary: classes.listItemText}}
            />
          </ListItem>
        ))}
      </List>
    </div>
  </Drawer>
);

export default withStyles(styles)(DrawerComponent);
