import {connect} from 'react-redux';
import {compose, lifecycle, withHandlers, withState} from 'recompose';
import {selectProject} from 'modules/settings/actions/settingsProject';
import Settings from './Settings';
import getProjectsList from '../../modules/modals/actions/api/getProjectsList';
import {createProjectApi} from '../settings/actions/api/createProjectApi';
import {deleteProjectApi} from '../settings/actions/api/deleteProjectApi';
import {editProjectApi} from '../settings/actions/api/editProjectApi';

const mapStateToProps = (state) => ({
  projectsList: state.projects.list,
  selectedProjectId: state.settings.selectedProjectId,
  colorsSet: state.projects.list
    .map((item) => item.color)
    .filter((elem, pos, arr) => arr.indexOf(elem) === pos)
});

const mapDispatchToProps = {
  getProjectsList,
  createProjectApi,
  deleteProjectApi,
  editProjectApi,
  selectProject
};

interface IProps {
  getProjectsList: () => void;
}

export default compose(
  connect(
    mapStateToProps,
    mapDispatchToProps
  ),

  withState('projects', 'updateProjectsList', ({projectsList}) => projectsList),
  withState('color', 'setColorValue', {}),
  withState('projectNew', 'setProjectNew', {}),
  withState('envokedAction', 'setActionName', []),

  withHandlers({
    handleAddProjectBtnClick: ({
      projects,
      updateProjectsList,
      color,
      envokedAction
    }) => () => {
      updateProjectsList(
        projects.concat([{id: Math.random(), color: '#8bc34a'}])
      );
      envokedAction.push('add');
    },
    handleColorChange: ({color, envokedAction}) => () => (event) => {
      const {value, name} = event.target;
      color[name] = value;
    },
    handleDeleteProject: ({
      projects,
      updateProjectsList,
      selectProject,
      envokedAction
    }) => (id) => () => {
      const currentProject = projects.find((item) => item.id === id);
      projects.splice(projects.indexOf(currentProject), 1);
      updateProjectsList(projects);
      selectProject(id);
      envokedAction.push('delete');
    },
    handleProjectChange: ({projectNew, setProjectNew, envokedAction}) => (
      id
    ) => (event) => {
      const {value} = event.target;
      projectNew.id = id;
      projectNew.project_name = value;
      setProjectNew(projectNew);
    }
  }),

  withHandlers({
    handleFormSubmit: ({
      selectedProjectId,
      createProjectApi,
      deleteProjectApi,
      editProjectApi,
      projectNew,
      color,
      envokedAction,
        projectsList
    }) => (event) => {
      event.preventDefault();

      const params = {
        id: projectNew.id,
        project_name: projectNew.project_name,
        project_description: projectNew.project_name,
        project_type_id: 1,
        deleted: 'false',
        color: color[`color-${projectNew.id}`]
      };


      envokedAction.forEach((item) => {
        switch (item) {
        case 'delete':
          deleteProjectApi(selectedProjectId).catch(() => {});
          break;
        case 'add':
          createProjectApi(params).catch(() => {});
          break;
        case 'edit':
          editProjectApi(params).catch(() => {});
          break;
        default:
        }
      });
    }
  }),

  lifecycle<IProps, {}>({
    componentDidMount() {
      this.props.getProjectsList();
    }
  })
)(Settings);
