package command;

import javafx.scene.control.TextArea;

public class CommandControl {

    private ICommand currentCommand;
    private TextArea outputArea;

    public ICommand matchCommand(String command, TextArea outputArea) {
        this.outputArea = outputArea;

        if(command.matches("show algorithm")) currentCommand = new ShowAlgorithmCmd();
        else {
            return currentCommand = null;
        }
        return currentCommand;
    }

    public void executeCommand() {
        System.out.println("--- execute ---");
        String response = currentCommand.execute();
        outputArea.setText(response);
    }
}
