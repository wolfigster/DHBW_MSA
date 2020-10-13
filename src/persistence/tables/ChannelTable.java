package persistence.tables;

import msa.Channel;
import msa.Participant;
import persistence.HSQLDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public static void insertChannel(Channel channel) {
        insertChannel(channel.getName(), channel.getParticipant01().getId(), channel.getParticipant02().getId());
    }

    public static void insertChannel(String name, int participant_01, int participant_02) {
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
                .append("WHERE name = '").append(name).append("'");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

    public static ArrayList<Channel> getChannels() {
        ArrayList<Channel> channels = new ArrayList<>();
        try {
            StringBuilder sqlStringBuilder = new StringBuilder();
            sqlStringBuilder.append("SELECT * ")
                    .append("FROM channel");
            ResultSet resultSet = HSQLDB.instance.query(sqlStringBuilder.toString());
            while (resultSet.next() && resultSet != null) {
                channels.add(createChannel(resultSet));
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return channels;
    }

    public static Channel getChannel(String name) {
        Channel channel = null;
        try {
            StringBuilder sqlStringBuilder = new StringBuilder();
            sqlStringBuilder.append("SELECT * ")
                    .append("FROM channel ")
                    .append("WHERE name = '").append(name).append("'");
            ResultSet resultSet = HSQLDB.instance.query(sqlStringBuilder.toString());
            while (resultSet.next() && resultSet != null) {
                channel = createChannel(resultSet);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return channel;
    }

    public static String getChannelNameByParticipants(int participant_01, int participant_02) {
        try {
            StringBuilder sqlStringBuilder = new StringBuilder();
            sqlStringBuilder.append("SELECT name ")
                    .append("FROM channel ")
                    .append("WHERE participant_01 = ").append(participant_01).append(" ")
                    .append("AND participant_02 = ").append(participant_02);
            ResultSet resultSet = HSQLDB.instance.query(sqlStringBuilder.toString());
            while(resultSet.next() && resultSet != null) return resultSet.getString("name");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return null;
    }

    public static ArrayList<Participant> getParticipantsByChannelName(String name) {
        try {
            StringBuilder sqlStringBuilder = new StringBuilder();
            sqlStringBuilder.append("SELECT participant_01, participant_02 ")
                    .append("FROM channel ")
                    .append("WHERE name = '").append(name).append("'");
            ResultSet resultSet = HSQLDB.instance.query(sqlStringBuilder.toString());
            ArrayList<Participant> participants = new ArrayList<>();
            while(resultSet.next() && resultSet != null) {
                participants.add(ParticipantTable.getParticipantById(resultSet.getInt("participant_01")));
                participants.add(ParticipantTable.getParticipantById(resultSet.getInt("participant_02")));
            }
            if(!participants.isEmpty()) return participants;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return null;
    }

    private static Channel createChannel(ResultSet resultSet) {
        try {
            Participant participant01 = ParticipantTable.getParticipantById(resultSet.getInt("participant_01"));
            Participant participant02 = ParticipantTable.getParticipantById(resultSet.getInt("participant_02"));
            return new Channel(resultSet.getString("name"), participant01, participant02);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return null;
    }
}
