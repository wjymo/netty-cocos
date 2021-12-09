package com.zzn.nettygame.netty.util;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Broadcaster {
    private Broadcaster(){

    }
    private final static ChannelGroup CHANNEL_GROUP=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void addChannel(Channel channel){
        if(channel==null){
            return;
        }
        CHANNEL_GROUP.add(channel);
    }

    public static void removeChannel(Channel channel){
        if(channel==null){
            return;
        }
        CHANNEL_GROUP.remove(channel);
    }

    public static void broadcast(Object msg){
        if(msg!=null){
            CHANNEL_GROUP.writeAndFlush(msg);
        }

    }

}
