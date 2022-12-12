package org.project.core;

import org.project.ORM.PersonalManagerORM;
import org.project.gui.MainWindow;

import java.util.HashMap;

public class Coordinator {

    private final HashMap<String, PersonalManagerORM> userProjects;
    private MainWindow mainGUI;
    private DatabaseFacade userInstance;
    private DatabaseFacade mediatorInstance;

    public Coordinator() {
        this.userProjects = new HashMap<>();
    }

    public void setMainGUI(MainWindow mainGUI) {
        this.mainGUI = mainGUI;
    }

    public void setUserInstance(DatabaseFacade userInstance) {
        this.userInstance = userInstance;
    }

    public void setMediatorInstance(DatabaseFacade mediatorInstance) {
        this.mediatorInstance = mediatorInstance;
    }

    public void addProject(PersonalManagerORM project) {
        this.userProjects.put(project.getP_id(), project);
    }

    public void removeProject(PersonalManagerORM project) {
        this.userProjects.remove(project.getP_id());
    }

    public void removeProject(String projectID) {
        this.userProjects.remove(projectID);
    }

    public PersonalManagerORM getProject(String projectID) {
        return this.userProjects.get(projectID);
    }


}