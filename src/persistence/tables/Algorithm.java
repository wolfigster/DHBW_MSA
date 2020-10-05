package persistence.tables;

import persistence.HSQLDB;

public class Algorithm {

    public static void createTable() {
        System.out.println("--- create Algorithms Table ---");

        StringBuilder sqlStringBuilder01 = new StringBuilder();
        sqlStringBuilder01.append("CREATE TABLE algorithms ( ")
                .append("id TINYINT NOT NULL").append(",")
                .append("name VARCHAR(10) NOT NULL").append(",")
                .append("PRIMARY KEY (id)")
                .append(" )");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder01.toString());

        HSQLDB.instance.update(sqlStringBuilder01.toString());

        StringBuilder sqlStringBuilder02 = new StringBuilder();
        sqlStringBuilder02.append("CREATE UNIQUE INDEX idxAlgorithms ON algorithms (name)");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder02.toString());

        HSQLDB.instance.update(sqlStringBuilder02.toString());
    }

    public static void dropTable() {
        System.out.println("--- drop Algorithms Table ---");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE algorithms");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

    public static void insertAlgorithm(String name) {
        System.out.println("--- insert Algorithm in Algorithms Table ---");

        int nextID = HSQLDB.instance.getNextID("algorithms") + 1;
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("INSERT INTO algorithms (").append("id").append(",").append("name").append(")")
                .append(" VALUES ")
                .append("(").append(nextID).append(",").append("'").append(name).append("'").append(")");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

    public static void deleteAlgorithm(int id) {
        System.out.println("--- delete Algorithm from Algorithms Table ---");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DELETE ")
                .append("FROM algorithms ")
                .append("WHERE id = ").append(id);
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }
}
