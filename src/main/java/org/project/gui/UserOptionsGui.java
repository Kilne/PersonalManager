package org.project.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
            saveChanges.setDisable(true);
        });
        saveChanges.setOnAction(e -> {
            if (!userNameChangeField.getText().isEmpty() &&
                    !userNameChangeField.getText().isEmpty() &&
                    !userNameChangeField.getText().isBlank() &&
                    !userNameChangeField.getText().isBlank()
            ) {
                if (!userPasswordField.getText().isBlank() &&
                        !userPasswordField.getText().isBlank()) {
                    // TODO: se c'è la password alterare pure la password
                    // ALTER USER user_name WITH PASSWORD 'new_password';
                }else {
                    // TODO: alterare username con tabelle e proprietà del database
                    // ALTER USER myuser RENAME TO newname;
                }
            } else{
                ErrorWindow errorWindow = new ErrorWindow("Your details are missing.");
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