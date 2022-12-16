package org.project;

import io.github.cdimascio.dotenv.Dotenv;
import org.project.core.Coordinator;
import org.project.core.DatabaseFacade;

public class Main {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();
        Coordinator coordinator = new Coordinator();
        DatabaseFacade databaseFacade = new DatabaseFacade("localhost", 5432, "postgres", "postgres", "admin");
        coordinator.setMediatorInstance(databaseFacade);
        System.out.println(coordinator.getMediatorInstance().connect());
        System.out.println(coordinator.getMediatorInstance().getClientInfo());
        System.out.println(coordinator.getMediatorInstance().disconnect());




    }
}