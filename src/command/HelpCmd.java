package command;

public class HelpCmd implements ICommand {

    public HelpCmd() {
        System.out.println("HelpCmd was constructed");
    }

    @Override
    public String execute() {
        System.out.println("Run command HelpCmd");
        StringBuilder response = new StringBuilder();

        response.append("The following commands are possible to execute:");
        response.append("\n> ").append("show algorithm");
        response.append("\n> ").append("show participant");
        response.append("\n> ").append("show channel");
        response.append("\n> ").append("drop channel [name]");
        response.append("\n> ").append("encrypt message \"[message]\" using [algorithm] and keyfile [filename]");
        response.append("\n> ").append("decrypt message \"[message]\" using [algorithm] and keyfile [filename]");
        response.append("\n> ").append("crack encrypted message \"[message]\" using [algorithm]");
        response.append("\n> ").append("register participant [name] with type [normal|intruder]");
        response.append("\n> ").append("create channel [name] from [participant01] to [participant02]");
        response.append("\n> ").append("intrude channel [name] by [participant]");
        response.append("\n> ").append("send message \"[message]\" from [participant01] to [participant02] using [algorithm] and keyfile [name]");

        return response.toString();
    }
}

