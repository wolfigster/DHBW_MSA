package msa;

import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;
import cryptography.Cryptography;
import gui.GUI;
import persistence.HSQLDB;
import persistence.tables.ChannelTable;
import persistence.tables.PostboxTable;

import java.util.concurrent.*;

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

        String channelName = ChannelTable.getChannelNameByParticipants(msgEvent.getParticipantFrom().getId(), msgEvent.getParticipantTo().getId()) == null ? ChannelTable.getChannelNameByParticipants(msgEvent.getParticipantTo().getId(), msgEvent.getParticipantFrom().getId()) : ChannelTable.getChannelNameByParticipants(msgEvent.getParticipantFrom().getId(), msgEvent.getParticipantTo().getId());
        if(channelName == null) return;
        Channel channel = ChannelTable.getChannel(channelName);
        Cryptography cryptography = new Cryptography();

        if(channel.getIntruder() != null ) {
            if(channel.getIntruder().getId() == this.id) {
                PostboxTable.insertPostbox(this.name, msgEvent.getParticipantFrom().getId(), "unknown");

                String crackedMsg = null;
                TimeLimiter timeLimiter = SimpleTimeLimiter.create(Executors.newSingleThreadExecutor());
                try {
                    String result = timeLimiter.callWithTimeout(new Callable<String>() {
                        @Override
                        public String call() {
                            String res = cryptography.crack(msgEvent.getMessage(), msgEvent.getAlgorithmType(), msgEvent.getKeyfile());
                            return res;
                        }
                    }, 30L, TimeUnit.SECONDS);
                    crackedMsg = result;
                } catch (TimeoutException | InterruptedException | ExecutionException e) {
                    crackedMsg = "unknown";
                }
                if(!crackedMsg.equals("unknown")) {
                    int whereId = HSQLDB.instance.getNextID("postbox_" + this.name);
                    PostboxTable.updateLastPostBoxMessage(this.name, whereId, crackedMsg.replace(",", "-"));
                    GUI.addTextToOutputArea("intruder " + this.name + " cracked message from participant " + msgEvent.getParticipantFrom().name + " | " + crackedMsg);
                } else {
                    GUI.addTextToOutputArea("intruder " + this.name + " | cracked message from participant " + msgEvent.getParticipantFrom().name + " failed");
                }
                return;
            }
        }

        GUI.addTextToOutputArea(this.getName() + " (" + this.getId() + ") received new message");
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
