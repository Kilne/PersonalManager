package org.project.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;

public class ProjectCard {

    private final MainWindow mainWindow;

    public ProjectCard(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public GridPane assembleCard() {

        //Grid
        GridPane projectCard = new GridPane();
        projectCard.setHgap(10);
        projectCard.setVgap(10);
        projectCard.setAlignment(javafx.geometry.Pos.CENTER);


        //Elements and IDs
        Label projectName = new Label("Project Name");
        Label projectDescription = new Label("Project Description");
        Label projectDeadline = new Label("Project Deadline");
        Label target = new Label("Target");
        Label progress = new Label("Progress");
        Label stepsTotal = new Label("Steps Total");
        Label stepsCompleted = new Label("Steps Completed");
        Label projectID = new Label("Project ID");
        projectCard.setId("projectCard");
        projectID.setVisible(false);
        projectID.setId("projectID");

        Label projectNameField = new Label("Project Name Field");
        Label projectDescriptionField = new Label("Project Description Field");
        Label projectDeadlineField = new Label("Project Deadline Field");
        Label targetField = new Label("Target Field");
        ProgressBar progressField = new ProgressBar(0);
        Label stepsTotalField = new Label("Steps Total Field");
        Label stepsCompletedField = new Label("Steps Completed Field");

        projectNameField.setId("projectNameField");
        projectDescriptionField.setId("projectDescriptionField");
        projectDeadlineField.setId("projectDeadlineField");
        targetField.setId("targetField");
        progressField.setId("progressField");
        stepsTotalField.setId("stepsTotalField");
        stepsCompletedField.setId("stepsCompletedField");

        // Buttons and events
        Button deleteButton = new Button("Delete");
        deleteButton.setId("deleteButton");
        deleteButton.setOnAction(e -> {
            MainWindow.getCoordinator().removeProject(projectID.getText());
            mainWindow.populateProjects();
        });
        Button editButton = new Button("Edit");
        editButton.setId("editButton");
        editButton.setOnAction(e -> {
            ProjectInterface projectInterface = new ProjectInterface();
            projectInterface.setProjectInterfaceORM(MainWindow
                    .getCoordinator().getProject(projectID.getText()));
            projectInterface.display();
            mainWindow.populateProjects();
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