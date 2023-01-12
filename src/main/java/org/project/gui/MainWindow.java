package org.project.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.project.core.Coordinator;
import org.project.core.adapters.QueryType;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Main Gui window of the application
 *
 * @author luca maiuri
 */
public class MainWindow extends Application implements Runnable {

    private static Coordinator coordinator;
    private final HashMap<String, Scene> elements = new HashMap<>();
    private Stage window;

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
        this.window.setScene(this.elements.get("Login"));
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
        MainWindow.getCoordinator().setUserProjects(
                MainWindow.getCoordinator().getUserInstance().queryTheDatabase(
                        QueryType.SELECT,
                        MainWindow.getCoordinator().getUserInstance().getCurrentUser(),
                        null
                )
        );

        GridPane scrollGrid = (GridPane) ((ScrollPane) this.window.getScene().lookup("#scrollPane")).getContent();
        if(scrollGrid.getChildren().size()!=0) {
            scrollGrid.getChildren().clear();
        }

        if (MainWindow.getCoordinator().getUserProjects().size() != 0) {
            AtomicInteger i = new AtomicInteger(0);
            this.window.getScene().getRoot().getChildrenUnmodifiable().forEach(
                    child -> {
                        if (child instanceof ScrollPane) {
                            GridPane innerGrid = (GridPane) ((ScrollPane) child).getContent();
                            MainWindow.getCoordinator().getUserProjects().forEach((id, p) -> {
                                GridPane card = new ProjectCard(this).assembleCard();
                                card.getChildren().forEach(
                                        cardElement -> {
                                            try {
                                                switch (cardElement.getId()) {
                                                    case "projectNameField" ->
                                                            ((Label) cardElement).setText(p.getP_name());
                                                    case "projectDescriptionField" ->
                                                            ((Label) cardElement).setText(p.getP_description());
                                                    case "projectDeadlineField" -> ((Label) cardElement).setText(
                                                            p.getP_dueDate().toString().split("T")[0]
                                                    );
                                                    case "targetField" -> ((Label) cardElement).setText(
                                                            p.getP_target().toString());
                                                    case "progressField" -> ((ProgressBar) cardElement).setProgress(
                                                            p.getP_progress());
                                                    case "stepsTotalField" -> ((Label) cardElement).setText(
                                                            String.valueOf(p.getP_steps_value()));
                                                    case "stepsCompletedField" -> ((Label) cardElement).setText(
                                                            String.valueOf(p.getP_steps_completed()));
                                                    case "projectID" ->
                                                            ((Label) cardElement).setText(String.valueOf(id));
                                                }
                                            } catch (NullPointerException | ClassCastException ignored) {
                                            }
                                        }
                                );
                                innerGrid.add(card, 0, i.getAndIncrement());
                            });
                        }
                    }
            );
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

    /**
     * Sets the username of the current user in the user scene.
     */
    protected void setUsername() {
        this.window.getScene().getRoot().getChildrenUnmodifiable().forEach(
                elements -> {
                    if (elements instanceof VBox vBox) {
                        vBox.getChildren().forEach(
                                vboxElement -> {
                                    if (vboxElement instanceof Label label) {
                                        if (label.getId() != null && label.getId().equals("usernameLabel")) {
                                            label.setText(MainWindow.coordinator.getUserInstance().getCurrentUser());
                                        }
                                    }
                                }
                        );
                    }
                }
        );
    }

    @Override
    public void run() {
        launch();
    }
}