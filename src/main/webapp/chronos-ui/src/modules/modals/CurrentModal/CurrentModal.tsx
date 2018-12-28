import * as React from 'react';

import {LoginModal} from '..';

interface IProps {
  modal: {
    id: string;
  };
}

const modalsMap: any = {
  LOGIN_MODAL: <LoginModal />
};

const CurrentModal: React.FunctionComponent<IProps> = ({modal}) => (
  <React.Fragment>{modal && modalsMap[modal.id]}</React.Fragment>
);

export default CurrentModal;
