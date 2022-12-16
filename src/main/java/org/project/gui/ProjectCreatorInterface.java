package org.project.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.project.ORM.PersonalManagerORM;
import org.project.core.ProjectCreatorLogic;

import java.time.LocalDateTime;

/**
 * Prject creator interface
 *
 * @author luca maiuri
 */
public class ProjectCreatorInterface {

    MainWindow mainWindow;
    Stage stage;

    public ProjectCreatorInterface(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * Create the project creator window and wait for user input
     */
    public void display() {
        this.stage = new Stage();
        stage.setTitle("Project Creator");
        stage.setResizable(false);
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.setScene(init());
        stage.showAndWait();
    }

    /**
     * Create the project creator scene
     *
     * @return the project creator scene and events
     */
    private Scene init() {

        // Static Elements
        Label P_name = new Label("Project Name");
        Label P_description = new Label("Project Description");
        Label P_deadline = new Label("Project Deadline");
        Label P_target = new Label("Project Target");

        //Input elements
        TextField P_nameField = new TextField();
        TextField P_descriptionField = new TextField();
        DatePicker P_deadlineField = new DatePicker(LocalDateTime.now().toLocalDate());
        TextField P_targetField = new TextField();

        //Buttons
        Button createButton = new Button("Create");
        Button cancelButton = new Button("Cancel");

        //Event handlers
        createButton.setOnAction(e -> {
            ProjectCreatorLogic logic = new ProjectCreatorLogic();
            if (logic.createOne(P_nameField.getText(), P_descriptionField.getText(), P_deadlineField.getValue().toString(),
                    P_targetField.getText())) {
                // TODO NON LO PUOI CREARE TU L'ID MA LO DEVI OTTENERE DAL DB PER CUI PRENDI QUESTI DATI E LI
                //  PASSI AL COORDINATOR CHE LI PASSA AL DB E RICEVI L'ID CON IL PROGETTO DOPO, IL METODO QUI VALIDA I DATI
                //  E LI PASSA
                PersonalManagerORM orm = logic.getProject();


                stage.close();
            } else {
                System.out.println("Project not created");
            }
        });
        cancelButton.setOnAction(e -> {
            this.stage.close();
        });

        //Grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        //Add elements to grid
        grid.add(P_name, 0, 0);
        grid.add(P_nameField, 1, 0);
        grid.add(P_description, 0, 1);
        grid.add(P_descriptionField, 1, 1);
        grid.add(P_deadline, 0, 2);
        grid.add(P_deadlineField, 1, 2);
        grid.add(P_target, 0, 3);
        grid.add(P_targetField, 1, 3);
        grid.add(createButton, 0, 4);
        grid.add(cancelButton, 1, 4);

        return new Scene(grid, 300, 275);
    }

}