package org.project;


import org.project.database.MyPostgreWrapper;
import org.project.database.PostgreBuilder;

public class Main {
    public static void main(String[] args) {

        PostgreBuilder postgreBuilder = new PostgreBuilder();
        postgreBuilder.withUser("postgres");
        postgreBuilder.withPassword("admin");
        MyPostgreWrapper postgreWrapper = postgreBuilder.build();
        postgreWrapper.connect();
        postgreWrapper.disconnect();
    }
}