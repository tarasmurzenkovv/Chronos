export const INVALID_EMAIL_LOCAL = 100;

export const INVALID_PASSWORD_LOCAL = 105;
export const INVALID_CONFIRM_PASSWORD_LOCAL = 106;
export const PASSWORDS_NOT_MATCH_LOCAL = 107;

export const INVALID_FIRST_NAME_LOCAL = 110;
export const INVALID_LAST_NAME_LOCAL = 111;

export const emailInputErrorCodes = [INVALID_EMAIL_LOCAL];

export const passwordInputErrorCodes = [INVALID_PASSWORD_LOCAL];

export const passwordConfirmInputErrorCodes = [
  INVALID_CONFIRM_PASSWORD_LOCAL,
  PASSWORDS_NOT_MATCH_LOCAL
];

export const firstNameInputErrorCodes = [INVALID_FIRST_NAME_LOCAL];

export const lastNameInputErrorCodes = [INVALID_LAST_NAME_LOCAL];

export const errorMessagesMap = {
  [INVALID_EMAIL_LOCAL]: 'Invalid email',
  [INVALID_PASSWORD_LOCAL]: 'Invalid password',
  [INVALID_CONFIRM_PASSWORD_LOCAL]: 'Invalid confirm password',
  [PASSWORDS_NOT_MATCH_LOCAL]: 'Confirm password does not match the password.',
  [INVALID_FIRST_NAME_LOCAL]: 'Invalid first name',
  [INVALID_LAST_NAME_LOCAL]: 'Invalid last name'
};
