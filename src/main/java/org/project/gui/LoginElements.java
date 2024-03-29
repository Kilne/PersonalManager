package org.project.gui;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.project.core.DatabaseFacade;

/**
 * Login elements of the application
 *
 * @author luca maiuri
 */
public class LoginElements {

    private final MainWindow mainWindow;

    public LoginElements(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

    }

    /**
     * Create the login scene
     * @return the login scene
     */
    public Scene loginElements() {

        //INITIALIZE ELEMENTS
        Label title = new Label("Login with your credentials");
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        Label loginLabel = new Label("Username");
        Label passwordLabel = new Label("Password");
        TextField loginField = new TextField();
        PasswordField passwordField = new PasswordField();
        GridPane loginGrid = new GridPane();

        //EVENTS
        loginField.setId("loginField");
        passwordField.setId("passwordField");
        loginButton.setOnAction(e -> {
            Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().load();
            DatabaseFacade userDB = new DatabaseFacade(
                    dotenv.get("DB_HOST"),
                    Integer.parseInt(dotenv.get("DB_PORT")),
                    dotenv.get("DB_NAME"),
                    loginField.getText(),
                    passwordField.getText()
            );
            MainWindow.getCoordinator().setUserInstance(userDB);
            loginField.clear();
            passwordField.clear();
            if (MainWindow.getCoordinator().getUserInstance().connect()) {
                mainWindow.changeScene("User");
                mainWindow.setUsername();
                mainWindow.populateProjects();
            } else {
                MainWindow.getCoordinator().setUserInstance(null);
                ErrorWindow errorWindow = new ErrorWindow("Login failed, check your credentials");
                errorWindow.show();
            }
        });
        registerButton.setOnAction(e -> mainWindow
                .changeScene("Register"));

        //SET ELEMENTS
        loginGrid.setAlignment(javafx.geometry.Pos.CENTER);
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);
        loginGrid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        loginGrid.add(title, 0, 0, 2, 1);
        loginGrid.add(loginLabel, 0, 1);
        loginGrid.add(loginField, 1, 1);
        loginGrid.add(passwordLabel, 0, 2);
        loginGrid.add(passwordField, 1, 2);
        loginGrid.add(loginButton, 0, 3);
        loginGrid.add(registerButton, 1, 3);

        return new Scene(loginGrid, 300, 275);
    }


}