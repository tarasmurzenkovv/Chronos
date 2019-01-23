import * as React from 'react';
import {
  Button,
  FormControl,
  IconButton,
  MenuItem,
  OutlinedInput,
  Select,
  TextField,
  Typography,
  withStyles,
  WithStyles,
  InputLabel
} from '@material-ui/core';

import AddIcon from '@material-ui/icons/Add';
import RemoveCircleOutlineIcon from '@material-ui/icons/RemoveCircleOutline';
import {IListItem} from '../../modules/modals/reducers/projects';
import * as theme from './Settings.scss';
import styles from './styles';

interface IProps extends WithStyles<typeof styles> {
  projects: IListItem[];
  color: {id: string};
  colorsSet: string[];
  handleAddProjectBtnClick(): void;
  handleColorChange: (id: number) => (id: React.SyntheticEvent) => void;
  handleDeleteProject: (id: number) => (id: React.SyntheticEvent) => void;
  handleFormSubmit(): void;
  handleProjectChange: (id: number) => (id: React.SyntheticEvent) => void;
}

const Settings: React.FunctionComponent<IProps> = ({
  classes,
  projects,
  color,
  colorsSet,
  handleAddProjectBtnClick,
  handleColorChange,
  handleDeleteProject,
  handleFormSubmit,
  handleProjectChange
}) => (
  <div>
    <Typography variant="h6" color="inherit" className={classes.navTitle}>
      Projects:
    </Typography>
    <form onSubmit={handleFormSubmit} className={classes.formWrapper}>
      {projects.map((project) => (
        <div key={project.id} className={theme.formControlWrapper}>
          <FormControl
            variant="outlined"
            className={`${classes.formControl} ${project.deleted &&
              classes.formControlDeleted}`}
          >
            {!project.deleted && (
              <IconButton
                color="primary"
                aria-label="Menu"
                onClick={handleDeleteProject(project.id)}
                className={classes.deleteBtn}
              >
                <RemoveCircleOutlineIcon />
              </IconButton>
            )}
            <TextField
              id="project"
              name="project"
              label="Project"
              margin="normal"
              variant="outlined"
              type="string"
              value={project.project_name}
              className={classes.inputText}
              InputProps={{
                readOnly: project.deleted && true,
                classes: {
                  notchedOutline: classes.textFieldFocusedNotchedOutline
                }
              }}
              onChange={handleProjectChange(project.id)}
            />
          </FormControl>
          <FormControl className={classes.formControlSelect}>
            <InputLabel
              htmlFor="color"
              className={`${
                project.deleted ? classes.labelTextDeleted : classes.labelText
              }`}
            >
              Color
            </InputLabel>

            {!project.deleted ? (
              <Select
                value={
                  Object.keys(color).length === 0
                    ? project.color
                    : color[`color-${project.id}`] !== undefined
                      ? color[`color-${project.id}`]
                      : '#8bc34a'
                }
                onChange={handleColorChange(project.id)}
                className={classes.selectColor}
                renderValue={() => (
                  <div
                    className={theme.colorOption}
                    style={{
                      backgroundColor: `${
                        Object.keys(color).length === 0
                          ? project.color
                          : color[`color-${project.id}`] !== undefined
                            ? color[`color-${project.id}`]
                            : '#8bc34a'
                      }`
                    }}
                  />
                )}
                input={
                  <OutlinedInput
                    labelWidth={50}
                    name={`color-${project.id}`}
                    id="color"
                    classes={{
                      notchedOutline: classes.textFieldFocusedNotchedOutline
                    }}
                  />
                }
              >
                {colorsSet.map((item) => (
                  <MenuItem
                    key={Math.random()}
                    value={item}
                    className={classes.selectItemColor}
                  >
                    <div
                      className={theme.colorOption}
                      style={{
                        backgroundColor: `${item}`
                      }}
                    />
                  </MenuItem>
                ))}
              </Select>
            ) : (
              <TextField
                id="color"
                name="color"
                margin="normal"
                variant="outlined"
                type="string"
                value={project.color}
                className={`${classes.inputText} ${project.deleted &&
                  classes.inputTextDeleted}`}
                InputProps={{
                  readOnly: true,
                  classes: {
                    root: classes.deletedText,
                    notchedOutline: classes.textFieldFocusedNotchedOutline,
                    inputAdornedStart: classes.deletedBox
                  },
                  startAdornment: (
                    <div
                      className={theme.colorOption}
                      style={{
                        backgroundColor: `${project.color}`
                      }}
                    />
                  )
                }}
              />
            )}
          </FormControl>
        </div>
      ))}
      <div className={theme.btnWrapper}>
        <Button
          type="button"
          color="primary"
          className={classes.controlBtn}
          onClick={handleAddProjectBtnClick}
        >
          Add new project
          <AddIcon className={classes.rightIcon} />
        </Button>
        <Button
          type="submit"
          color="primary"
          className={`${classes.controlBtn} ${classes.saveBtnDisabled}`}
        >
          Save
        </Button>
      </div>
    </form>
  </div>
);

export default withStyles(styles)(Settings);
