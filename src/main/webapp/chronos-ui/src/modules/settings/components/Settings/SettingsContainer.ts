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
  withState('color', 'setColorValue', {}),
  withState('projectNew', 'setProjectNew', {}),
  withState('invokedAction', 'setActionName', []),
  withState('isBtnActive', 'setActiveBtn', false),
  withState('shouldBlockNavigation', 'setBlockNavigation', true),

  withHandlers({
    handleAddProjectBtnClick: ({
      projects,
      updateProjectsList,
      invokedAction
    }) => () => {
      const idValue = Math.floor(Math.random() * (100 - 1 + 1) + 1);
      updateProjectsList(
        projects.concat([
          {id: idValue, color: '#8bc34a', project_name: 'New project'}
        ])
      );
      invokedAction.push('add');
    },
    handleColorChange: ({
      color,
      invokedAction,
      setActiveBtn,
      projectNew,
      projects
    }) => () => (event) => {
      const {value, name} = event.target;
      color[name] = value;
      invokedAction.push('edit');
      projectNew.id = name;
      projectNew.color = value;
      projectNew.project_name =
        projectNew.project_name !== undefined
          ? projectNew.project_name
          : projects.find((item) => item.id === parseInt(name, 10))
            .project_name;
      setActiveBtn(true);
    },
    handleDeleteProject: ({projects, setActiveBtn, invokedAction, selectProject}) => (
      id
    ) => () => {
      const currentProject = projects.find((item) => item.id === id);
      projects.splice(projects.indexOf(currentProject), 1);
      selectProject(id);
      setActiveBtn(true);
      invokedAction.push('delete');
      console.log('id', id)
    },
    handleProjectChange: ({
      projectNew,
      setProjectNew,
      invokedAction,
      setActiveBtn
    }) => (id) => (event) => {
      const {value, name} = event.target;
      projectNew.id = id;
      projectNew.project_name = value;
      setProjectNew(projectNew);
      if (name !== 'New project') {
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
      projectNew,
      invokedAction
    }) => (event) => {
      event.preventDefault();

      const params = {
        id: projectNew.id,
        project_name: projectNew.project_name,
        project_description: projectNew.project_name,
        project_type_id: 1,
        deleted: 'false',
        color: projectNew.color
      };

      invokedAction.forEach((item) => {
        switch (item) {
          case 'delete':
            deleteProjectApi(selectedProjectId)
            .then(() => {
              setActiveBtn(false);
            })
            .catch(() => {});
            break;
          case 'add':
            createProjectApi(params)
            .then(() => {
              setActiveBtn(false);
            })
            .catch(() => {});
            break;
          case 'edit':
            editProjectApi(params)
            .then(() => {
              setActiveBtn(false);
            })
            .catch(() => {});
            break;
          default:
        }
      });
    }
  }),
  lifecycle<IProps, {}>({
    componentDidMount() {
      this.props.getProjectsList().then((response) => {
        this.props.updateProjectsList(
          response.payload.list.filter((item) => item.deleted !== true)
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
