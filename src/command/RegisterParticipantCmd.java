package command;

import msa.Participant;
import msa.ParticipantType;
import persistence.HSQLDB;
import persistence.tables.ParticipantTable;
import persistence.tables.PostboxTable;
import persistence.tables.TypeTable;

public class RegisterParticipantCmd implements ICommand {

    private final String participantName;
    private final String type;

    public RegisterParticipantCmd(String participantName, String type) {
        System.out.println("RegisterParticipantCmd was constructed");
        this.participantName = participantName;
        this.type = type;
    }

    @Override
    public String execute() {
        System.out.println("Run command RegisterParticipantCmd");
        String response = null;

        if(ParticipantTable.getParticipantByName(participantName) == null) {
            // [i]
            ParticipantType participantType = TypeTable.getTypeIdByName(type);
            if(participantType == null) return "type with name \"" + type + "\" not found";
            int nextID = HSQLDB.instance.getNextID("participants") + 1;
            Participant participant = new Participant(nextID, participantName, participantType);
            ParticipantTable.insertParticipant(participant);

            // [ii]
            PostboxTable.createTable(participantName);

            response = "participant " + participantName + " with type " + participantType.getName() + " registered and postbox_" + participantName + " created";
        } else response = "participant " + participantName + " already exists, using existing postbox_" + participantName;

        return response;
    }
}