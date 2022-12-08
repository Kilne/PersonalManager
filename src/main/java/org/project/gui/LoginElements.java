package org.project.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginElements {

    Button loginButton = new Button("Login");
    Button registerButton = new Button("Register");
    Label loginLabel = new Label("Username");
    Label passwordLabel = new Label("Password");
    TextField loginField = new TextField();
    TextField passwordField = new TextField();

    GridPane loginGrid = new GridPane();

    public GridPane loginElements() {
        this.loginGrid.setAlignment(javafx.geometry.Pos.CENTER);
        this.loginGrid.setHgap(10);
        this.loginGrid.setVgap(10);
        this.loginGrid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        this.loginGrid.add(this.loginLabel, 0, 1);
        this.loginGrid.add(this.loginField, 1, 1);
        this.loginGrid.add(this.passwordLabel, 0, 2);
        this.loginGrid.add(this.passwordField, 1, 2);
        this.loginGrid.add(this.loginButton, 0, 3);
        this.loginGrid.add(this.registerButton, 1, 3);
        return this.loginGrid;
    }


}