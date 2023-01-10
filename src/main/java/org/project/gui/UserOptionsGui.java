package org.project.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Current user options
 *
 * @author luca Maiuri
 */
public class UserOptionsGui {

    private MainWindow mainGuiWindow;

    private Stage currentWindow = new Stage();

    public UserOptionsGui(MainWindow mainGuiWindow) {
        this.mainGuiWindow = mainGuiWindow;
    }

    private Scene init() {

        // Grid
        GridPane currentGrid = new GridPane();
        currentGrid.setHgap(10);
        currentGrid.setVgap(10);
        currentGrid.setAlignment(Pos.CENTER);
        currentGrid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));

        // Labels
        Label userLabel = new Label("CurrentUsername");

        // Fields
        TextField userNameChangeField = new TextField();
        PasswordField userPasswordField = new PasswordField();

        userNameChangeField.setId("userNameField");
        userNameChangeField.setEditable(false);

        userPasswordField.setId("userPasswordField");
        userPasswordField.setEditable(false);

        // Buttons
        Button editYourProfileDetails = new Button("Edit your details");
        Button exit = new Button("Exit");
        Button saveChanges = new Button("Save changes");

        saveChanges.setDisable(true);

        // Actions
        exit.setOnAction(e -> {
            this.currentWindow.close();
        });
        editYourProfileDetails.setOnAction(e -> {
            editYourProfileDetails.setDisable(true);
            userNameChangeField.setEditable(true);
            userPasswordField.setEditable(true);
            saveChanges.setDisable(false);
        });
        saveChanges.setOnAction(e -> {

            // TODO FARE PER BENE QUI GLI ALTER E I CONTROLLI

            // See if fields are empty or blank then collect
            if(!userNameChangeField.getText().isEmpty() || !userNameChangeField.getText().isBlank()){

                // Collect the new username
                String userNew = userNameChangeField.getText();

                // Now check if you need to change the password too



            } else{

                // Then open the error and interrupt the event
                ErrorWindow errorWindow = new ErrorWindow("Details are missing.");
                errorWindow.show();

            }

        });

        return new Scene(currentGrid);

}

    public void display() {
        this.currentWindow.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        this.currentWindow.setScene(init());
        this.currentWindow.showAndWait();
    }
}