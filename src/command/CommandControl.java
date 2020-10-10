package command;

import javafx.scene.control.TextArea;

public class CommandControl {

    private ICommand currentCommand;
    private TextArea outputArea;

    public ICommand matchCommand(String command, TextArea outputArea) {
        this.outputArea = outputArea;
        return currentCommand;
    }

    public void executeCommand() {
        System.out.println("--- execute ---");
        String response = currentCommand.execute();
        outputArea.setText(response);
    }
}
