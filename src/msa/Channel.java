package msa;

import com.google.common.eventbus.EventBus;

public class Channel {

    private String name;
    private Participant participant01;
    private Participant participant02;
    private EventBus eventBus;

    public Channel(String name, Participant participant01, Participant participant02) {
        this.name = name;
        this.participant01 = participant01;
        this.participant02 = participant02;
        this.eventBus = new EventBus(name);
        this.eventBus.register(participant01);
        this.eventBus.register(participant02);
    }

    public void sendMsg(MsgEvent msgEvent) {
        eventBus.post(msgEvent);
    }

    public String getName() {
        return name;
    }

    public Participant getParticipant01() {
        return participant01;
    }

    public Participant getParticipant02() {
        return participant02;
    }
}
