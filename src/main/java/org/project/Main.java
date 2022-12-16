package org.project;

import io.github.cdimascio.dotenv.Dotenv;
import org.project.ORM.FactoryORM;
import org.project.ORM.ORMtypes;
import org.project.ORM.PersonalManagerORM;
import org.project.core.Coordinator;
import org.project.core.DatabaseFacade;
import org.project.database.MyPostgreWrapper;
import org.project.database.PostgreBuilder;
import org.project.gui.MainWindow;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();
        DatabaseFacade admin = new DatabaseFacade("localhost", 5432, "postgres", "postgres", "admin");
        Coordinator coordinator = new Coordinator();
        coordinator.setMediatorInstance(admin);
        System.out.println(coordinator.createUser("luca", "admin"));

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