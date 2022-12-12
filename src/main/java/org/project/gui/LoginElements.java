package org.project.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Login elements of the application
 *
 * @author luca maiuri
 */
public class LoginElements {

    private Label title;
    private Button loginButton;
    private Button registerButton;
    private Label loginLabel;
    private Label passwordLabel;
    private TextField loginField;
    private PasswordField passwordField;
    private final MainWindow mainWindow;
    private GridPane loginGrid;

    public LoginElements(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

    }

    /**
     * Create the login scene
     * @return the login scene
     */
    public Scene loginElements() {

        //INITIALIZE ELEMENTS
        this.title = new Label("Login with your credentials");
        this.loginButton = new Button("Login");
        this.registerButton = new Button("Register");
        this.loginLabel = new Label("Username");
        this.passwordLabel = new Label("Password");
        this.loginField = new TextField();
        this.passwordField = new PasswordField();
        this.loginGrid = new GridPane();

        //EVENTS
        this.loginField.setId("loginField");
        this.passwordField.setId("passwordField");
        this.loginButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {
            //TODO: Add login logic
        });
        this.registerButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> mainWindow
                .changeScene("Register"));

        //SET ELEMENTS
        this.loginGrid.setAlignment(javafx.geometry.Pos.CENTER);
        this.loginGrid.setHgap(10);
        this.loginGrid.setVgap(10);
        this.loginGrid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        this.loginGrid.add(this.title, 0, 0, 2, 1);
        this.loginGrid.add(this.loginLabel, 0, 1);
        this.loginGrid.add(this.loginField, 1, 1);
        this.loginGrid.add(this.passwordLabel, 0, 2);
        this.loginGrid.add(this.passwordField, 1, 2);
        this.loginGrid.add(this.loginButton, 0, 3);
        this.loginGrid.add(this.registerButton, 1, 3);

        return new Scene(this.loginGrid, 300, 275);
    }


}