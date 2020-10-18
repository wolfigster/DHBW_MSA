package command;

import persistence.tables.ParticipantTable;
import persistence.tables.PostboxTable;

public class ShowPostboxCmd implements ICommand {

    private final String participantName;

    public ShowPostboxCmd(String participantName) {
        System.out.println("ShowPostboxCmd was constructed");
        this.participantName = participantName;
    }

    @Override
    public String execute() {
        System.out.println("Run command ShowPostboxCmd");
        StringBuilder response = new StringBuilder();

        if(ParticipantTable.getParticipantByName(participantName) == null) {
            response.append(participantName).append(" doesn't exist");
            return response.toString();
        }

        response.append("ID | Participant from | Timestamp | Message");
        for (String string : PostboxTable.getPostBox(participantName)) {
            response.append("\n").append(string);
        }
        return response.toString();
    }
}