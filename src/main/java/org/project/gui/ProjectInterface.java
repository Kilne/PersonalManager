package org.project.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.project.ORM.PersonalManagerORM;

/**
 * Single project GUI interface
 *
 * @author luca maiuri
 */
public class ProjectInterface {

    private final Stage window = new Stage();
    private Label title;
    private GridPane projectGrid;
    private Label projectName;
    private Label projectDescription;
    private Label projectDeadline;
    private Label target;
    private Label progress;
    private Label stepsTotal;
    private Label stepsCompleted;
    private Label stepsValue;
    private TextField projectNameField;
    private TextField projectDescriptionField;
    private TextField projectDeadlineField;
    private TextField targetField;
    private TextField progressField;
    private TextField stepsTotalField;
    private TextField stepsCompletedField;
    private TextField stepsValueField;
    private Button saveButton;
    private Button cancelButton;

    public void setProjectInterface(PersonalManagerORM orm) {
        this.projectNameField.setText(orm.getP_name());
        this.projectDescriptionField.setText(orm.getP_description());
        this.projectDeadlineField.setText(orm.getP_dueDate().toString());
        this.targetField.setText(orm.getP_target().toString());
        this.progressField.setText(orm.getP_progress().toString());
        this.stepsTotalField.setText(orm.getP_steps_number().toString());
        this.stepsCompletedField.setText(orm.getP_steps_completed().toString());
        this.stepsValueField.setText(orm.getP_steps_value().toString());
    }


    public void display() {
        window.setTitle("Project Interface");
        window.setResizable(false);
        window.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        window.setScene(projectInterface());
        window.showAndWait();

    }

    public Scene projectInterface() {
        // Initialize
        this.title = new Label("Project Interface");
        this.projectName = new Label("Project Name");
        this.projectDescription = new Label("Project Description");
        this.projectDeadline = new Label("Project Deadline");
        this.target = new Label("Target");
        this.progress = new Label("Progress");
        this.stepsTotal = new Label("Steps Total");
        this.stepsCompleted = new Label("Steps Completed");
        this.stepsValue = new Label("Steps Value");
        this.projectNameField = new TextField();
        this.projectDescriptionField = new TextField();
        this.projectDeadlineField = new TextField();
        this.targetField = new TextField();
        this.progressField = new TextField();
        this.stepsTotalField = new TextField();
        this.stepsCompletedField = new TextField();
        this.stepsValueField = new TextField();
        this.saveButton = new Button("Save");
        this.cancelButton = new Button("Cancel");
        this.projectGrid = new GridPane();


        // SET ELEMENTS
        this.saveButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {
            //TODO: Add save logic with business logic
        });
        this.cancelButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {
            window.close();
        });

        // SET GRID
        this.projectGrid.setAlignment(javafx.geometry.Pos.CENTER);
        this.projectGrid.setHgap(10);
        this.projectGrid.setVgap(10);
        this.projectGrid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        this.projectGrid.add(this.title, 0, 0, 2, 1);
        this.projectGrid.add(this.projectName, 0, 1);
        this.projectGrid.add(this.projectNameField, 1, 1);
        this.projectGrid.add(this.projectDescription, 0, 2);
        this.projectGrid.add(this.projectDescriptionField, 1, 2);
        this.projectGrid.add(this.projectDeadline, 0, 3);
        this.projectGrid.add(this.projectDeadlineField, 1, 3);
        this.projectGrid.add(this.target, 0, 4);
        this.projectGrid.add(this.targetField, 1, 4);
        this.projectGrid.add(this.progress, 0, 5);
        this.projectGrid.add(this.progressField, 1, 5);
        this.projectGrid.add(this.stepsTotal, 0, 6);
        this.projectGrid.add(this.stepsTotalField, 1, 6);
        this.projectGrid.add(this.stepsCompleted, 0, 7);
        this.projectGrid.add(this.stepsCompletedField, 1, 7);
        this.projectGrid.add(this.stepsValue, 0, 8);
        this.projectGrid.add(this.stepsValueField, 1, 8);
        this.projectGrid.add(this.saveButton, 0, 9);
        this.projectGrid.add(this.cancelButton, 1, 9);


        return new Scene(this.projectGrid, 400, 400);
    }

}