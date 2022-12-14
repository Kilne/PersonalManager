package org.project.core;

import org.project.ORM.PersonalManagerORM;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ProjectEditorLogic {

    PersonalManagerORM project;

    public PersonalManagerORM getProject() {
        return this.project;
    }

    public void setProject(PersonalManagerORM project) {
        this.project = project;
    }

    private void syncParams() {
        this.project.setP_steps_value(
                this.project.getP_target() / this.project.getP_dueDate().until(LocalDateTime.now(), ChronoUnit.DAYS));
        this.project.setP_steps_number(
                Math.toIntExact(this.project.getP_dueDate().until(LocalDateTime.now(), ChronoUnit.DAYS)));
        this.project.setP_progress(
                (float) this.project.getP_steps_completed() / (float) this.project.getP_steps_number());
    }

    public boolean changeName(String newName) {
        if (newName.length() > 0) {
            this.project.setP_name(newName);
            return true;
        }
        return false;
    }

    public boolean changeDescription(String newDescription) {
        if (newDescription.length() > 0) {
            this.project.setP_description(newDescription);
            return true;
        }
        return false;
    }

    public boolean changeDueDate(String newDueDate) {
        if (newDueDate.length() > 0) {
            try {
                LocalDateTime date = LocalDateTime.parse(newDueDate);
                if (date.isAfter(LocalDateTime.now())) {
                    this.project.setP_dueDate(date);
                    this.syncParams();
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

    public boolean changeTarget(String newTarget) {
        if (newTarget.length() > 0) {
            try {
                float target = Float.parseFloat(newTarget);
                if (target > 0) {
                    this.project.setP_target(target);
                    this.syncParams();
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

    public boolean addStepCompleted() {
        int current = this.project.getP_steps_completed();
        if (current < this.project.getP_steps_number()) {
            this.project.setP_steps_completed(current + 1);
            this.syncParams();
            return true;
        }
        return false;
    }

    public boolean removeStepCompleted() {
        int current = this.project.getP_steps_completed();
        if (current > 0) {
            this.project.setP_steps_completed(current - 1);
            this.syncParams();
            return true;
        }
        return false;
    }

}