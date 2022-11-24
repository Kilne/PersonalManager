package org.project;


import org.project.ORM.FactoryORM;
import org.project.ORM.PersonalManagerORM;

public class Main {
    public static void main(String[] args) {

        FactoryORM factoryORM = new FactoryORM();
        factoryORM.buildORM("personalmanagerorm");
        PersonalManagerORM personalManagerORM = (PersonalManagerORM) factoryORM.getORM();
        personalManagerORM.parse("id=1;name=Luca;description=This is a description;dueDate=2024-12-30T19:34:50.630;progress=0.5;target=10000");
        System.out.println(personalManagerORM);
    }
}