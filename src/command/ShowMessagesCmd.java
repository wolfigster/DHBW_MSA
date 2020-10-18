package command;

import persistence.tables.MessageTable;

public class ShowMessagesCmd implements ICommand {

    public ShowMessagesCmd() {
        System.out.println("ShowPostboxCmd was constructed");
    }

    @Override
    public String execute() {
        System.out.println("Run command ShowPostboxCmd");
        StringBuilder response = new StringBuilder();

        response.append("ID | Participant from | Participant to | Timestamp | Algorithm | Keyfile | Plain message | Encrypted Message");
        for (String string : MessageTable.getMessages()) {
            response.append("\n").append(string);
        }
        return response.toString();
    }
}