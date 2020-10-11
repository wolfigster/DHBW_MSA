package network;

public enum ParticipantType {
    NORMAL("Normal", "normal", 1),
    INTRUDER("Intruder", "intruder", 2)
    ;

    private final String name;
    private final String type;
    private final int id;


    ParticipantType(String name, String type, int id) {
        this.name = name;
        this.type = type;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public static ParticipantType getParticipantTypeByName(String name) {
        for(ParticipantType participantType : values()) {
            if(participantType.getName().toLowerCase().equals(name.toLowerCase())) return participantType;
        }
        return null;
    }

    public static ParticipantType getParticipantTypeById(int id) {
        for(ParticipantType participantType : values()) {
            if(participantType.getId() == id) return participantType;
        }
        return null;
    }
}
