package org.project;

import org.project.core.Coordinator;
import org.project.core.DiscoverDatabase;
import org.project.gui.MainWindow;

public class Main {

    public static void main(String[] args) {
        Coordinator coordinator = new Coordinator();
        DiscoverDatabase discoverDatabase = new DiscoverDatabase();
        coordinator.setMediatorInstance(discoverDatabase.connectToLocalDatabase());
        coordinator.setMainGUI(new MainWindow());
        System.out.println(coordinator.getMediatorInstance().connect());
        coordinator.getMainGUI().run();
        System.out.println(coordinator.getMediatorInstance().disconnect());
    }
}