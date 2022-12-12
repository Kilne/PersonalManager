package org.project.gui;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.project.core.Coordinator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Main Gui window of the application
 *
 * @author luca maiuri
 */
public class MainWindow extends Application implements Runnable {

    private Stage window;
    private static Coordinator coordinator;
    private final HashMap<String, Scene> elements = new HashMap<>();

    public static Coordinator getCoordinator() {
        return coordinator;
    }

    public static void setCoordinator(Coordinator coordinator) {
        MainWindow.coordinator = coordinator;
    }

    /**
     * Main window of the application elements
     */
    private void loadElements() {
        this.elements.put("Login", new LoginElements(this).loginElements());
        this.elements.put("Register", new RegisterElements(this).getRegisterGrid());
        this.elements.put("User", new UserProjectList(this).init());
    }

    @Override
    public void start(Stage stage) {
        this.window = stage;
        loadElements();
        this.window.setTitle("Personal Manager");
        this.window.setScene(this.elements.get("User"));
        addCard();
        this.window.show();
    }

    /**
     * Change the scene of the main window
     *
     * @param sceneName the name of the scene to change to.
     */
    protected void changeScene(String sceneName) {
        this.window.setScene(this.elements.get(sceneName));
    }

    protected void addCard() {
        //TODO: Add card logic
        for (Node node1 : this.window.getScene().getRoot().getChildrenUnmodifiable()) {
            if (Objects.equals(node1.getId(), "scrollPane")) {
                Node node = ((ScrollPane) node1).getContent();
                if (Objects.equals(node.getId(), "userProjectList")) {
                    GridPane gridPane = (GridPane) node;
                    ArrayList<Node> nodes = new ArrayList<>(gridPane.getChildren());

                    nodes.add(new ProjectCard(this).assembleCard());
                    nodes.add(new ProjectCard(this).assembleCard());
                    nodes.add(new ProjectCard(this).assembleCard());
                    nodes.add(new ProjectCard(this).assembleCard());
                    gridPane.getChildren().clear();
                    for (int i = 0; i < nodes.size(); i++) {
                        gridPane.addRow(i, nodes.get(i));
                    }
                }
            }
        }
    }


    @Override
    public void run() {
        launch();
    }
}