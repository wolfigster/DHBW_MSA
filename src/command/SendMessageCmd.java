package command;

import configuration.Configuration;
import cryptography.Cryptography;
import logger.AlgorithmType;
import msa.Channel;
import msa.MsgEvent;
import msa.Participant;
import persistence.tables.AlgorithmTable;
import persistence.tables.ChannelTable;
import persistence.tables.MessageTable;
import persistence.tables.ParticipantTable;

import java.io.File;

public class SendMessageCmd implements ICommand {

    private final String message;
    private final String participantName01;
    private final String participantName02;
    private final String algorithm;
    private final String keyfile;

    private final Cryptography cryptography = new Cryptography();

    public SendMessageCmd(String message, String participantName01, String participantName02, String algorithm, String keyfile) {
        System.out.println("SendMessageCmd was constructed");

        this.message = message;
        this.participantName01 = participantName01;
        this.participantName02 = participantName02;
        this.algorithm = algorithm;
        this.keyfile = keyfile;
    }

    @Override
    public String execute() {
        System.out.println("Run command SendMessageCmd");
        StringBuilder response = new StringBuilder();

        Participant participant01 = ParticipantTable.getParticipantByName(participantName01);
        Participant participant02 = ParticipantTable.getParticipantByName(participantName02);
        Channel channel;
        AlgorithmType algorithmType;
        File keyf;

        if(participant01 == null) {
            response.append(participantName01).append(" doesn't exist");
            return response.toString();
        }

        if(participant02 == null) {
            response.append(participantName02).append(" doesn't exist");
            return response.toString();
        }

        String channelName = ChannelTable.getChannelNameByParticipants(participant01.getId(), participant02.getId()) == null ? ChannelTable.getChannelNameByParticipants(participant02.getId(), participant01.getId()) : ChannelTable.getChannelNameByParticipants(participant01.getId(), participant02.getId());
        if(channelName == null) {
            response.append("no valid channel from ").append(participant01.getName()).append(" (").append(participant01.getId()).append(") to ").append(participant02.getName()).append(" (").append(participant02.getId()).append(")");
            return response.toString();
        }
        channel = ChannelTable.getChannel(channelName);

        algorithmType = AlgorithmType.getAlgorithm(algorithm);
        if(algorithmType == null) {
            response.append("Couldn't find algorithm with name: \"").append(algorithm).append("\"");
            return response.toString();
        }

        keyf = new File(Configuration.instance.keyfileDirectory + keyfile);
        if(!keyf.exists()) {
            response.append("Couldn't find keyfile: \"").append(keyfile).append("\"");
            return response.toString();
        }

        String encryptedMsg = cryptography.encrypt(message, algorithmType, keyf);
        int algorithmId = AlgorithmTable.getAlgorithmId(algorithmType);
        MessageTable.insertMessage(participant01.getId(), participant02.getId(), message, algorithmId, encryptedMsg, keyf.getName());
        MsgEvent msgEvent = new MsgEvent(participant01, participant02, encryptedMsg, algorithmType, keyf);
        channel.sendMsg(msgEvent);
        response.append(participant02.getName()).append(" (").append(participant02.getId()).append(") received new message");

        return response.toString();
    }
}