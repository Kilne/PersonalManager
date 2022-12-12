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

    private Label title;
    private Button registerButton;
    private Button CancelButton;
    private Label loginLabel;
    private Label passwordLabel;
    private final MainWindow mainWindow;
    private TextField loginField;
    private TextField passwordField;

    private GridPane registerGrid;

    public RegisterElements(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * Create the register scene
     * @return the register scene
     */
    public Scene getRegisterGrid() {
        //INITIALIZE ELEMENTS
        this.title = new Label("Register with your credentials");
        this.registerButton = new Button("Register");
        this.CancelButton = new Button("Cancel");
        this.loginLabel = new Label("Username");
        this.passwordLabel = new Label("Password");
        this.loginField = new TextField();
        this.passwordField = new TextField();
        this.registerGrid = new GridPane();

        // SET ELEMENTS
        this.loginField.setId("loginField");
        this.passwordField.setId("passwordField");
        this.registerButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {
            //TODO: Add register logic
        });
        this.CancelButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> mainWindow
                .changeScene("Login"));

        // GRID
        this.registerGrid.setAlignment(javafx.geometry.Pos.CENTER);
        this.registerGrid.setHgap(10);
        this.registerGrid.setVgap(10);
        this.registerGrid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        this.registerGrid.add(this.title, 0, 0, 2, 1);
        this.registerGrid.add(this.loginLabel, 0, 1);
        this.registerGrid.add(this.loginField, 1, 1);
        this.registerGrid.add(this.passwordLabel, 0, 2);
        this.registerGrid.add(this.passwordField, 1, 2);
        this.registerGrid.add(this.registerButton, 0, 3);
        this.registerGrid.add(this.CancelButton, 1, 3);

        return new Scene(this.registerGrid, 300, 275);
    }
}