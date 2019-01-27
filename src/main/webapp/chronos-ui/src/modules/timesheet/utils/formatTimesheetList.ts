export const formatTimesheetList = (timesheetList, projectsList) =>
  timesheetList.length && projectsList.length
    ? timesheetList.map((timesheetItem) => ({
        ...timesheetItem,
      project_name: projectsList.length
        ? projectsList.find(
          (projectItem) => projectItem.id === timesheetItem.project_id
            ).project_name
          : '',
        color: projectsList.length
        ? projectsList.find(
          (projectItem) => projectItem.id === timesheetItem.project_id
            ).color
          : ''
    }))
    : [];
