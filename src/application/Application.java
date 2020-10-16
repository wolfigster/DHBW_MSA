package application;

import logger.AlgorithmType;
import msa.Channel;
import msa.Participant;
import msa.ParticipantType;
import persistence.HSQLDB;
import persistence.tables.*;

public class Application {
    public static void main(String... args) {
        simulateHSQLDB();
    }


    private static void simulateHSQLDB() {
        HSQLDB.instance.setupConnection();

        // drop all tables
        for(Participant participant : ParticipantTable.getParticipants()) PostboxTable.dropTable(participant.getName());
        AlgorithmTable.dropTable();
        ChannelTable.dropTable();
        MessageTable.dropTable();
        ParticipantTable.dropTable();
        TypeTable.dropTable();

        // create all tables
        AlgorithmTable.createTable();
        TypeTable.createTable();
        ParticipantTable.createTable();
        ChannelTable.createTable();
        MessageTable.createTable();

        // insert data in tables for simulation purposes
        // insert Algorithms
        for(AlgorithmType algorithmType : AlgorithmType.values()) {
            if(algorithmType != AlgorithmType.NONE) {
                AlgorithmTable.insertAlgorithm(algorithmType.getName());
            }
        }

        // insert Types
        for(ParticipantType participantType : ParticipantType.values()) TypeTable.insertType(participantType.getType());

        // insert Participants
        Participant branch_hkg = new Participant(1, "branch_hkg", ParticipantType.NORMAL);
        Participant branch_cpt = new Participant(2, "branch_cpt", ParticipantType.NORMAL);
        Participant branch_sfo = new Participant(3, "branch_sfo", ParticipantType.NORMAL);
        Participant branch_syd = new Participant(4, "branch_syd", ParticipantType.NORMAL);
        Participant branch_wuh = new Participant(5, "branch_wuh", ParticipantType.NORMAL);
        Participant msa = new Participant(6, "msa", ParticipantType.INTRUDER);

        ParticipantTable.insertParticipant(branch_hkg);
        ParticipantTable.insertParticipant(branch_cpt);
        ParticipantTable.insertParticipant(branch_sfo);
        ParticipantTable.insertParticipant(branch_syd);
        ParticipantTable.insertParticipant(branch_wuh);
        ParticipantTable.insertParticipant(msa);

        // add Postboxes
        for(Participant participant : ParticipantTable.getParticipants()) PostboxTable.createTable(participant.getName());

        // insert Channels
        Channel hkg_wuh = new Channel("hkg_wuh", branch_hkg, branch_wuh);
        Channel hkg_cpt = new Channel("hkg_cpt", branch_hkg, branch_cpt);
        Channel cpt_syd = new Channel("cpt_syd", branch_cpt, branch_syd);
        Channel syd_sfo = new Channel("syd_sfo", branch_syd, branch_sfo);

        ChannelTable.insertChannel(hkg_wuh);
        ChannelTable.insertChannel(hkg_cpt);
        ChannelTable.insertChannel(cpt_syd);
        ChannelTable.insertChannel(syd_sfo);

        HSQLDB.instance.shutdown();
    }
}