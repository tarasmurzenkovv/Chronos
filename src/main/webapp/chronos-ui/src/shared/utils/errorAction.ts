interface IRes {
  type: string;
  error: boolean;
  payload: {
    error: any;
  };
}

const errorAction = (type: string, error: any): IRes => ({
  type,
  error: true,
  payload: {
    error
  }
});

export default errorAction;
