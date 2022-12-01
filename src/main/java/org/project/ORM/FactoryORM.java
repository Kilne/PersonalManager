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

    /**
     * Build a specific ORM class.
     * <p></p>
     * The ORM class is built according to the type passed as parameter.
     *
     * @param ormName The name of the ORM class to build.
     * @see ORMtypes
     */
    public void buildORM(ORMtypes ormName) {
        if (ormName == ORMtypes.PERSONALMANAGERORM) {
            this.orm = new PersonalManagerORM();
        } else {
            this.orm = null;
        }
    }
}