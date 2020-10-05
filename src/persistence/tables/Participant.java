package persistence.tables;

import persistence.HSQLDB;

public class Participant {

    public static void createTable() {
        System.out.println("--- create Participants Table ---");

        StringBuilder sqlStringBuilder01 = new StringBuilder();
        sqlStringBuilder01.append("CREATE TABLE participants ( ")
                .append("id TINYINT NOT NULL").append(",")
                .append("name VARCHAR(50) NOT NULL").append(",")
                .append("type_id TINYINT NULL").append(",")
                .append("PRIMARY KEY (id)")
                .append(" )");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder01.toString());

        HSQLDB.instance.update(sqlStringBuilder01.toString());

        StringBuilder sqlStringBuilder02 = new StringBuilder();
        sqlStringBuilder02.append("CREATE UNIQUE INDEX idxParticipants ON types (name)");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder02.toString());

        HSQLDB.instance.update(sqlStringBuilder02.toString());

        StringBuilder sqlStringBuilder03 = new StringBuilder();
        sqlStringBuilder03.append("ALTER TABLE participants ADD CONSTRAINT participant_type_id ")
                .append("FOREIGN KEY (type_id) ")
                .append("REFERENCES participants (id) ")
                .append("ON DELETE CASCADE");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder03.toString());

        HSQLDB.instance.update(sqlStringBuilder03.toString());
    }

    public static void dropTable() {
        System.out.println("--- drop Participants Table ---");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE participants");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

    public static void insertParticipant(String name, int typeID) {
        System.out.println("--- insert Participant in Participants Table ---");

        int nextID = HSQLDB.instance.getNextID("participants") + 1;
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("INSERT INTO participants (").append("id").append(",").append("name").append(",").append("type_id").append(")")
                .append(" VALUES ")
                .append("(").append(nextID).append(",").append("'").append(name).append("'").append(",").append(typeID).append(")");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

    public static void deleteMessage(int id) {
        System.out.println("--- delete Participant from Participants Table ---");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DELETE ")
                .append("FROM participants ")
                .append("WHERE id = ").append(id);
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }
}
