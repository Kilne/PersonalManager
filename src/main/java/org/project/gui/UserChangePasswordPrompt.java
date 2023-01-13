package org.project.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Dialog for username change
 *
 * @author luca maiuri
 */
public class UserChangePasswordPrompt {

    String password = "";

    public void display() {
        Stage window = new Stage();
        window.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        window.setTitle("Change Password");
        window.setMinWidth(250);
        window.setMinHeight(250);
        window.setResizable(false);
        window.setOnCloseRequest(e -> {
            e.consume();
            window.close();
        });
        Label label = new Label("Insert your password");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button confirmButton = new Button("Confirm");
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> window.close());
        confirmButton.setOnAction(e -> {
            this.password = passwordField.getText();
            window.close();
        });
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, passwordField, confirmButton, cancelButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public String getPassword() {
        return password;
    }
}