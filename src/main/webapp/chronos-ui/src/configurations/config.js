const config = {
  version: process.env.REACT_APP_VERSION,
  api_server: process.env.REACT_APP_API_SERVER,

  ws_server: process.env.REACT_APP_WS_SERVER,

  enable_logs: Boolean(process.env.REACT_APP_ENABLE_LOGS),
  enable_devtools: Boolean(process.env.REACT_APP_ENABLE_DEVTOOLS),
  enable_update_analytics: Boolean(
    process.env.REACT_APP_ENABLE_UPDATE_ANALYTICS
  )
};
export default config;
