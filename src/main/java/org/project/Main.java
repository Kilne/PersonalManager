package org.project;


import org.project.ORM.PersonalManagerORM;
import org.project.core.DatabaseFacade;
import org.project.core.adapters.MyPostgresDataProcess;
import org.project.core.adapters.QueryType;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        DatabaseFacade databaseFacade = new DatabaseFacade();
        databaseFacade.setNewConnection("localhost", 5432, "postgres", "postgres", "admin");
        databaseFacade.connect();
        MyPostgresDataProcess dataProcess = new MyPostgresDataProcess();
        ArrayList<PersonalManagerORM> test = new ArrayList<>();
        dataProcess.setData("Name:test3 Description:test2 Due Date:2023-11-18T00:00 Progress:23.5 Target:2000.0 Steps Number:600 Steps Completed:200 Steps Value:22.22");
        test.add(dataProcess.packData());
        dataProcess.setData("Name:test4 Description:test2 Due Date:2023-11-18T00:00 Progress:23.5 Target:2000.0 Steps Number:600 Steps Completed:200 Steps Value:22.22");
        test.add(dataProcess.packData());
        ArrayList<PersonalManagerORM> personalManagerORMS =
                databaseFacade.queryTheDatabase(QueryType.INSERT, "Test", test);
        System.out.println(personalManagerORMS);
        databaseFacade.disconnect();

    }
}