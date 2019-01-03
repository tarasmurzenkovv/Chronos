import config from 'configurations/config';

export default function apiCall(endpoint: string, options?: any) {
  return fetch(`${config.api_server}${endpoint}`, {
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application/json',
      'Access-Control-Allow-Origin': '*'
    },
    ...options
  }).then((res) => res.json());
}
