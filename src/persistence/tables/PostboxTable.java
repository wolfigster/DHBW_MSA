package persistence.tables;

import persistence.HSQLDB;

public class PostboxTable {

    public static void createTable(String participantName) {
        System.out.println("--- create Postbox Table for: " + participantName + " ---");

        StringBuilder sqlStringBuilder01 = new StringBuilder();
        sqlStringBuilder01.append("CREATE TABLE postbox_").append(participantName).append(" (")
                .append("id TINYINT NOT NULL").append(",")
                .append("participant_from_id TINYINT NOT NULL").append(",")
                .append("message VARCHAR(50) NOT NULL").append(",")
                .append("timestamp INTEGER").append(",")
                .append("PRIMARY KEY (id)")
                .append(" )");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder01.toString());

        HSQLDB.instance.update(sqlStringBuilder01.toString());

        StringBuilder sqlStringBuilder02 = new StringBuilder();
        sqlStringBuilder02.append("ALTER TABLE postbox_").append(participantName).append(" ADD CONSTRAINT postbox_").append(participantName).append("_participant_from_id ")
                .append("FOREIGN KEY (participant_from_id) ")
                .append("REFERENCES participants (id) ")
                .append("ON DELETE CASCADE");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder02.toString());

        HSQLDB.instance.update(sqlStringBuilder02.toString());
    }

    public static void dropTable(String participantName) {
        System.out.println("--- drop postbox_" + participantName + " Table");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE postbox_").append(participantName);
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

    public static void insertPostbox(String participantName, int participantFromId, String message) {
        System.out.println("--- insert Data in postbox_" + participantName + " Table");

        long timestamp = System.currentTimeMillis() / 1000L;

        int nextID = HSQLDB.instance.getNextID("postbox_" + participantName) + 1;
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("INSERT INTO postbox_").append(participantName).append(" (").append("id").append(",").append("participant_from_id").append(",").append("message").append(",").append("timestamp").append(")")
                .append(" VALUES ")
                .append("(").append(nextID).append(",'").append(participantFromId).append("','").append(message).append("',").append(timestamp).append(")");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

    public static void deleteChannel(String participantName, String name) {
        System.out.println("--- delete Data from postbox_" + participantName + " Table");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DELETE ")
                .append("FROM postbox_").append(participantName).append(" ")
                .append("WHERE id = ").append(name);
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }
}
