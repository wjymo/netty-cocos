package com.zzn.nettygame.netty.handler.cmdhandler;

import com.zzn.nettygame.netty.dto.User;
import com.zzn.nettygame.netty.proto1.GameMsgProtocol;
import com.zzn.nettygame.netty.util.UserManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.Collection;
import java.util.Objects;

public class WhoElseIsHereCmdHandler implements ICmdHandler<GameMsgProtocol.WhoElseIsHereCmd> {

    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.WhoElseIsHereCmd cmd){
        GameMsgProtocol.WhoElseIsHereResult.Builder builder=GameMsgProtocol.WhoElseIsHereResult.newBuilder();
        Integer userId = (Integer)ctx.channel().attr(AttributeKey.valueOf("userId")).get();
        boolean hasUser=false;
        Collection<User> users = UserManager.listUser();
        for (User user : users) {
            if(user==null){
                continue;
            }
            if(Objects.equals(userId,user.getUserId())){
                continue;
            }
            hasUser=true;
            GameMsgProtocol.WhoElseIsHereResult.UserInfo.Builder userBuilder=
                    GameMsgProtocol.WhoElseIsHereResult.UserInfo.newBuilder();
            userBuilder.setUserId(user.getUserId());
            userBuilder.setHeroAvatar(user.getHeroAvatar());
//                GameMsgProtocol.WhoElseIsHereResult.UserInfo userInfo = userBuilder.build();
            builder.addUserInfo(userBuilder);
        }
        if(hasUser){
            GameMsgProtocol.WhoElseIsHereResult whoElseIsHereResult = builder.build();
            ctx.writeAndFlush(whoElseIsHereResult);
        }
    }
}
