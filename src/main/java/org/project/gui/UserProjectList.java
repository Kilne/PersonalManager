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

    private Label userName;
    private Label loggedAs;
    private Button createProject;
    private Button logout;
    private VBox vbox;
    private GridPane grid;
    private ScrollPane scrollPane;
    private BorderPane borderPane;

    private final MainWindow mainWindow;

    public UserProjectList(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public Scene init() {
        // GridPane init
        this.grid = new GridPane();
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

        this.scrollPane = new ScrollPane();
        scrollPane.setContent(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setId("scrollPane");

        //BorderPane init
        this.borderPane = new BorderPane();
        borderPane.setId("generalWindow");
        borderPane.setCenter(scrollPane);
        borderPane.setLeft(vbox);

        return new Scene(borderPane, 700, 400);
    }

    private void initStaticElements() {
        //User section and CSS
        this.loggedAs = new Label("Logged as:");
        this.userName = new Label("UserPlaceholder");
        this.userName.setId("userName");
        this.loggedAs.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #ffffff;");
        this.userName.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #ffffff;");

        //Buttons
        this.createProject = new Button("Create Project");
        this.logout = new Button("Logout");

        //Events for buttons
        this.logout.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> mainWindow
                .changeScene("Login"));
        this.createProject.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {
            //TODO: Add create project logic
        });

        //Add elements to VBox
        this.vbox.getChildren().addAll(
                this.loggedAs,
                this.userName,
                this.createProject,
                this.logout);
    }

}