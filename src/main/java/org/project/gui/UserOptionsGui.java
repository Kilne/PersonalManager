package org.project.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.project.core.DatabaseFacade;

/**
 * Current user options
 *
 * @author luca Maiuri
 */
public class UserOptionsGui {

    private final MainWindow mainGuiWindow;

    private final Stage currentWindow = new Stage();

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
        Label userLabel = new Label("Insert new username:");
        Label passwordLabel = new Label("Insert new password:");

        // Fields
        TextField userNameChangeField = new TextField();
        PasswordField userPasswordField = new PasswordField();

        userNameChangeField.setId("userNameField");
        userPasswordField.setId("userPasswordField");

        // Buttons
        Button exit = new Button("Exit");
        Button changeUsername = new Button("Change username");
        Button changePassword = new Button("Change password");
        Button killYourself = new Button("Delete your account");

        // Actions
        exit.setOnAction(e -> this.currentWindow.close());

        changeUsername.setOnAction(e -> {
            if (
                    !userNameChangeField.getText().isBlank() &&
                            !userNameChangeField.getText().isEmpty()
            ) {
                String query =
                        "ALTER USER " +
                                MainWindow.getCoordinator().getUserInstance().getCurrentUser() +
                                " RENAME TO " +
                                userNameChangeField.getText();

                String oldUser = MainWindow.getCoordinator().getUserInstance().getCurrentUser();
                UserChangePasswordPrompt userChangePasswordPrompt = new UserChangePasswordPrompt();
                userChangePasswordPrompt.display();
                String password = userChangePasswordPrompt.getPassword();

                if (password.isBlank() || password.isEmpty()) {
                    ErrorWindow errorWindow = new ErrorWindow("Password cannot be empty");
                    userNameChangeField.clear();
                    userPasswordField.clear();
                    errorWindow.show();
                } else {
                    if (
                            MainWindow.getCoordinator().getUserInstance().disconnect() &&
                                    MainWindow.getCoordinator().getMediatorInstance().userDetailsManipulation(query)
                    ) {
                        String[] dbInfo = MainWindow.getCoordinator().getMediatorInstance().getClientInfo();
                        MainWindow.getCoordinator().getUserInstance().setNewConnection(
                                dbInfo[0],
                                Integer.parseInt(dbInfo[1]),
                                MainWindow.getCoordinator().getMediatorInstance().getDatabaseName(),
                                userNameChangeField.getText(),
                                password
                        );
                        if (MainWindow.getCoordinator().getUserInstance().connect()) {
                            MainWindow.getCoordinator().getMediatorInstance().userDetailsManipulation(
                                    "ALTER TABLE " +
                                            oldUser +
                                            " RENAME TO " +
                                            userNameChangeField.getText()
                            );
                            userNameChangeField.setText("");
                            Alert success = new Alert(Alert.AlertType.INFORMATION);
                            success.setContentText("Username correctly changed.");
                            this.mainGuiWindow.setUsername();
                            this.mainGuiWindow.populateProjects();
                            success.showAndWait();
                        } else {
                            ErrorWindow errorWindow = new ErrorWindow("Cannot connect to the database.");
                            errorWindow.show();
                            this.currentWindow.close();
                            this.mainGuiWindow.close();
                        }
                    } else {
                        Alert fail = new Alert(Alert.AlertType.ERROR);
                        fail.setContentText("There was an error with changing the username");
                        userNameChangeField.setText("");
                        fail.showAndWait();
                    }
                }
            }else{
                ErrorWindow errorWindow = new ErrorWindow("Missing details.");
                errorWindow.show();
            }
        });

        changePassword.setOnAction(e->{
            if(!userPasswordField.getText().isEmpty() && !userPasswordField.getText().isBlank()){

                String query = "ALTER ROLE "+
                        MainWindow.getCoordinator().getUserInstance().getCurrentUser()+
                        " WITH PASSWORD '"+
                        userPasswordField.getText()+
                        "'";
                if(MainWindow.getCoordinator().getUserInstance().userDetailsManipulation(query)){

                    userPasswordField.setText("");
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setContentText("Password changed");
                    success.showAndWait();
                }else {
                    Alert fail = new Alert(Alert.AlertType.ERROR);
                    fail.setContentText("There was an error changing your password");
                    fail.showAndWait();
                }

            } else {
                ErrorWindow errorWindow = new ErrorWindow("Missing details.");
                errorWindow.show();
            }
        });

        killYourself.setOnAction(e->{

            DatabaseFacade mediator = MainWindow.getCoordinator().getMediatorInstance();
            DatabaseFacade userIst = MainWindow.getCoordinator().getUserInstance();
            String currentUser= MainWindow.getCoordinator().getUserInstance().getCurrentUser();

            Dialog<ButtonType> confirm = new Dialog<>();

            confirm.setContentText("Are you sure about it?");
            confirm.getDialogPane().getButtonTypes().add(ButtonType.OK);
            confirm.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            confirm.showAndWait();
            if( confirm.getResult() == ButtonType.OK ){

                if(userIst.disconnect()){

                    if( mediator.removeUserFromDatabase(currentUser)){

                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setContentText("Successfully deleted the user");
                        success.showAndWait();
                        this.currentWindow.close();
                        this.mainGuiWindow.changeScene("Login");

                    }else{
                        ErrorWindow errorWindow =
                                new ErrorWindow("Could not disconnect the user.");
                        errorWindow.show();
                        userIst.connect();
                    }

                }else{

                    ErrorWindow errorWindow=
                            new ErrorWindow("Could not disconnect the client.\n" +
                                    "Nothing has been changed.");
                    errorWindow.show();
                }

            }
        });

        // Add elements to the grid
        currentGrid.add(userLabel, 0, 0);
        currentGrid.add(userNameChangeField, 1, 0);
        currentGrid.add(changeUsername, 2, 0);
        currentGrid.add(passwordLabel, 0, 1);
        currentGrid.add(userPasswordField, 1, 1);
        currentGrid.add(changePassword, 2, 1);
        currentGrid.add(killYourself,0,2);
        currentGrid.add(exit, 2, 2);

        return new Scene(currentGrid);

    }

    public void display() {
        this.currentWindow.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        this.currentWindow.setScene(init());
        this.currentWindow.showAndWait();
    }
}