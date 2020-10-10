package persistence.tables;

import persistence.HSQLDB;

public class ChannelTable {

    public static void createTable() {
        System.out.println("--- create Channels Table ---");

        StringBuilder sqlStringBuilder01 = new StringBuilder();
        sqlStringBuilder01.append("CREATE TABLE channel ( ")
                .append("name VARCHAR(25) NOT NULL").append(",")
                .append("participant_01 TINYINT NOT NULL").append(",")
                .append("participant_02 TINYINT NOT NULL").append(",")
                .append("PRIMARY KEY (name)")
                .append(" )");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder01.toString());

        HSQLDB.instance.update(sqlStringBuilder01.toString());

        StringBuilder sqlStringBuilder02 = new StringBuilder();
        sqlStringBuilder02.append("ALTER TABLE channel ADD CONSTRAINT channel_participant_01 ")
                .append("FOREIGN KEY (participant_01) ")
                .append("REFERENCES participants (id) ")
                .append("ON DELETE CASCADE");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder02.toString());

        HSQLDB.instance.update(sqlStringBuilder02.toString());

        StringBuilder sqlStringBuilder03 = new StringBuilder();
        sqlStringBuilder03.append("ALTER TABLE channel ADD CONSTRAINT channel_participant_02 ")
                .append("FOREIGN KEY (participant_02) ")
                .append("REFERENCES participants (id) ")
                .append("ON DELETE CASCADE");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder03.toString());

        HSQLDB.instance.update(sqlStringBuilder03.toString());
    }

    public static void dropTable() {
        System.out.println("--- drop Channels Table ---");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE channel");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

    public static void insertChannel(String name, String participant_01, String participant_02) {
        System.out.println("--- insert Channel in Channel Table ---");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("INSERT INTO channel (").append("name").append(",").append("participant_01").append(",").append("participant_02").append(")")
                .append(" VALUES ")
                .append("('").append(name).append("','").append(participant_01).append("','").append(participant_02).append("'").append(")");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

    public static void deleteChannel(String name) {
        System.out.println("--- delete Channel from Channel Table ---");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DELETE ")
                .append("FROM channel ")
                .append("WHERE id = ").append(name);
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }
}
