package persistence.tables;

import msa.ParticipantType;
import persistence.HSQLDB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeTable {

    public static void createTable() {
        System.out.println("--- create Types Table");

        StringBuilder sqlStringBuilder01 = new StringBuilder();
        sqlStringBuilder01.append("CREATE TABLE types ( ")
                .append("id TINYINT NOT NULL").append(",")
                .append("name VARCHAR(10) NOT NULL").append(",")
                .append("PRIMARY KEY (id)")
                .append(" )");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder01.toString());

        HSQLDB.instance.update(sqlStringBuilder01.toString());

        StringBuilder sqlStringBuilder02 = new StringBuilder();
        sqlStringBuilder02.append("CREATE UNIQUE INDEX idxTypes ON types (name)");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder02.toString());

        HSQLDB.instance.update(sqlStringBuilder02.toString());
    }

    public static void dropTable() {
        System.out.println("--- drop Types Table");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE types");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

    public static void insertType(String name) {
        System.out.println("--- insert Type in Types Table");

        int nextID = HSQLDB.instance.getNextID("types") + 1;
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("INSERT INTO types (").append("id").append(",").append("name").append(")")
                .append(" VALUES ")
                .append("(").append(nextID).append(",").append("'").append(name).append("'").append(")");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

    public static void deleteType(int id) {
        System.out.println("--- delete Type from Types Table");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DELETE ")
                .append("FROM types ")
                .append("WHERE id = ").append(id);
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

    public static ParticipantType getTypeIdByName(String name) {
        try {
            StringBuilder sqlStringBuilder = new StringBuilder();
            sqlStringBuilder.append("SELECT id ")
                    .append("FROM types ")
                    .append("WHERE name = '").append(name).append("'");
            ResultSet resultSet = HSQLDB.instance.query(sqlStringBuilder.toString());
            while(resultSet.next() && resultSet != null) return ParticipantType.getParticipantTypeById(resultSet.getInt("id"));
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return null;
    }

    public static ParticipantType getTypeNameById(int id) {
        try {
            StringBuilder sqlStringBuilder = new StringBuilder();
            sqlStringBuilder.append("SELECT name ")
                    .append("FROM types ")
                    .append("WHERE id = ").append(id);
            ResultSet resultSet = HSQLDB.instance.query(sqlStringBuilder.toString());
            while(resultSet.next() && resultSet != null) return ParticipantType.getParticipantTypeByName(resultSet.getString("name"));
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return null;
    }
}
