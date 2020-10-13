package command;

import msa.Channel;
import msa.Participant;
import persistence.tables.ChannelTable;
import persistence.tables.ParticipantTable;

public class IntrudeChannelCmd implements ICommand {

    private final Channel channel;
    private final Participant participant;

    public IntrudeChannelCmd(String channelName, String participantName) {
        System.out.println("IntrudeChannelCmd was constructed");

        this.channel = ChannelTable.getChannel(channelName);
        this.participant = ParticipantTable.getParticipantByName(participantName);

    }

    @Override
    public String execute() {
        System.out.println("Run command IntrudeChannelCmd");
        StringBuilder response = new StringBuilder();

        return response.toString();
    }
}
