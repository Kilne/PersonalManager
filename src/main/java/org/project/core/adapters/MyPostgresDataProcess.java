package org.project.core.adapters;

import org.project.ORM.FactoryORM;
import org.project.ORM.ORMtypes;
import org.project.ORM.PersonalManagerORM;

import java.time.LocalDateTime;
import java.util.Arrays;

public class MyPostgresDataProcess implements AdapterDataDB<PersonalManagerORM> {
    private final FactoryORM factoryORM;
    private String data;

    public MyPostgresDataProcess() {
        this.factoryORM = new FactoryORM();
    }

    public void setData(String data) {
        this.data = data;
    }

    /**
     * Packs the data into a PersonalManagerORM object.
     *
     * @return The PersonalManagerORM object.
     * @see PersonalManagerORM
     * @see FactoryORM
     * @see ORMtypes
     */
    @Override
    public PersonalManagerORM packData() {
        this.factoryORM.buildORM(ORMtypes.PERSONALMANAGERORM);
        PersonalManagerORM personalManagerORM = (PersonalManagerORM) this.factoryORM.getORM();
        String[] tokenized = this.data.split(" ");
        for (String token : tokenized) {
            String[] splittedToken = token.split(":");
            switch (splittedToken[0].toLowerCase()) {
                case "id" -> personalManagerORM.setP_id(splittedToken[1]);
                case "name" -> personalManagerORM.setP_name(splittedToken[1]);
                case "description" -> personalManagerORM.setP_description(splittedToken[1]);
                case "duedate" -> personalManagerORM.setP_dueDate(LocalDateTime.parse(splittedToken[1] + "T00:00:00"));
                case "progress" -> personalManagerORM.setP_progress(Float.parseFloat(splittedToken[1]));
                case "target" -> personalManagerORM.setP_target(Float.parseFloat(splittedToken[1]));
                case "stepsnumber" -> personalManagerORM.setP_steps_number(Integer.parseInt(splittedToken[1]));
                case "stepscompleted" -> personalManagerORM.setP_steps_completed(Integer.parseInt(splittedToken[1]));
                case "stepsvalue" -> personalManagerORM.setP_steps_value(Float.parseFloat(splittedToken[1]));
            }
        }

        return personalManagerORM;
    }

    /**
     * Unpacks the data from a PersonalManagerORM object.
     *
     * @return A string containing the data.
     */
    @Override
    public String unpackData(PersonalManagerORM data) {
        String[] tokens = data.describeColumns().split(" ");
        String[] columns = Arrays.stream(tokens).map(token -> token.split(":")[0]).toArray(String[]::new);
        StringBuilder stringBuilder = new StringBuilder();
        for (String column : columns) {
            stringBuilder.append(column).append(":");
            switch (column.toLowerCase()) {
                case "id" -> stringBuilder.append(data.getP_id());
                case "name" -> stringBuilder.append(data.getP_name());
                case "description" -> stringBuilder.append(data.getP_description());
                case "duedate" -> stringBuilder.append(data.getP_dueDate().toString().split("T")[0]);
                case "progress" -> stringBuilder.append(data.getP_progress());
                case "target" -> stringBuilder.append(data.getP_target());
                case "stepsnumber" -> stringBuilder.append(data.getP_steps_number());
                case "stepscompleted" -> stringBuilder.append(data.getP_steps_completed());
                case "stepsvalue" -> stringBuilder.append(data.getP_steps_value());
            }
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}