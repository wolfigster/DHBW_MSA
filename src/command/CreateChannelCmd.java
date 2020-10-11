package command;

public class CreateChannelCmd implements ICommand {

    public CreateChannelCmd(String name) {
        System.out.println("CreateChannelCmd was constructed");
    }

    @Override
    public String execute() {
        System.out.println("Run command CreateChannelCmd");
        StringBuilder response = new StringBuilder();
        // Do something
        return response.toString();
    }
}