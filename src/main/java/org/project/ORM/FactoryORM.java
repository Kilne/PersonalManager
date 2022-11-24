package org.project.ORM;

/**
 * Factory Class for ORM classes.
 *
 * @author Luca Maiuri
 */
@SuppressWarnings("unused")
public class FactoryORM {
    private CommonORM orm;

    public FactoryORM() {
        this.orm = null;
    }

    public CommonORM getORM() {
        return this.orm;
    }

    public void buildORM(String ormName) {
        if ("personalmanagerorm".equalsIgnoreCase(ormName)) {
            this.orm = new PersonalManagerORM();
        } else {
            this.orm = null;
        }
    }
}