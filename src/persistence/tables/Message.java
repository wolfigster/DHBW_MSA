package persistence.tables;

import persistence.HSQLDB;

public class Message {

    public static void createTable() {
        System.out.println("--- create Messages Table ---");

        StringBuilder sqlStringBuilder01 = new StringBuilder();
        sqlStringBuilder01.append("CREATE TABLE messages ( ")
                .append("id TINYINT NOT NULL").append(",")
                .append("participant_from_id TINYINT NOT NULL").append(",")
                .append("participant_to_id TINYINT NOT NULL").append(",")
                .append("plain_message VARCHAR(50) NOT NULL").append(",")
                .append("algorithm_id TINYINT NOT NULL").append(",")
                .append("encrypted_message VARCHAR(50) NOT NULL").append(",")
                .append("keyfile VARCHAR(20) NOT NULL").append(",")
                .append("timestamp INTEGER").append(",")
                .append("PRIMARY KEY (id)")
                .append(" )");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder01.toString());

        HSQLDB.instance.update(sqlStringBuilder01.toString());

        StringBuilder sqlStringBuilder02 = new StringBuilder();
        sqlStringBuilder02.append("ALTER TABLE messages ADD CONSTRAINT messages_participant_from_id ")
                .append("FOREIGN KEY (participant_from_id) ")
                .append("REFERENCES participants (id) ")
                .append("ON DELETE CASCADE");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder02.toString());

        HSQLDB.instance.update(sqlStringBuilder02.toString());

        StringBuilder sqlStringBuilder03 = new StringBuilder();
        sqlStringBuilder03.append("ALTER TABLE messages ADD CONSTRAINT messages_participant_to_id ")
                .append("FOREIGN KEY (participant_to_id) ")
                .append("REFERENCES participants (id) ")
                .append("ON DELETE CASCADE");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder03.toString());

        HSQLDB.instance.update(sqlStringBuilder03.toString());

        StringBuilder sqlStringBuilder04 = new StringBuilder();
        sqlStringBuilder04.append("ALTER TABLE messages ADD CONSTRAINT messages_algorithm_id ")
                .append("FOREIGN KEY (algorithm_id) ")
                .append("REFERENCES algorithms (id) ")
                .append("ON DELETE CASCADE");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder04.toString());

        HSQLDB.instance.update(sqlStringBuilder04.toString());
    }

    public static void dropTable() {
        System.out.println("--- drop Messages Table ---");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE messages");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

    public static void insertMessage(int participantFromID, int participantToID, String plainMessage, int algorithmID, String encryptedMessage, String keyFile) {
        System.out.println("--- insert Message in Messages Table ---");

        long timestamp = System.currentTimeMillis() / 1000L;

        int nextID = HSQLDB.instance.getNextID("messages") + 1;
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("INSERT INTO messages (").append("id").append(",").append("participant_from_id").append(",").append("participant_to_id").append(",").append("plain_message").append(",").append("algorithm_id").append(",").append("encrypted_message").append(",").append("keyfile").append(",").append("timestamp").append(")")
                .append(" VALUES ")
                .append("(").append(nextID).append(",").append(participantFromID).append(",").append(participantToID).append(",").append("'").append(plainMessage).append("',").append(algorithmID).append(",").append("'").append(encryptedMessage).append("','").append(keyFile).append("',").append(timestamp)
                .append(")");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

    public static void deleteMessage(int id) {
        System.out.println("--- delete Message from Messages Table ---");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DELETE ")
                .append("FROM messages ")
                .append("WHERE id = ").append(id);
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }
}
