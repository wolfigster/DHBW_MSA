package command;

import msa.Participant;
import persistence.tables.ParticipantTable;

public class ShowParticipantCmd implements ICommand {

    public ShowParticipantCmd() {
        System.out.println("ShowParticipantCmd was constructed");
    }

    @Override
    public String execute() {
        System.out.println("Run command ShowParticipantCmd");
        StringBuilder response = new StringBuilder();
        // Do something

        response.append("ID | Name | Type");
        for (Participant participant : ParticipantTable.getParticipants()) {
            response.append("\n").append(participant.getId()).append(" | ").append(participant.getName()).append(" | ").append(participant.getType());
        }
        return response.toString();
    }
}