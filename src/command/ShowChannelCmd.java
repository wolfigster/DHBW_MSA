package command;

import network.Channel;
import network.Participant;
import persistence.tables.ChannelTable;
import persistence.tables.ParticipantTable;

public class ShowChannelCmd implements ICommand {

    public ShowChannelCmd() {
        System.out.println("ShowChannelCmd was constructed");
    }

    @Override
    public String execute() {
        System.out.println("Run command ShowChannelCmd");
        StringBuilder response = new StringBuilder();
        // Do something

        response.append("Channel | Participant01 and Participant02");
        for(Channel channel : ChannelTable.getChannels()) {
            response.append("\n").append(channel.getName()).append(" | ").append(channel.getParticipant01().getName()).append(" and ").append(channel.getParticipant02().getName());
        }
        for(Participant participant : ParticipantTable.getParticipants()) {
            System.out.println(participant.getId() + " - " + participant.getName());
        }
        return response.toString();
    }
}