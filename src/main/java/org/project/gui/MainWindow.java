package org.project.gui;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Vector;

public class MainWindow extends Application implements Runnable {

    private final Vector<Node> WindowElements = new Vector<>();
    private Stage window;
    private final Boolean loggedIn = false;
    private final Vector<Node> currentElements = new Vector<>();

    public MainWindow() {
        this.window = new Stage();
        this.WindowElements.add(new LoginElements().loginElements());
        this.WindowElements.add(new RegisterElements().getRegisterGrid());
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        window = stage;
        window.setTitle("Personal Project Manager");
        window.setScene(new javafx.scene.Scene((GridPane) this.WindowElements.get(0)));
        syncElements();
        window.setOnCloseRequest(e -> {
            e.consume();
            window.close();
        });

        System.out.println("Window started");
        System.out.println(this.currentElements);

        window.show();
    }

    private void changeScene(int index) {
        window.setScene(new javafx.scene.Scene((GridPane) this.WindowElements.get(index)));
    }

    private void syncElements() {
        this.currentElements.clear();
        this.currentElements.addAll(this.window.getScene().getRoot().getChildrenUnmodifiable());
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        launch();
    }
}