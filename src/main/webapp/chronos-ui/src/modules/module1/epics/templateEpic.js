import {ofType} from 'redux-observable';
import {tap} from 'rxjs/operators';

export default (action$) =>
  action$.pipe(
    ofType('TEST'),
    // eslint-disable-next-line
    tap(() => console.log('epic'))
  );
