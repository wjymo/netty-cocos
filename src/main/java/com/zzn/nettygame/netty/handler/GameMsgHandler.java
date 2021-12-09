package com.zzn.nettygame.netty.handler;

import com.google.protobuf.GeneratedMessageV3;
import com.zzn.nettygame.netty.handler.cmdhandler.*;
import com.zzn.nettygame.netty.proto1.GameMsgProtocol;
import com.zzn.nettygame.netty.util.Broadcaster;
import com.zzn.nettygame.netty.util.UserManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameMsgHandler extends ChannelInboundHandlerAdapter {
//    private final static ChannelGroup CHANNEL_GROUP=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Integer userId = (Integer)ctx.channel().attr(AttributeKey.valueOf("userId")).get();
        log.info("用户：{}下线",userId);
        UserManager.removeUser(userId);
        Broadcaster.removeChannel(ctx.channel());
        GameMsgProtocol.UserQuitResult.Builder builder=GameMsgProtocol.UserQuitResult.newBuilder();
        builder.setQuitUserId(userId);
        GameMsgProtocol.UserQuitResult userQuitResult = builder.build();
        Broadcaster.broadcast(userQuitResult);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        try {
            Broadcaster.addChannel(ctx.channel());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("收到客户消息：{}",msg);
//        if(msg instanceof GameMsgProtocol.UserEntryCmd){
//            GameMsgProtocol.UserEntryCmd cmd = (GameMsgProtocol.UserEntryCmd) msg;
//            (new UserEntryCmdHandler()).handle(ctx,cmd);
//        }else if(msg instanceof GameMsgProtocol.WhoElseIsHereCmd){
//            new WhoElseIsHereCmdHandler().handle(ctx,(GameMsgProtocol.WhoElseIsHereCmd)msg);
//        }else if(msg instanceof GameMsgProtocol.UserMoveToCmd){
//            GameMsgProtocol.UserMoveToCmd cmd = (GameMsgProtocol.UserMoveToCmd) msg;
//            new UserMoveToCmdHandler().handle(ctx,cmd);
//        }
        ICmdHandler<? extends GeneratedMessageV3> cmdHandler = CmdHandlerFactory.create(msg);
        cmdHandler.handle(ctx,cast(msg));
//        cmdHandler.handle(ctx,(GeneratedMessageV3)msg);
    }

    private static <TCmdx extends GeneratedMessageV3> TCmdx cast(Object msg){
        if(null==msg){
            return null;
        }
        return (TCmdx)msg;
    }

}
