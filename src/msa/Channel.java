package msa;

import com.google.common.eventbus.EventBus;

public class Channel {

    private final String name;
    private final Participant participant01;
    private final Participant participant02;
    private Participant intruder;
    private EventBus eventBus;

    public Channel(String name, Participant participant01, Participant participant02) {
        this.name = name;
        this.participant01 = participant01;
        this.participant02 = participant02;
        this.eventBus = new EventBus(name);
        this.eventBus.register(participant01);
        this.eventBus.register(participant02);
        this.intruder = null;
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

    public Participant getIntruder() {
        return intruder;
    }

    public void setIntruder(Participant intruder) {
        this.eventBus.register(intruder);
        this.intruder = intruder;
    }
}
