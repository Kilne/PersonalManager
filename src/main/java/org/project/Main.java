package org.project;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Application;
import org.project.gui.MainWindow;

public class Main {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();
        Application.launch(MainWindow.class, args);
    }
}