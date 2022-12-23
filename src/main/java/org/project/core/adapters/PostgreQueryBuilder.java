package org.project.core.adapters;

import java.util.Map;
import java.util.TreeMap;

/**
 * Query builder for PostgreSQL.
 *
 * @author Luca Maiuri
 */
@SuppressWarnings("unused")
public class PostgreQueryBuilder {

    private static final String encloser = "\"";
    private static final String lightEnclose = "'";
    private static final String select = "SELECT * FROM public." + encloser + "tablename" + encloser;
    private static final String insert = "INSERT INTO public." + encloser + "tablename" + encloser +" () VALUES ()";
    private static final String update = "UPDATE public." + encloser + "tablename" + encloser +" SET () WHERE ()";
    private static final String delete = "DELETE FROM public" + encloser + "tablename" + encloser +" WHERE ()";
    private final TreeMap<String, String> args = new TreeMap<>();
    private String query;
    private QueryType type;
    private String table;

    /**
     * Parses the args string for the query.
     *
     * @param args String of args.
     * @return This object containing the args in a map.
     */
    public PostgreQueryBuilder parseArgs(String args) {
        String[] argsStrings = args.split(" ");
        for (String arg : argsStrings) {
            String[] argStrings = arg.split(":");
            this.args.put(argStrings[0], argStrings[1]);
        }
        return this;
    }

    /**
     * Sets the table name.
     *
     * @param table Table name.
     * @return This object containing the table name.
     */
    public PostgreQueryBuilder setTable(String table) {
        this.table = table;
        return this;
    }

    /**
     * Resets the query args
     *
     * @return This object with the args resetted.
     */
    public PostgreQueryBuilder resetArgs() {
        this.args.clear();
        return this;
    }

    /**
     * Resets the query.
     *
     * @return This object with the query resetted.
     */
    public PostgreQueryBuilder resetQuery() {
        this.query = "";
        return this;
    }

    /**
     * Resets the query type.
     *
     * @return This object with the query type resetted.
     */
    public PostgreQueryBuilder resetQueryType() {
        this.type = null;
        return this;
    }

    /**
     * Resets the table name.
     *
     * @return This object with the table name resetted.
     */
    public PostgreQueryBuilder resetTable() {
        this.table = "";
        return this;
    }

    /**
     * Resets all fields
     *
     * @return This object with all field resetted
     */
    public PostgreQueryBuilder resetAll() {
        resetArgs();
        resetQuery();
        resetQueryType();
        resetTable();
        return this;
    }

    /**
     * Sets the query type
     *
     * @param type The query type
     * @return This object with a query type set
     * @see QueryType
     */
    public PostgreQueryBuilder setQueryType(QueryType type) {
        this.type = type;
        return this;
    }

    /**
     * Fills a string with the args and table name.
     * <p></p>
     * Depending on the {@link QueryType} used the args are processed
     * in different ways to accomodate the SQL constructs of Postgre
     *
     * @param transientQuery The transient query to be built
     * @return The query built
     */
    private String addColumnsAndValues(String transientQuery) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for (Map.Entry<String, String> entry : args.entrySet()) {
            columns.append(encloser).append(entry.getKey().toLowerCase()).append(encloser).append(", ");
            values.append(lightEnclose).append(entry.getValue()).append(lightEnclose).append(", ");
        }
        columns.delete(columns.length() - 2, columns.length());
        values.delete(values.length() - 2, values.length());
        transientQuery = transientQuery.replace("tablename", this.table);
        switch (this.type) {
            case INSERT -> {
                StringBuilder columnsBuilder = new StringBuilder();
                StringBuilder valuesBuilder = new StringBuilder();
                for (Map.Entry<String, String> entry : this.args.entrySet()) {
                    if (entry.getKey().equalsIgnoreCase("id")) continue;
                    columnsBuilder.append(encloser).append(entry.getKey().toLowerCase()).append(encloser).append(",");
                    valuesBuilder.append(lightEnclose).append(entry.getValue()).append(lightEnclose).append(",");
                }
                transientQuery = transientQuery.replaceFirst("\\(\\)",
                        "(" + columnsBuilder.deleteCharAt(columnsBuilder.length() - 1) + ")");
                transientQuery = transientQuery.replaceFirst("\\(\\)",
                        "(" + valuesBuilder.deleteCharAt(valuesBuilder.length() - 1) + ")");
            }
            case UPDATE -> {
                transientQuery = transientQuery.replaceFirst("\\(\\)", columns + "=" + values);
                transientQuery = transientQuery.replaceFirst("\\(\\)", "ID=" + this.args.get("ID"));
            }
            case DELETE -> transientQuery = transientQuery
                    .replaceFirst("\\(\\)", "ID=" + this.args.get("ID"));
        }
        return transientQuery;
    }

    /**
     * Builds the query depending on the query type
     *
     * @return the object with a built query set
     */
    public PostgreQueryBuilder buildQuery() {
        this.query = switch (this.type) {
            case INSERT -> addColumnsAndValues(insert);
            case UPDATE -> addColumnsAndValues(update);
            case DELETE -> addColumnsAndValues(delete);
            case SELECT -> select.replace("tablename", this.table);
        };
        return this;
    }

    public String getQuery() {
        return query;
    }

}