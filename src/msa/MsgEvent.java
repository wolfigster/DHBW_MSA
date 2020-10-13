package msa;

import logger.AlgorithmType;

import java.io.File;

public class MsgEvent {

    private final Participant participantFrom;
    private final Participant participantTo;
    private final String message;
    private final AlgorithmType algorithmType;
    private final File keyfile;

    public MsgEvent(Participant participantFrom, Participant participantTo, String message, AlgorithmType algorithmType, File keyfile) {
        this.participantFrom = participantFrom;
        this.participantTo = participantTo;
        this.message = message;
        this.algorithmType = algorithmType;
        this.keyfile = keyfile;
    }

    public Participant getParticipantFrom() {
        return participantFrom;
    }

    public Participant getParticipantTo() {
        return participantTo;
    }

    public String getMessage() {
        return message;
    }

    public AlgorithmType getAlgorithmType() {
        return algorithmType;
    }

    public File getKeyfile() {
        return keyfile;
    }
}
