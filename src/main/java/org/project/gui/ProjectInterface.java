package org.project.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.project.ORM.PersonalManagerORM;
import org.project.core.ProjectEditorLogic;
import org.project.core.adapters.QueryType;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Single project GUI interface
 *
 * @author luca maiuri
 */
public class ProjectInterface {

    private final ProjectEditorLogic logic = new ProjectEditorLogic();
    private Stage window;
    private PersonalManagerORM orm;

    public void setProjectInterfaceORM(PersonalManagerORM orm) {
        this.orm = orm;
    }

    /**
     * Create the project interface stage and wait for user input
     */
    public void display() {
        this.window = new Stage();
        window.setTitle("Project Interface");
        window.setResizable(false);
        window.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        window.setScene(projectInterface());
        window.showAndWait();
    }

    /**
     * Create the project interface scene
     *
     * @return the project interface scene and events
     */
    private Scene projectInterface() {
        // Initialize
        Label title = new Label("Project Interface");
        Label projectName = new Label("Project Name");
        Label projectDescription = new Label("Project Description");
        Label projectDeadline = new Label("Project Deadline");
        Label target = new Label("Target");
        TextField projectNameField = new TextField();
        TextField projectDescriptionField = new TextField();
        TextField projectDeadlineField = new TextField();
        TextField targetField = new TextField();
        Label projectStepsTotal = new Label("Total Steps");
        Label projectStepsCompleted = new Label("Completed Steps");
        Label projectStepsTotalField = new Label(orm.getP_steps_number().toString());
        Label projectStepsCompletedField = new Label(orm.getP_steps_completed().toString());
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");
        Button addStepButton = new Button("Add Step");
        Button removeStepButton = new Button("Remove Step");
        GridPane projectGrid = new GridPane();


        // SET ELEMENTS AND EVENTS
        saveButton.setOnAction(e -> {
            this.logic.setProject(orm);
            Vector<Boolean> tests = new Vector<>();
            AtomicReference<Boolean> result = new AtomicReference<>(true);
            tests.add(logic.changeName(projectNameField.getText()));
            tests.add(logic.changeDescription(projectDescriptionField.getText()));
            tests.add(logic.changeDueDate(projectDeadlineField.getText()));
            tests.add(logic.changeTarget(targetField.getText()));
            tests.forEach(test -> {
                if (!test) {
                    result.set(false);
                }
            });
            if (result.get()) {
                ArrayList<PersonalManagerORM> project_to_update = new ArrayList<>();
                project_to_update.add(this.logic.getProject());
                MainWindow.getCoordinator().setUserProjects(
                        MainWindow.getCoordinator().getUserInstance().queryTheDatabase(
                                QueryType.UPDATE,
                                MainWindow.getCoordinator().getUserInstance().getCurrentUser(),
                                project_to_update
                        )
                );
                window.close();
            }else {
                ErrorWindow errorWindow = new ErrorWindow("Some parameters are not correct.");
                errorWindow.show();
            }
        });
        cancelButton.setOnAction(e -> window.close());
        addStepButton.setOnAction(e -> {
            this.logic.setProject(this.orm);
            if (this.logic.addStepCompleted()) {
                projectStepsCompletedField.setText(this.logic.getProject().getP_steps_completed().toString());
                this.setProjectInterfaceORM(this.logic.getProject());
            }
        });
        removeStepButton.setOnAction(e -> {
            this.logic.setProject(this.orm);
            if (this.logic.removeStepCompleted()) {
                projectStepsCompletedField.setText(this.logic.getProject().getP_steps_completed().toString());
                this.setProjectInterfaceORM(this.logic.getProject());
            }
        });
        projectNameField.setText(orm.getP_name());
        projectDescriptionField.setText(orm.getP_description());
        projectDeadlineField.setText(orm.getP_dueDate().toString());
        targetField.setText(orm.getP_target().toString());

        // SET GRID
        projectGrid.setAlignment(javafx.geometry.Pos.CENTER);
        projectGrid.setHgap(10);
        projectGrid.setVgap(10);
        projectGrid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        projectGrid.add(title, 0, 0, 2, 1);
        projectGrid.add(projectName, 0, 1);
        projectGrid.add(projectNameField, 1, 1);
        projectGrid.add(projectDescription, 0, 2);
        projectGrid.add(projectDescriptionField, 1, 2);
        projectGrid.add(projectDeadline, 0, 3);
        projectGrid.add(projectDeadlineField, 1, 3);
        projectGrid.add(target, 0, 4);
        projectGrid.add(targetField, 1, 4);
        projectGrid.add(projectStepsTotal, 0, 5);
        projectGrid.add(projectStepsTotalField, 1, 5);
        projectGrid.add(projectStepsCompleted, 0, 6);
        projectGrid.add(projectStepsCompletedField, 1, 6);
        projectGrid.add(saveButton, 0, 7);
        projectGrid.add(cancelButton, 1, 7);
        projectGrid.add(addStepButton, 0, 8);
        projectGrid.add(removeStepButton, 1, 8);

        return new Scene(projectGrid, 400, 400);
    }

}