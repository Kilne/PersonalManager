package org.project.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;

public class ProjectCard {

    private GridPane projectCard;

    private Label projectName;
    private Label projectDescription;
    private Label projectDeadline;
    private Label target;
    private Label progress;
    private Label stepsTotal;
    private Label stepsCompleted;
    private Label projectID;

    private Label projectNameField;
    private Label projectDescriptionField;
    private Label projectDeadlineField;
    private Label targetField;
    private ProgressBar progressField;
    private Label stepsTotalField;
    private Label stepsCompletedField;

    private Button editButton;
    private Button deleteButton;

    private final MainWindow mainWindow;

    public ProjectCard(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public GridPane assembleCard() {

        //Grid
        projectCard = new GridPane();
        projectCard.setHgap(10);
        projectCard.setVgap(10);
        projectCard.setAlignment(javafx.geometry.Pos.CENTER);


        //Elements and IDs
        projectName = new Label("Project Name");
        projectDescription = new Label("Project Description");
        projectDeadline = new Label("Project Deadline");
        target = new Label("Target");
        progress = new Label("Progress");
        stepsTotal = new Label("Steps Total");
        stepsCompleted = new Label("Steps Completed");
        projectID = new Label("Project ID");
        projectCard.setId("projectCard");
        projectID.setVisible(false);

        projectNameField = new Label("Project Name Field");
        projectDescriptionField = new Label("Project Description Field");
        projectDeadlineField = new Label("Project Deadline Field");
        targetField = new Label("Target Field");
        progressField = new ProgressBar(0);
        stepsTotalField = new Label("Steps Total Field");
        stepsCompletedField = new Label("Steps Completed Field");

        projectNameField.setId("projectNameField");
        projectDescriptionField.setId("projectDescriptionField");
        projectDeadlineField.setId("projectDeadlineField");
        targetField.setId("targetField");
        progressField.setId("progressField");
        stepsTotalField.setId("stepsTotalField");
        stepsCompletedField.setId("stepsCompletedField");

        deleteButton = new Button("Delete");
        deleteButton.setId("deleteButton");
        deleteButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {
            //TODO: Add delete logic
        });
        editButton = new Button("Edit");
        editButton.setId("editButton");
        editButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {
            //TODO: Add edit logic
        });

        //Add elements to grid
        projectCard.add(projectName, 0, 0);
        projectCard.add(projectDescription, 1, 0);
        projectCard.add(projectDeadline, 2, 0);
        projectCard.add(target, 3, 0);
        projectCard.add(progress, 4, 0);
        projectCard.add(stepsTotal, 5, 0);
        projectCard.add(stepsCompleted, 6, 0);

        projectCard.add(projectNameField, 0, 1);
        projectCard.add(projectDescriptionField, 1, 1);
        projectCard.add(projectDeadlineField, 2, 1);
        projectCard.add(targetField, 3, 1);
        projectCard.add(progressField, 4, 1);
        projectCard.add(stepsTotalField, 5, 1);
        projectCard.add(stepsCompletedField, 6, 1);

        projectCard.add(editButton, 5, 2);
        projectCard.add(deleteButton, 6, 2);
        projectCard.add(projectID, 4, 2);

        //CSS
        projectCard.setStyle("-fx-background-color: #adadad; -fx-border-color: #000000; -fx-border-width: 1px;");

        return projectCard;
    }

}