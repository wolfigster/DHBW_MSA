package persistence.tables;

import msa.Participant;
import msa.ParticipantType;
import persistence.HSQLDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ParticipantTable {

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

    public static void insertParticipant(Participant participant) {
        System.out.println("--- insert Participant in Participants Table ---");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("INSERT INTO participants (").append("id").append(",").append("name").append(",").append("type_id").append(")")
                .append(" VALUES ")
                .append("(").append(participant.getId()).append(",").append("'").append(participant.getName()).append("'").append(",").append(participant.getType().getId()).append(")");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

    public static void deleteParticipant(int id) {
        System.out.println("--- delete Participant from Participants Table ---");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DELETE ")
                .append("FROM participants ")
                .append("WHERE id = ").append(id);
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

    public static ArrayList<Participant> getParticipants() {
        ArrayList<Participant> participants = new ArrayList<>();
        try {
            StringBuilder sqlStringBuilder = new StringBuilder();
            sqlStringBuilder.append("SELECT * ")
                    .append("FROM participants ");
            ResultSet resultSet = HSQLDB.instance.query(sqlStringBuilder.toString());
            while (resultSet.next() && resultSet != null) {
                participants.add(createParticipant(resultSet));
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return participants;
    }

    public static Participant getParticipantByName(String name) {
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("SELECT * ")
                .append("FROM participants ")
                .append("WHERE name = '").append(name).append("'");
        ResultSet resultSet = HSQLDB.instance.query(sqlStringBuilder.toString());
        try {
            if(resultSet.next()) return createParticipant(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static Participant getParticipantById(int id) {
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("SELECT * ")
                .append("FROM participants ")
                .append("WHERE id = ").append(id);
        ResultSet resultSet = HSQLDB.instance.query(sqlStringBuilder.toString());
        try {
            if(resultSet.next()) return createParticipant(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private static Participant createParticipant(ResultSet resultSet) {
        try {
            ParticipantType type = ParticipantType.getParticipantTypeById(resultSet.getInt("type_id"));
            return new Participant(resultSet.getInt("id"), resultSet.getString("name"), type);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return null;
    }
}
