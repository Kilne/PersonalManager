package org.project;

import io.github.cdimascio.dotenv.Dotenv;
import org.project.core.Coordinator;
import org.project.core.DiscoverDatabase;
import org.project.gui.MainWindow;

public class Main {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();
        Coordinator coordinator = new Coordinator();
        DiscoverDatabase discoverDatabase = new DiscoverDatabase();
        coordinator.setMediatorInstance(discoverDatabase.connectToLocalDatabase());
        coordinator.setMainGUI(new MainWindow());
        System.out.println(coordinator.getMediatorInstance().connect());
        coordinator.getMainGUI().run();
        System.out.println(coordinator.getMediatorInstance().disconnect());
    }
}