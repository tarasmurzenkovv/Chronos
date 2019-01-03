import requestsStatuses from './requestsStatuses';

const failureAction = (action: string): string =>
  `${action}_${requestsStatuses.failure}`;

const pendingAction = (action: string): string =>
  `${action}_${requestsStatuses.pending}`;

const successAction = (action: string): string =>
  `${action}_${requestsStatuses.success}`;

interface IcreateAsyncAction {
  failure: string;
  pending: string;
  success: string;
}
const createAsyncAction = (action: string): IcreateAsyncAction => ({
  failure: failureAction(action),
  pending: pendingAction(action),
  success: successAction(action)
});

export default createAsyncAction;
