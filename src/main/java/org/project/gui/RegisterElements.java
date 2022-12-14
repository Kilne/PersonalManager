package org.project.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Register elements of the application
 *
 * @author luca maiuri
 */
public class RegisterElements {

    private final MainWindow mainWindow;

    public RegisterElements(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * Create the register scene
     * @return the register scene
     */
    public Scene getRegisterGrid() {
        //INITIALIZE ELEMENTS
        Label title = new Label("Register with your credentials");
        Button registerButton = new Button("Register");
        Button cancelButton = new Button("Cancel");
        Label loginLabel = new Label("Username");
        Label passwordLabel = new Label("Password");
        TextField loginField = new TextField();
        TextField passwordField = new TextField();
        GridPane registerGrid = new GridPane();

        // SET ELEMENTS
        loginField.setId("loginField");
        passwordField.setId("passwordField");
        registerButton.setOnAction(e -> {
            //TODO: Add register logic
        });
        cancelButton.setOnAction(e -> mainWindow
                .changeScene("Login"));

        // GRID
        registerGrid.setAlignment(javafx.geometry.Pos.CENTER);
        registerGrid.setHgap(10);
        registerGrid.setVgap(10);
        registerGrid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        registerGrid.add(title, 0, 0, 2, 1);
        registerGrid.add(loginLabel, 0, 1);
        registerGrid.add(loginField, 1, 1);
        registerGrid.add(passwordLabel, 0, 2);
        registerGrid.add(passwordField, 1, 2);
        registerGrid.add(registerButton, 0, 3);
        registerGrid.add(cancelButton, 1, 3);

        return new Scene(registerGrid, 300, 275);
    }
}