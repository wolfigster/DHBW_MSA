package command;

import msa.Participant;
import persistence.tables.ChannelTable;
import persistence.tables.ParticipantTable;

public class SendMessageCmd implements ICommand {

    private final String message;
    private final String participantName01;
    private final String participantName02;
    private final String algorithm;
    private final String keyfile;

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

        if(participant01 == null) {
            response.append(participantName01).append(" doesn't exist");
            return response.toString();
        }

        if(participant02 == null) {
            response.append(participantName02).append(" doesn't exist");
            return response.toString();
        }

        if(ChannelTable.getChannelNameByParticipants(participant01.getId(), participant02.getId()) == null && ChannelTable.getChannelNameByParticipants(participant02.getId(), participant01.getId()) == null) {
            response.append("no valid channel from ").append(participant01.getName()).append(" (").append(participant01.getId()).append(") to ").append(participant02.getName()).append(" (").append(participant02.getId()).append(")");
            return response.toString();
        }

        return response.toString();
    }
}