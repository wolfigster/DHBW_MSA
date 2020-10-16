package msa;

import persistence.tables.ChannelTable;

import java.util.HashMap;

public class MSA {

    private static HashMap<String, Channel> channelHashMap = new HashMap<>();

    public static void initialize() {
        for(Channel channel : ChannelTable.getChannels()) channelHashMap.put(channel.getName(), channel);
    }

    public static Channel getChannel(String name) {
        return channelHashMap.get(name);
    }

    public static void addChannel(Channel channel) {
        channelHashMap.put(channel.getName(), channel);
    }
}
