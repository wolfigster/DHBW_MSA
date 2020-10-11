package network;

public class Participant {

    private int id;
    private String name;
    private ParticipantType type;

    public Participant(int id, String name, ParticipantType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ParticipantType getType() {
        return type;
    }
}
