// export const EMAIL_PATTERN = '^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$';
// export const PASSWORD_PATTERN = '^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!"#$%&\'()*+,\\-./:;<=>?@[\\]^_`{|}~]).{6,20}$';

export const EMAIL_PATTERN = '^(.+)@(.+)$';
export const PASSWORD_PATTERN =
  '^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$';
