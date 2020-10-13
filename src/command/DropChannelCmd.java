package command;

import persistence.tables.ChannelTable;

public class DropChannelCmd implements ICommand {

    private final String name;

    public DropChannelCmd(String name) {
        System.out.println("DropChannelCmd was constructed");
        this.name = name;
    }

    @Override
    public String execute() {
        System.out.println("Run command DropChannelCmd");
        String response = null;

        if(ChannelTable.getParticipantsByChannelName(name) != null) {
            ChannelTable.deleteChannel(name);
            response = "channel " + name + " deleted";
        } else response = "unknown channel " + name;
        return response;
    }
}