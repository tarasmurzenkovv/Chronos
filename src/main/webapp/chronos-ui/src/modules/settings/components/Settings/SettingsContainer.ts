import {connect} from 'react-redux';
import {compose, lifecycle, withHandlers, withState} from 'recompose';
import {selectProject} from 'modules/settings/actions/settingsProject';
import Settings from './Settings';
import getProjectsList from '../../../modals/actions/api/getProjectsList';
import {createProjectApi} from '../../actions/api/createProjectApi';
import {deleteProjectApi} from '../../actions/api/deleteProjectApi';
import {editProjectApi} from '../../actions/api/editProjectApi';

const mapStateToProps = (state) => ({
  projectsList: state.projects.list.filter((item) => item.deleted !== true),
  selectedProjectId: state.settings.selectedProjectId,
  colorsSet: state.projects.list
    .map((item) => item.color)
    .filter((elem, pos, arr) => arr.indexOf(elem) === pos),
  projectsColorsList: state.projects.list.reduce(
    (obj, item) => ((obj[item.id] = item.color), obj),
    {}
  ),
  isLoading: state.projects.isLoading
});

const mapDispatchToProps = {
  getProjectsList,
  createProjectApi,
  deleteProjectApi,
  editProjectApi,
  selectProject
};

interface IProps {
  getProjectsList: () => any;
  updateProjectsList: (projects: any) => any;
  setColorValue: (projects: any) => any;
}

export default compose(
  connect(
    mapStateToProps,
    mapDispatchToProps
  ),

  withState('projects', 'updateProjectsList', []),
  withState('projectSelected', 'updateProjectSelected', []),
  withState('color', 'setColorValue', {}),
  withState('editParams', 'setEditParams', {}),
  withState('addParams', 'setAddParams', {}),
  withState('invokedAction', 'setActionName', []),
  withState('isBtnActive', 'setActiveBtn', false),
  withState('isProjectClosed', 'setClosedProject', {}),
  withState('shouldBlockNavigation', 'setBlockNavigation', true),

  withHandlers({
    handleAddProjectBtnClick: ({
      projects,
      updateProjectsList,
      invokedAction
    }) => () => {
      const idValue = Math.floor(Math.random() * (1000 - 1 + 1) + 1);
      updateProjectsList(
        projects.concat([
          {
            id: idValue,
            color: '#8bc34a',
            project_name: 'New project',
            isNew: true
          }
        ])
      );
      invokedAction.push('add');
    },
    handleColorChange: ({
      color,
      invokedAction,
      setActiveBtn,
      editParams,
      projects,
      addParams
    }) => () => (event) => {
      const {value, name} = event.target;
      const newProjectName = projects.find(
        (item) => item.id === parseInt(name, 10)
      ).project_name;
      color[name] = value;

      setActiveBtn(true);
      if (newProjectName === 'New project') {
        addParams[name] = {
          id: parseInt(name, 10),
          project_name:
            addParams[name] === undefined
              ? newProjectName
              : addParams[name].project_name,
          color: value
        };
        invokedAction.push('add');
      } else {
        editParams[name] = {
          id: parseInt(name, 10),
          project_name:
            editParams[name] === undefined
              ? newProjectName
              : editParams[name].project_name,
          color: value
        };
        invokedAction.push('edit');
      }
    },
    handleDeleteProject: ({
      projects,
      setActiveBtn,
      invokedAction,
      projectSelected,
      isProjectClosed
    }) => (id) => (event) => {
      const currentProject = event.currentTarget.getAttribute('name');
      const deletedProject = projects.find((item) => item.id === id);
      if (deletedProject.isNew) {
        projects.splice(projects.indexOf(currentProject), 1);
        setActiveBtn(false);
      }
      if (currentProject == id && !deletedProject.isNew) {
        isProjectClosed[id] = !isProjectClosed[id];
        setActiveBtn(true);
      }
      projectSelected.push(id);
      invokedAction.push('delete');
    },
    handleProjectChange: ({
      editParams,
      setProjectNew,
      invokedAction,
      setActiveBtn,
      addParams,
      setAddParams,
      color
    }) => (id) => (event) => {
      const {value, name} = event.target;
      if (name === 'New project') {
        addParams[id] = {
          id,
          project_name: value,
          color: `${color[id] === undefined ? '#8bc34a' : color[id]}`
        };
      } else {
        editParams[id] = {
          id,
          project_name: value,
          color: `${color[id] === undefined ? '#8bc34a' : color[id]}`
        };
        invokedAction.push('edit');
      }
      setActiveBtn(true);
    }
  }),

  withHandlers({
    handleFormSubmit: ({
      selectedProjectId,
      createProjectApi,
      deleteProjectApi,
      editProjectApi,
      setActiveBtn,
      setActionName,
      projectNew,
      projectSelected,
      invokedAction,
      addParams,
      editParams
    }) => (event) => {
      event.preventDefault();

      const actionsAll = invokedAction.filter(
        (elem, pos, arr) => arr.indexOf(elem) === pos
      );
      const addData = Object.entries(addParams);
      const editData = Object.entries(editParams);

      actionsAll.forEach((item) => {
        switch (item) {
        case 'delete':
          projectSelected.forEach((value) => {
            deleteProjectApi(value)
              .then(() => {
                  setActiveBtn(false);
                })
              .catch(() => {});
          });
          break;
        case 'add':
          addData.forEach((value: any) => {
            const params = {
              id: value[1].id,
              project_name: value[1].project_name,
              project_description: value[1].project_name,
              project_type_id: 1,
              deleted: 'false',
              color: value[1].color
            };
            createProjectApi(params)
              .then(() => {
                setActiveBtn(false);
                })
              .catch(() => {});
          });
          break;
        case 'edit':
          editData.forEach((value: any) => {
            const params = {
              id: value[1].id,
              project_name: value[1].project_name,
              project_description: value[1].project_name,
              project_type_id: 1,
              deleted: 'false',
              color: value[1].color
            };
              editProjectApi(params)
              .then(() => {
                  setActiveBtn(false);
                })
                .catch(() => {});
            });
          break;
        default:
        }
      });
      invokedAction.splice(0, invokedAction.length);
    }
  }),
  lifecycle<IProps, {}>({
    componentDidMount() {
      this.props.getProjectsList().then((response) => {
        const deletedProject = response.payload.list.filter(
          (item) => item.deleted === true
        );
        const allProjects = response.payload.list;
        deletedProject.forEach((item) => {
          allProjects.push(allProjects.splice(allProjects.indexOf(item), 1)[0]);
        });
        this.props.updateProjectsList(
          // response.payload.list.filter((item) => item.deleted !== true)
          allProjects
        );
        this.props.setColorValue(
          response.payload.list.reduce(
            (obj, item) => ((obj[item.id] = item.color), obj),
            {}
          )
        );
      });
    }
  })
)(Settings);
