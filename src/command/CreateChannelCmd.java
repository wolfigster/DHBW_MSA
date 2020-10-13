package command;

import msa.Channel;
import msa.Participant;
import persistence.tables.ChannelTable;
import persistence.tables.ParticipantTable;

public class CreateChannelCmd implements ICommand {

    private String channelName;
    private Participant participant01;
    private Participant participant02;
    private Channel channel;

    public CreateChannelCmd(String channelName, String participantName01, String participantName02) {
        System.out.println("CreateChannelCmd was constructed");

        this.channelName = channelName;
        this.participant01 = ParticipantTable.getParticipantByName(participantName01);
        this.participant02 = ParticipantTable.getParticipantByName(participantName02);
    }

    @Override
    public String execute() {
        System.out.println("Run command CreateChannelCmd");
        StringBuilder response = new StringBuilder();
        // Do something

        if(participant01.getName().equals(participant02.getName())) {
            response.append(participant01.getName()).append(" (").append(participant01.getId()).append(") and ").append(participant02.getName()).append(" (").append(participant02.getId()).append(") are identical - cannot create channel on itself");
            return response.toString();
        }

        // [i]
        if(ChannelTable.getParticipantsByChannelName(channelName) != null) {
            response.append("channel ").append(channelName).append(" already exists");
            return response.toString();
        }

        // [ii]
        if(ChannelTable.getChannelNameByParticipants(participant01.getId(), participant02.getId()) != null || ChannelTable.getChannelNameByParticipants(participant02.getId(), participant01.getId()) != null) {
            response.append("communication channel between ").append(participant01.getName()).append(" (").append(participant01.getId()).append(") and ").append(participant02.getName()).append(" (").append(participant02.getId()).append(") already exists");
            return response.toString();
        }
        channel = new Channel(channelName, participant01, participant02);
        ChannelTable.insertChannel(channel);
        response.append("channel ").append(channelName).append(" from ").append(participant01.getName()).append(" (").append(participant01.getId()).append(") to ").append(participant02.getName()).append(" (").append(participant02.getId()).append(") successfully created");

        return response.toString();
    }
}