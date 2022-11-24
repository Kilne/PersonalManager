package org.project.ORM;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

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

    /**
     * Parse a query result string from a PostgreSQL database wrapper class and return a Project object.
     * <p></p>
     * <p> The string must be in the form of: "ColumnName=Value ; " for each column in the table. </p>
     * <p></p>
     * <p>If the string is not in the correct format, the method will default the value of the object to a default value.</p>
     * <p></p>
     * <p> The types of the values are determined by the column type in the database, so they are assumed Type-Safe. </p>
     *
     * @param objects The query result string.
     */
    @Override
    public void parse(Object... objects) {
        /* inital varargs checks , default the object in case of failure */
        if (objects.length != 1 && !(objects[0] instanceof String)) {
            defaultTheObject();
        } else {
            /* parse the string, split each column provided */
            String s = (String) objects[0];
            String[] split = s.split(";");

            /*checks if an ID is present if not default the object */
            boolean idPresent = false;
            for (String s1 : split) {
                if (s1.toLowerCase().contains("id")) {
                    idPresent = true;
                    break;
                }
            }

            /* If ID column is present proceeds to iterate over the string for values, else it defaults the object */
            if (idPresent) {
                for (String s1 : split) {
                    switch (s1.split("=")[0].toLowerCase()) {
                        case "id" -> this.p_id = s1.split("=")[1];
                        case "name" -> setP_name(s1.split("=")[1]);
                        case "description" -> setP_description(s1.split("=")[1]);
                        case "duedate" -> setP_dueDate(LocalDateTime.parse(s1.split("=")[1]));
                        case "progress" -> setP_progress(Float.parseFloat(s1.split("=")[1]));
                        case "target" -> setP_target(Float.parseFloat(s1.split("=")[1]));
                        case "stepsnumber" -> setP_steps_number(Integer.parseInt(s1.split("=")[1]));
                        case "stepscompleted" -> setP_steps_completed(Integer.parseInt(s1.split("=")[1]));
                        case "stepsvalue" -> setP_steps_value(Float.parseFloat(s1.split("=")[1]));
                        default -> {
                        }
                    }
                }
                /* fill in the missing values using reflection*/
                for (Field field : this.getClass().getDeclaredFields()) {
                    try {
                        if (field.get(this) == null) {
                            switch (field.getName().toLowerCase()) {
                                case "p_name" -> setP_name("");
                                case "p_description" -> setP_description("");
                                case "p_duedate" -> setP_dueDate(LocalDateTime.now());
                                case "p_progress" -> setP_progress(0f);
                                case "p_target" -> setP_target(0f);
                                case "p_steps_number" -> setP_steps_number(-1);
                                case "p_steps_completed" -> setP_steps_completed(-1);
                                case "p_steps_value" -> setP_steps_value(-1f);
                            }
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                defaultTheObject();
            }
        }
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
        if (p_dueDate.compareTo(LocalDateTime.now()) > 0) {
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
     * If the steps number is negative, the parameter will be set in two ways:
     * 1) If the steps number is null, the parameter will be calculated from the target and the Due Date,
     * if they are not null, otherwise it will be set to a default value of 100.
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
                    this.p_steps_number = (int) (this.p_target / (this.p_dueDate.getDayOfYear() - LocalDateTime.now().getDayOfYear()));
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
     * If the steps completed is negative or greater than the steps number,
     * , the parameter will be set to a default value of 0 or the current steps completed.
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
     * If the steps value is negative, the parameter will be set in two ways:
     * 1) If the steps number is not null then it will be kept as is.
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

    /**
     * Defaults the object to a default state.
     */
    private void defaultTheObject() {
        this.p_id = "";
        this.p_name = "";
        this.p_description = "";
        this.p_dueDate = LocalDateTime.now();
        this.p_progress = 0f;
        this.p_target = 0f;
        this.p_steps_number = 0;
        this.p_steps_completed = 0;
        this.p_steps_value = 0f;
    }

    @Override
    public String toString() {
        return "Project{" +
                "\n p_id='" + p_id + '\'' +
                ",\n p_name='" + p_name + '\'' +
                ",\n p_description='" + p_description + '\'' +
                ",\n p_dueDate=" + p_dueDate +
                ",\n p_progress=" + p_progress +
                ",\n p_target=" + p_target +
                ",\n p_steps_number=" + p_steps_number +
                ",\n p_steps_completed=" + p_steps_completed +
                ",\n p_steps_value=" + p_steps_value +
                "\n}";
    }
}