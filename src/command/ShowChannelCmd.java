package command;

import msa.Channel;
import persistence.tables.ChannelTable;

public class ShowChannelCmd implements ICommand {

    public ShowChannelCmd() {
        System.out.println("ShowChannelCmd was constructed");
    }

    @Override
    public String execute() {
        System.out.println("Run command ShowChannelCmd");
        StringBuilder response = new StringBuilder();

        response.append("Channel | Participant01 and Participant02");
        for(Channel channel : ChannelTable.getChannels()) {
            response.append("\n").append(channel.getName()).append(" | ").append(channel.getParticipant01().getName()).append(" and ").append(channel.getParticipant02().getName());
        }
        return response.toString();
    }
}