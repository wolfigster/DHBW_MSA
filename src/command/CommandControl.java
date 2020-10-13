package command;

import javafx.scene.control.TextArea;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandControl {

    private ICommand currentCommand;
    private TextArea outputArea;

    private final Pattern encryptMessagePattern = Pattern.compile("encrypt message \"(.*)\" using (.*) and keyfile (.*)");
    private final Pattern decryptMessagePattern = Pattern.compile("decrypt message \"(.*)\" using (.*) and keyfile (.*)");
    private final Pattern crackMessagePattern = Pattern.compile("crack encrypted message \"(.*)\" using (.*)");
    private final Pattern registerParticipantPattern = Pattern.compile("register participant (.*) with type (.*)");
    private final Pattern createChannelPattern = Pattern.compile("create channel (.*) from (.*) to (.*)");
    private final Pattern intrudeChannelPattern = Pattern.compile("intrude channel (.*) by (.*)");
    private final Pattern sendMessagePattern = Pattern.compile("send message \"(.*)\" from (.*) to (.*) using (.*) and keyfile (.*)");

    public CommandControl(TextArea outputArea) {
        this.outputArea = outputArea;
    }

    public ICommand matchCommand(String command) {

        if(command.matches("show algorithms?")) currentCommand = new ShowAlgorithmCmd();
        else if(command.matches("show participants?")) currentCommand = new ShowParticipantCmd();
        else if(command.matches(encryptMessagePattern.pattern())) {
            Matcher matcher = encryptMessagePattern.matcher(command);
            while(matcher.find()) currentCommand = new EncryptMessageCmd(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        else if(command.matches(decryptMessagePattern.pattern())) {
            Matcher matcher = decryptMessagePattern.matcher(command);
            while(matcher.find()) currentCommand = new DecryptMessageCmd(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        else if(command.matches(crackMessagePattern.pattern())) {
            Matcher matcher = crackMessagePattern.matcher(command);
            while(matcher.find()) currentCommand = new CrackMessageCmd(matcher.group(1), matcher.group(2));
        }
        else if(command.matches(registerParticipantPattern.pattern())) {
            Matcher matcher = registerParticipantPattern.matcher(command);
            while(matcher.find()) currentCommand = new RegisterParticipantCmd(matcher.group(1), matcher.group(2));
        }
        else if(command.matches(createChannelPattern.pattern())) {
            Matcher matcher = createChannelPattern.matcher(command);
            while(matcher.find()) currentCommand = new CreateChannelCmd(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        else if(command.matches("show channels?")) currentCommand = new ShowChannelCmd();
        else if(command.matches("drop channel .*")) currentCommand = new DropChannelCmd(command.replace("drop channel ", ""));
        else if(command.matches(intrudeChannelPattern.pattern())) {
            Matcher matcher = intrudeChannelPattern.matcher(command);
            while(matcher.find()) currentCommand = new IntrudeChannelCmd(matcher.group(1), matcher.group(2));
        }
        else if(command.matches(sendMessagePattern.pattern())) {
            Matcher matcher = sendMessagePattern.matcher(command);
            while(matcher.find()) currentCommand = new SendMessageCmd(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5));
        }
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
