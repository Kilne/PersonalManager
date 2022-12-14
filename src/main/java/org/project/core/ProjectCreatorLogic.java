package org.project.core;

import org.project.ORM.FactoryORM;
import org.project.ORM.ORMtypes;
import org.project.ORM.PersonalManagerORM;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ProjectCreatorLogic {

    PersonalManagerORM project;

    public ProjectCreatorLogic() {
        FactoryORM factoryORM = new FactoryORM();
        factoryORM.buildORM(ORMtypes.PERSONALMANAGERORM);
        this.project = (PersonalManagerORM) factoryORM.getORM();
    }

    public PersonalManagerORM getProject() {
        return this.project;
    }

    /**
     * This method is used to create and set the project's name and params
     *
     * @param name
     * @param des
     * @param dueDate
     * @param target
     * @return
     */
    public boolean createOne(String name, String des, String dueDate, String target) {

        if (!name.isBlank() && !des.isBlank() && !dueDate.isBlank() && !target.isBlank()
                && !name.isEmpty() && !des.isEmpty() && !dueDate.isEmpty() && !target.isEmpty()) {
            try {
                LocalDateTime date = LocalDateTime.parse(dueDate + "T00:00:00");
                if (date.isAfter(LocalDateTime.now())) {
                    this.project.setP_name(name);
                    this.project.setP_description(des);
                    this.project.setP_dueDate(date);
                    float targetFloat = Float.parseFloat(target);
                    if (targetFloat > 0) {
                        this.project.setP_target(targetFloat);
                    } else {
                        return false;
                    }
                    int stepsTot = (int) this.project.getP_dueDate().until(LocalDateTime.now(), ChronoUnit.DAYS);
                    this.project.setP_steps_number(stepsTot);
                    this.project.setP_steps_value(this.project.getP_target() / stepsTot);
                    this.project.setP_steps_completed(0);
                    this.project.setP_progress(0.0F);
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
        return false;
    }
}