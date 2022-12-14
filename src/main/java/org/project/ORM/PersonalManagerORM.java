package org.project.ORM;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Project ORM class.
 *
 * @author Luca Maiuri
 */
@SuppressWarnings("unused")
public class PersonalManagerORM implements CommonORM {

    private String p_id;
    private String p_name;
    private String p_description;
    private LocalDateTime p_dueDate;
    private Float p_progress;
    private Float p_target;
    private Integer p_steps_number;
    private Integer p_steps_completed;
    private Float p_steps_value;

    protected PersonalManagerORM() {
        this.p_id = null;
        this.p_name = null;
        this.p_description = null;
        this.p_dueDate = null;
        this.p_progress = null;
        this.p_target = null;
        this.p_steps_number = null;
        this.p_steps_value = null;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_id() {
        return p_id;
    }

    public String getP_name() {
        return p_name;
    }

    /**
     * Set the parameters of the ORM class Project Name.
     * If nothing is provided, the parameter will be set to a default value.
     *
     * @param p_name The name of the project.
     */
    public void setP_name(String p_name) {
        if (p_name.length() == 0) {
            this.p_name = "No name provided";
        } else if (p_name.length() > 30) {
            this.p_name = p_name.substring(0, 30);
        } else {
            this.p_name = p_name;
        }
    }

    public String getP_description() {
        return p_description;
    }

    /**
     * Set the parameters of the ORM class Project Description.
     * If nothing is provided, the parameter will be set to a default value.
     *
     * @param p_description The description of the project.
     */
    public void setP_description(String p_description) {
        if (p_description.length() == 0) {
            this.p_description = "No description provided";
        } else if (p_description.length() > 100) {
            this.p_description = p_description.substring(0, 100);
        } else {
            this.p_description = p_description;
        }
    }

    public LocalDateTime getP_dueDate() {
        return p_dueDate;
    }

    /**
     * Set the parameters of the ORM class Project Due Date.
     * If the date is before the existing date, the parameter will be set to a default value of the current date.
     *
     * @param p_dueDate The due date of the project.
     */
    public void setP_dueDate(LocalDateTime p_dueDate) {
        if (p_dueDate.isAfter(LocalDateTime.now())) {
            this.p_dueDate = p_dueDate;
        } else {
            if (this.p_dueDate == null) {
                this.p_dueDate = LocalDateTime.now();
            }
        }
    }

    public Float getP_progress() {
        return p_progress;
    }

    /**
     * Set the parameters of the ORM class Project Progress.
     * If the progress is negative, the parameter will be set to a default value of 0 or the current progress.
     *
     * @param p_progress The progress of the project.
     */
    public void setP_progress(Float p_progress) {
        if (p_progress >= 0) {
            this.p_progress = p_progress;
        } else {
            if (this.p_progress == null) {
                this.p_progress = 0f;
            }
        }
    }

    public Float getP_target() {
        return p_target;
    }

    /**
     * Set the parameters of the ORM class Project Target.
     * If the target is negative, the parameter will be set to a default value of 0 or the current target.
     *
     * @param p_target The target of the project.
     */
    public void setP_target(Float p_target) {
        if (p_target >= 0) {
            this.p_target = p_target;
        } else {
            if (this.p_target == null) {
                this.p_target = 0f;
            }
        }
    }

    public Integer getP_steps_number() {
        return p_steps_number;
    }

    /**
     * Set the parameters of the ORM class Project Steps Number.
     * <p></p>
     * If the steps number is negative, the parameter will be set in two ways:
     * <p></p>
     * 1) If the steps number is null, the parameter will be calculated from the target and the Due Date,
     * if they are not null, otherwise it will be set to a default value of 100.<br>
     * 2) If the steps number is not null, the parameter will be set to the current steps number.
     *
     * @param p_steps_number The steps number of the project.
     */
    public void setP_steps_number(Integer p_steps_number) {
        if (p_steps_number >= 0) {
            this.p_steps_number = p_steps_number;
        } else {
            if (this.p_steps_number == null) {
                if (this.p_target != null && this.p_dueDate != null) {
                    this.p_steps_number = Math.toIntExact(LocalDateTime.now().until(this.p_dueDate, ChronoUnit.DAYS));
                } else {
                    this.p_steps_number = 100;
                }
            }
        }
    }

    public Integer getP_steps_completed() {
        return p_steps_completed;
    }

    /**
     * Set the parameters of the ORM class Project Steps Completed.
     * <p></p>
     * If the steps completed is negative or greater than the steps number,
     * the parameter will be set to a default value of 0 or the current steps completed.
     *
     * @param p_steps_completed The steps completed of the project.
     */
    public void setP_steps_completed(Integer p_steps_completed) {
        if (p_steps_completed >= 0 && p_steps_completed <= this.p_steps_number) {
            this.p_steps_completed = p_steps_completed;
        } else {
            if (this.p_steps_completed == null) {
                this.p_steps_completed = 0;
            }
        }
    }

    public Float getP_steps_value() {
        return p_steps_value;
    }

    /**
     * Set the parameters of the ORM class Project Steps Value.
     * <p></p>
     * If the steps value is negative, the parameter will be set in two ways:
     * <p></p>
     * 1) If the steps number is not null then it will be kept as is.<br>
     * 2) If the steps number is null then it will be calculated from the current project target and Due Date, if they are not null,
     * otherwise it will be set to a default value of 1.
     *
     * @param p_steps_value The steps value of the project.
     */
    public void setP_steps_value(Float p_steps_value) {
        if (p_steps_value >= 0) {
            this.p_steps_value = p_steps_value;
        } else {
            if (this.p_steps_value == null) {
                if (this.p_target != null && this.p_steps_number != null) {
                    this.p_steps_value = this.p_target / this.p_steps_number;
                } else {
                    this.p_steps_value = 1f;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "ID:" + this.getP_id() + " " +
                "Name:" + this.getP_name() + " " +
                "Description:" + this.getP_description() + " " +
                "DueDate:" + this.getP_dueDate() + " " +
                "Progress:" + this.getP_progress() + " " +
                "Target:" + this.getP_target() + " " +
                "StepsNumber:" + this.getP_steps_number() + " " +
                "StepsCompleted:" + this.getP_steps_completed() + " " +
                "StepsValue:" + this.getP_steps_value();
    }

    /**
     * Describes the columns of the table for the ORM class.
     *
     * @return The columns of the table.
     */
    @Override
    public String describeColumns() {
        StringBuilder sb = new StringBuilder();
        for (Field field : this.getClass().getDeclaredFields()) {
            sb.append(
                    field.getName()
                            .substring(2)
                            .replaceAll("_", "")
                            .toUpperCase()
            ).append(":").append(field.getType().getSimpleName()).append(" ");
        }
        return sb.toString();
    }
}