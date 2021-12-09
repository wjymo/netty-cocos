package com.zzn.nettygame.netty.handler.cmdhandler;

import com.zzn.nettygame.netty.dto.User;
import com.zzn.nettygame.netty.proto1.GameMsgProtocol;
import com.zzn.nettygame.netty.util.Broadcaster;
import com.zzn.nettygame.netty.util.UserManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

public class UserEntryCmdHandler implements ICmdHandler<GameMsgProtocol.UserEntryCmd> {
    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserEntryCmd cmd){
        int userId = cmd.getUserId();
        String heroAvatar = cmd.getHeroAvatar();

        User user=new User();
        user.setUserId(userId);
        user.setHeroAvatar(heroAvatar);
        UserManager.addUser(user);
        ctx.channel().attr(AttributeKey.valueOf("userId")).set(userId);

        GameMsgProtocol.UserEntryResult.Builder builder=GameMsgProtocol.UserEntryResult.newBuilder();
        builder.setUserId(userId);
        builder.setHeroAvatar(heroAvatar);

        GameMsgProtocol.UserEntryResult userEntryResult=builder.build();
        Broadcaster.broadcast(userEntryResult);
    }
}
