module org.project {
    requires javafx.controls;
    requires io.github.cdimascio.dotenv.java;
    requires java.sql;
    exports org.project.gui;
    exports org.project.ORM;
    exports org.project.core;
    exports org.project.core.adapters;
}