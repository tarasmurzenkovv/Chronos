import {INVALID_EMAIL_API, INVALID_PASSWORD_API} from './apiErrorCodes';

import {
  INVALID_CONFIRM_PASSWORD_LOCAL,
  INVALID_EMAIL_LOCAL,
  INVALID_FIRST_NAME_LOCAL,
  INVALID_LAST_NAME_LOCAL,
  INVALID_PASSWORD_LOCAL,
  PASSWORDS_NOT_MATCH_LOCAL
} from './localErrorCodes';

export const emailInputErrorCodes = [INVALID_EMAIL_LOCAL, INVALID_EMAIL_API];

export const passwordInputErrorCodes = [
  INVALID_PASSWORD_LOCAL,
  INVALID_PASSWORD_API
];

export const passwordConfirmInputErrorCodes = [
  INVALID_CONFIRM_PASSWORD_LOCAL,
  PASSWORDS_NOT_MATCH_LOCAL
];

export const firstNameInputErrorCodes = [INVALID_FIRST_NAME_LOCAL];

export const lastNameInputErrorCodes = [INVALID_LAST_NAME_LOCAL];

export const errorMessagesMap = {
  [INVALID_EMAIL_LOCAL]: 'Invalid email',
  [INVALID_EMAIL_API]: 'Email not exist',
  [INVALID_PASSWORD_LOCAL]: 'Invalid password',
  [INVALID_PASSWORD_API]: 'Invalid password',
  [INVALID_CONFIRM_PASSWORD_LOCAL]: 'Invalid confirm password',
  [PASSWORDS_NOT_MATCH_LOCAL]: 'Confirm password does not match the password.',
  [INVALID_FIRST_NAME_LOCAL]: 'Invalid first name',
  [INVALID_LAST_NAME_LOCAL]: 'Invalid last name'
};
