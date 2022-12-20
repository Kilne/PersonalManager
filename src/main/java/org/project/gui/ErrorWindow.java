package org.project.gui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Error message window generic class
 * @author luca maiuri
 */
public class ErrorWindow {

    String errorMessage;

    Stage errorWindow = new Stage();

    /**
     * Sets the error message to show.
     * @param errorMessage the message
     */
    public ErrorWindow(String errorMessage){
        this.errorMessage = errorMessage;
    }

    /**
     * Inits the window/stage elements and events
     * @return The scene
     */
    private Scene init() {
        // STATIC ELEMENTS
        GridPane pane = new GridPane();
        Label message = new Label();
        message.setText(Objects.requireNonNullElse(this.errorMessage, "Random error"));
        pane.setAlignment(Pos.CENTER);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        pane.add(message, 0, 0);
        pane.getColumnConstraints().add(columnConstraints);


        //BUTTON AND EVENTS
        Button btn = new Button("Close");
        btn.setOnAction(e -> this.errorWindow.close());
        pane.add(btn, 0, 1);

        //SCENE
        return new Scene(pane);
    }

    /**
     * Shows the windows and waits for user.
     */
    public void show(){
        this.errorWindow.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        this.errorWindow.setScene(init());
        this.errorWindow.showAndWait();
    }
}