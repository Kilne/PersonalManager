package org.project.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class RegisterElements {

    private final Button registerButton = new Button("Register");
    private final Button CancelButton = new Button("Cancel");
    private final Label loginLabel = new Label("Username");
    private final Label passwordLabel = new Label("Password");

    private final TextField loginField = new TextField();
    private final TextField passwordField = new TextField();

    private final GridPane registerGrid = new GridPane();

    public GridPane getRegisterGrid() {
        this.registerGrid.setAlignment(javafx.geometry.Pos.CENTER);
        this.registerGrid.setHgap(10);
        this.registerGrid.setVgap(10);
        this.registerGrid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        this.registerGrid.add(this.loginLabel, 0, 1);
        this.registerGrid.add(this.loginField, 1, 1);
        this.registerGrid.add(this.passwordLabel, 0, 2);
        this.registerGrid.add(this.passwordField, 1, 2);
        this.registerGrid.add(this.registerButton, 0, 3);
        this.registerGrid.add(this.CancelButton, 1, 3);
        return this.registerGrid;
    }
}