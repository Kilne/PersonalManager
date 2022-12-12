package org.project;

import io.github.cdimascio.dotenv.Dotenv;
import org.project.core.Coordinator;
import org.project.gui.MainWindow;

public class Main {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();
        Coordinator coordinator = new Coordinator();
        MainWindow main = new MainWindow();
        coordinator.setMainGUI(main);
        MainWindow.setCoordinator(coordinator);
        new Thread(main).start();
    }
}