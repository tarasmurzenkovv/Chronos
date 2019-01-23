import {assign, chain} from 'lodash';

export const sortAndCreateFullName = (userList) =>
  chain(userList)
    .map((item) =>
      assign({full_name: `${item.first_name} ${item.last_name}`}, item)
    )
    .sortBy('full_name')
    .value();
