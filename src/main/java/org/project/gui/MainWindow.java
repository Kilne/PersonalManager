package org.project.gui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.project.ORM.PersonalManagerORM;
import org.project.core.Coordinator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;


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
        populateProjects();
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

    /**
     * Populate the projects list, clears the list and then adds the new projects if any from the coordinator.
     *
     * @see Coordinator
     */
    protected void populateProjects() {
        this.window.getScene().getRoot().getChildrenUnmodifiable().forEach(
                elements -> {
                    if (elements instanceof ScrollPane scrollPane) {
                        ((GridPane) scrollPane.getContent()).getChildren().clear();
                    }
                }
        );

        if (MainWindow.coordinator.getUserProjects().size() != 0) {
            ObservableList<Node> nodes = this.window.getScene().getRoot().getChildrenUnmodifiable();
            for (Node node : nodes) {
                if (node instanceof ScrollPane scrollPane) {
                    GridPane scrollPaneNodes = (GridPane) scrollPane.getContent();
                    MainWindow.coordinator.getUserProjects().forEach((s, personalManagerORM) -> {
                        scrollPaneNodes.add(new ProjectCard(this).assembleCard(),
                                0, scrollPaneNodes.getChildren().size() + 1);
                    });
                    ArrayList<PersonalManagerORM> userProjects = new ArrayList<>(MainWindow.coordinator.getUserProjects().values());
                    AtomicInteger i = new AtomicInteger();
                    scrollPaneNodes.getChildren().forEach(cardNode -> {
                        GridPane card = (GridPane) cardNode;
                        card.getChildren().forEach(element -> {
                            if (element.getId() != null) {
                                switch (element.getId()) {
                                    case "projectNameField" -> ((Label) element)
                                            .setText(userProjects.get(i.get()).getP_name());
                                    case "projectDescriptionField" -> ((Label) element)
                                            .setText(userProjects.get(i.get()).getP_description());
                                    case "projectDeadlineField" -> ((Label) element)
                                            .setText(userProjects.get(i.get()).getP_dueDate().toString());
                                    case "targetField" -> ((Label) element)
                                            .setText(userProjects.get(i.get()).getP_target().toString());
                                    case "stepsTotalField" -> ((Label) element)
                                            .setText(userProjects.get(i.get()).getP_steps_number().toString());
                                    case "stepsCompletedField" -> ((Label) element)
                                            .setText(userProjects.get(i.get()).getP_steps_completed().toString());
                                    case "progressField" -> ((ProgressBar) element)
                                            .setProgress(userProjects.get(i.get()).getP_progress());
                                    case "projectID" -> ((Label) element).setText(userProjects.get(i.get()).getP_id());
                                }
                            }
                        });
                        i.addAndGet(1);
                    });
                }
            }
        } else {
            this.window.getScene().getRoot().getChildrenUnmodifiable().forEach(
                    elements -> {
                        if (elements instanceof ScrollPane scrollPane) {
                            GridPane scrollPaneNodes = (GridPane) scrollPane.getContent();
                            scrollPaneNodes.add(new Label("No projects found"), 0, 0);
                        }
                    }
            );
        }
    }


    @Override
    public void run() {
        launch();
    }
}