export default function apiCall(endpoint: string, options?: any) {
  return fetch(endpoint, options).then((res) => res.json());
}
