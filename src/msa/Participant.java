package msa;

import com.google.common.eventbus.Subscribe;
import cryptography.Cryptography;
import persistence.tables.PostboxTable;

public class Participant {

    private int id;
    private String name;
    private ParticipantType type;

    public Participant(int id, String name, ParticipantType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    @Subscribe
    public void receiveMsg(MsgEvent msgEvent) {
        if(msgEvent.getParticipantFrom().getId() == this.id) return;
        Cryptography cryptography = new Cryptography();
        String encryptedMessage = cryptography.decrypt(msgEvent.getMessage(), msgEvent.getAlgorithmType(), msgEvent.getKeyfile());
        PostboxTable.insertPostbox(this.name, msgEvent.getParticipantFrom().getId(), encryptedMessage);
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
