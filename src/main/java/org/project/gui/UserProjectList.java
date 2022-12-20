package org.project.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class UserProjectList {

    private VBox vbox;

    private final MainWindow mainWindow;

    public UserProjectList(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * Create the user project list scene
     *
     * @return the user project list scene
     */
    public Scene init() {
        // GridPane init
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        grid.setId("userProjectList");

        //VBox init
        this.vbox = new VBox();
        this.vbox.setAlignment(Pos.CENTER);
        this.vbox.setSpacing(10);
        this.vbox.setBackground(javafx.scene.layout.Background.fill(javafx.scene.paint.Color.gray(0.4)));
        this.vbox.setBorder(
                new javafx.scene.layout.Border(
                        new javafx.scene.layout.BorderStroke(
                                javafx.scene.paint.Color.BLACK,
                                javafx.scene.layout.BorderStrokeStyle.SOLID,
                                new javafx.scene.layout.CornerRadii(5),
                                new javafx.scene.layout.BorderWidths(2)
                        )
                )
        );
        this.vbox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        //Init elements and events
        this.initStaticElements();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setId("scrollPane");

        //BorderPane init
        BorderPane borderPane = new BorderPane();
        borderPane.setId("generalWindow");
        borderPane.setCenter(scrollPane);
        borderPane.setLeft(vbox);

        return new Scene(borderPane, 900, 400);
    }

    /**
     * Initialize static elements of the scene and events
     */
    private void initStaticElements() {
        //User section and CSS
        Label loggedAs = new Label("Logged as:");
        Label userName = new Label("userPlaceHolder");
        userName.setId("usernameLabel");
        loggedAs.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #ffffff;");
        userName.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #ffffff;");

        //Buttons
        Button createProject = new Button("Create Project");
        Button logout = new Button("Logout");
        Button userOptions = new Button("User Options");

        //Events for buttons
        logout.setOnAction(e -> {
            mainWindow.changeScene("Login");
            MainWindow.getCoordinator().getUserInstance().disconnect();
            MainWindow.getCoordinator().setUserInstance(null);
        });
        createProject.setOnAction(e -> {
            ProjectCreatorInterface projectCreatorInterface = new ProjectCreatorInterface(mainWindow);
            projectCreatorInterface.display();
            mainWindow.populateProjects();
        });
        userOptions.setOnAction(e -> {
            // TODO fare le opzioni di un user, delete e cosi via.
        });

        //Add elements to VBox
        this.vbox.getChildren().addAll(
                loggedAs,
                userName,
                createProject,
                logout,
                userOptions);
    }

}