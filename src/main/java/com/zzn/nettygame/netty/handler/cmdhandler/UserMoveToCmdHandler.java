package com.zzn.nettygame.netty.handler.cmdhandler;

import com.zzn.nettygame.netty.proto1.GameMsgProtocol;
import com.zzn.nettygame.netty.util.Broadcaster;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

public class UserMoveToCmdHandler implements ICmdHandler<GameMsgProtocol.UserMoveToCmd> {
    @Override
    public void handle(ChannelHandlerContext ctx,GameMsgProtocol.UserMoveToCmd cmd){
        GameMsgProtocol.UserMoveToResult.Builder builder=GameMsgProtocol.UserMoveToResult.newBuilder();
        builder.setMoveUserId((Integer)ctx.channel().attr(AttributeKey.valueOf("userId")).get());
        builder.setMoveToPosX(cmd.getMoveToPosX());
        builder.setMoveToPosY(cmd.getMoveToPosY());
        GameMsgProtocol.UserMoveToResult userMoveToResult = builder.build();
        Broadcaster.broadcast(userMoveToResult);
    }
}
