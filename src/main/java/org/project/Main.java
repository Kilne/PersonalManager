package org.project;

import io.github.cdimascio.dotenv.Dotenv;
import org.project.ORM.FactoryORM;
import org.project.ORM.ORMtypes;
import org.project.ORM.PersonalManagerORM;
import org.project.core.Coordinator;
import org.project.gui.MainWindow;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();
        Coordinator coordinator = new Coordinator();
        FactoryORM factoryORM = new FactoryORM();
        factoryORM.buildORM(ORMtypes.PERSONALMANAGERORM);
        PersonalManagerORM personalManagerORM = (PersonalManagerORM) factoryORM.getORM();
        personalManagerORM.setP_id("1");
        personalManagerORM.setP_name("Project 1");
        personalManagerORM.setP_description("Description 1");
        personalManagerORM.setP_dueDate(LocalDateTime.parse("2024-01-01T00:00:00"));
        personalManagerORM.setP_progress(0.50F);
        personalManagerORM.setP_steps_number(100);
        personalManagerORM.setP_steps_completed(50);
        personalManagerORM.setP_steps_value(1.00F);
        personalManagerORM.setP_target(100.00F);

        coordinator.addProject(personalManagerORM);
        MainWindow main = new MainWindow();
        coordinator.setMainGUI(main);
        MainWindow.setCoordinator(coordinator);
        new Thread(main).start();
    }
}