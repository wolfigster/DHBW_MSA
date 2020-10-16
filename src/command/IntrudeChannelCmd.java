package command;

import msa.Channel;
import msa.Participant;
import msa.ParticipantType;
import persistence.tables.ChannelTable;
import persistence.tables.ParticipantTable;

public class IntrudeChannelCmd implements ICommand {

    private final String channelName;
    private final String participantName;

    public IntrudeChannelCmd(String channelName, String participantName) {
        System.out.println("IntrudeChannelCmd was constructed");

        this.channelName = channelName;
        this.participantName = participantName;
    }

    @Override
    public String execute() {
        System.out.println("Run command IntrudeChannelCmd");
        StringBuilder response = new StringBuilder();

        Channel channel = ChannelTable.getChannel(channelName);
        if(channel == null) {
            response.append("unknown channel ").append(channelName);
            return response.toString();
        }

        Participant participant = ParticipantTable.getParticipantByName(participantName);
        if(participant == null) {
            response.append(participantName).append(" doesn't exist");
            return response.toString();
        }

        if(!participant.getType().equals(ParticipantType.INTRUDER)) {
            response.append(participantName).append(" isn't an intruder");
            return response.toString();
        }

        channel.setIntruder(participant);
        response.append(participant.getName()).append(" intrudes now channel ").append(channel.getName());

        return response.toString();
    }
}
